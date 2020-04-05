package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import com.ahnbcilab.tremorquantification.functions.SafeClickListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.media.ImageReader
import android.net.Uri
import android.os.CountDownTimer
import android.os.Environment
import android.support.constraint.ConstraintLayout
import android.support.v4.widget.CircularProgressDrawable
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.INVISIBLE
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.ahnbcilab.tremorquantification.data.PathTraceData
import com.ahnbcilab.tremorquantification.functions.Drawable
import com.ahnbcilab.tremorquantification.functions.fitting
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.samsung.accessory.safiletransfer.a.e
import kotlinx.android.synthetic.main.activity_spiral.*
import kotlinx.android.synthetic.main.activity_writing.*
import kotlinx.android.synthetic.main.activity_writing.writingagain
import kotlinx.android.synthetic.main.activity_writing.writingcanvasLayout
import kotlinx.android.synthetic.main.activity_writing.writingfinish
import kotlinx.android.synthetic.main.crts_result_item2.*
import org.spongycastle.asn1.iana.IANAObjectIdentifiers.directory
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class Spiral : AppCompatActivity() {

    var filename: String = ""
    var Clinic_ID: String = ""
    var downurl : String = ""
    var PatientName: String = ""
    var path1: String = ""
    var spiral_result: DoubleArray = doubleArrayOf()
    val left_spiral_result: DoubleArray = doubleArrayOf()
    var right_spiral: String = ""
    var left: Int = 0
    var count : Int = 0
    private var currentX: Float = 0.toFloat()
    private var currentY: Float = 0.toFloat()
    private val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    private var spiral_ref = storageRef.child("images/")
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private var firebase_spiral_url = firebaseDatabase.getReference("URLList")
    private var firebase_path = firebaseDatabase.getReference("URLList")
    private var uid : String =""
    private var crts_right_spiral_downurl : String =""
    private var lorr : Boolean = true
    private var image_path : String = ""
    private lateinit var progressDialog : ProgressDialog

    private val pathTrace: MutableList<PathTraceData> = mutableListOf()
    private val timer = object : CountDownTimer(Long.MAX_VALUE, 1000 / 60) {
        override fun onTick(millisUntilFinished: Long) {
            pathTrace.add(PathTraceData(currentX, currentY, (Long.MAX_VALUE - millisUntilFinished).toInt()))
        }

        override fun onFinish() {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_spiral)


        val intent = intent
        //TODO: writing url 받아오기
        path1 = intent.getStringExtra("path")
        Clinic_ID = intent.getStringExtra("Clinic_ID")
        PatientName = intent.getStringExtra("PatientName")
        var crts_num = intent.getStringExtra("crts_num")
        right_spiral = intent.getStringExtra("right_spiral")
        spiral_result = intent.getDoubleArrayExtra("spiral_result")
        left = intent.getIntExtra("left", -1)
        lorr = intent.getBooleanExtra("lorr", true)

        if(intent.hasExtra("crts_right_spiral_downurl"))
            crts_right_spiral_downurl = intent.getStringExtra("crts_right_spiral_downurl")

        if (path1.equals("main")) {
            test_title_spiral.visibility = View.INVISIBLE
        }
        if (right_spiral.equals("yes")) {
            test_title_spiral.setImageResource(R.drawable.crtsb_13_text)
        }


        //의사 ID 얻어오기
        val user = FirebaseAuth.getInstance().currentUser
        uid = user!!.getUid()
        //왼손/오른손에 따라 파이어베이스 레퍼런스를 다르게 설정
        if(lorr)
        {
            firebase_spiral_url = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Spiral List").child("Right")
        }
        else
        {
            firebase_spiral_url = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Spiral List").child("Left")
        }

        //원래 URL이 들어있는 개수를 구함 스토리지의 저장명으로 활용될 예정
        firebase_spiral_url.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                count = dataSnapshot.childrenCount.toInt()

                // 0번, 1번, 2번.. 순으로 저장
                if (lorr) {
                    image_path = "$uid/$Clinic_ID/Sprial/Right/Image/$count.jpg"
                }
                else{
                    image_path = "$uid/$Clinic_ID/Sprial/Left/Image/$count.jpg"
                }

                //정해진 이미지 레퍼런스를 설정한다
                spiral_ref= storageRef.child(image_path)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })


        filename = SimpleDateFormat("yyyyMMdd_HH_mm").format(Calendar.getInstance().time)


        val layout = writingcanvasLayout
        val view = DrawView(this)
        val baseLine = baseView(this)
        layout.addView(view)
        layout.addView(baseLine)

        // 그림 그릴 때, 다시 그리는 버튼
        writingagain.setOnClickListener {
            timer.cancel()
            view.clearLayout()
            val intent = Intent(this, Spiral::class.java)
            intent.putExtra("Clinic_ID", Clinic_ID)
            intent.putExtra("PatientName", PatientName)
            intent.putExtra("path", path1)
            intent.putExtra("right_spiral", right_spiral)
            intent.putExtra("crts_num", crts_num)
            intent.putExtra("filename", "${Clinic_ID}_$filename.csv")
            intent.putExtra("spiral_result", spiral_result)
            intent.putExtra("crts_num", crts_num)
            intent.putExtra("left", left)
            startActivity(intent)
        }

        backButton_spiral.setOnClickListener {
            loadingEnd()
            val dlg = AlertDialog.Builder(this@Spiral)
            dlg.setTitle("종료")
                    .setMessage("지금 종료하면 데이터를 모두 잃게됩니다. 종료하시겠습니까?")
                    .setPositiveButton("종료") { dialogInterface, i ->
                        if (path1.equals("main")) {
                            val intent = Intent(this@Spiral, Spiral_Task_Select::class.java)
                            intent.putExtra("Clinic_ID", Clinic_ID)
                            intent.putExtra("PatientName", PatientName)
                            intent.putExtra("path", path1)
                            intent.putExtra("task", "spiral")
                            startActivity(intent)
                            finish()
                        } else {
                            onBackPressed()
                            finish()
                        }
                    }
                    .setNegativeButton("취소") { dialogInterface, i -> }
                    .show()
        }
        backButton2_spiral.setOnClickListener {
            val dlg = AlertDialog.Builder(this@Spiral)
            dlg.setTitle("종료")
                    .setMessage("지금 종료하면 데이터를 모두 잃게됩니다. 종료하시겠습니까?")
                    .setPositiveButton("종료") { dialogInterface, i ->
                        if (path1.equals("main")) {
                            val intent = Intent(this@Spiral, Spiral_Task_Select::class.java)
                            intent.putExtra("Clinic_ID", Clinic_ID)
                            intent.putExtra("PatientName", PatientName)
                            intent.putExtra("path", path1)
                            intent.putExtra("task", "spiral")
                            startActivity(intent)
                            finish()
                        } else {
                            onBackPressed()
                            finish()
                        }
                    }
                    .setNegativeButton("취소") { dialogInterface, i -> }
                    .show()
        }

        // 그림 그리고 나서, 다음으로 넘어가는 버튼
        writingfinish.setSafeOnClickListener {
            timer.cancel()
            loading()
            //view.saveAsJPG(view, this.filesDir.path + "/spiralTest", "${patientId}_$filename.jpg")
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            var prevData: PathTraceData? = null
            val metaData = "${CurrentUserData.user?.uid},$Clinic_ID,$filename"
            val path = File("${this.filesDir.path}/testData") // raw save to file dir(data/com.bcilab....)
            if (!path.exists()) path.mkdirs()
            val file = File(path, "${Clinic_ID}_$filename.csv")
            try {
                PrintWriter(file).use { out ->
                    out.println(metaData)
                    for (item in pathTrace)
                        out.println(item.joinToString(","))
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error on writing file", Toast.LENGTH_LONG).show()
                println(e.message)
            }
            /* ******************************** processing image file *************************************/

            var v1 = window.decorView
            v1.isDrawingCacheEnabled = true
            v1.buildDrawingCache()
            var captureView = v1.drawingCache
            try {
                var fos = FileOutputStream("sdcard/Download/")
                captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.close()
                fos.flush()
            }catch (e : FileNotFoundException){
                e.printStackTrace()
            }
            val baos = ByteArrayOutputStream()
            captureView.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            var uri = Uri.fromFile(File("sdcard/Download/"))
            val data = baos.toByteArray()
            /* ******************************** save image local *************************************/
            try{
                onCap(captureView)
            } catch (e : java.lang.Exception){

            }finally {
                captureView.recycle();
            }
            /* ******************************** save image firebase *************************************/
            //삭제를 위한 path저장
            firebase_path = firebaseDatabase.getReference("URL List").child(uid).child(Clinic_ID).child("Path")
            firebase_path.push().setValue(spiral_ref.path)
            var uploadTask = spiral_ref.putBytes(data)
            uploadTask.addOnFailureListener(){
                Toast.makeText(this, "image uploading failed", Toast.LENGTH_SHORT).show()
            }
            uploadTask.addOnSuccessListener() {
                val urlTask = it.getStorage().getDownloadUrl()
                while (!urlTask.isSuccessful) ;
                val downloadUrl = urlTask.result

                downurl = downloadUrl.toString()

                //이미지 경로를 활용하여 데이터 경로를 만든다
                val data_path = image_path.replace("Image", "Data").replace("jpg", "csv")
                //left hand
                //TODO: writing도 보내자
                if (right_spiral.equals("no")) { //left
                    val intent = Intent(this, AnalysisActivity::class.java)
                    intent.putExtra("filename", "${Clinic_ID}_$filename.csv")
                    intent.putExtra("path1", path1)
                    intent.putExtra("path", path)
                    intent.putExtra("Clinic_ID", Clinic_ID)
                    intent.putExtra("PatientName", PatientName)
                    intent.putExtra("task", "SpiralTask")
                    intent.putExtra("spiral_result", spiral_result)
                    intent.putExtra("left_spiral_downurl", String())
                    intent.putExtra("crts_right_spiral_downurl", downurl)
                    intent.putExtra("right_spiral", "no")
                    intent.putExtra("crts_num", crts_num)
                    intent.putExtra("data_path", data_path)
                    startActivity(intent)
                    Toast.makeText(this, "Wait...", Toast.LENGTH_LONG).show()
                    loadingEnd()
                    finish()
                    //right hand
                } else {
                    val intent = Intent(this, AnalysisActivity::class.java)
                    intent.putExtra("filename", "${Clinic_ID}_$filename.csv")
                    intent.putExtra("path1", path1)
                    intent.putExtra("path", path)
                    intent.putExtra("left", left)
                    intent.putExtra("Clinic_ID", Clinic_ID)
                    intent.putExtra("PatientName", PatientName)
                    intent.putExtra("task", "SpiralTask")
                    intent.putExtra("right_spiral", "yes")
                    intent.putExtra("spiral_result", spiral_result)
                    intent.putExtra("right_spiral_downurl", downurl)
                    intent.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl)
                    intent.putExtra("left_spiral_result", left_spiral_result)
                    intent.putExtra("data_path", data_path)
                    intent.putExtra("crts_num", crts_num)
                    startActivity(intent)
                    Toast.makeText(this, "Wait...", Toast.LENGTH_LONG).show()
                    loadingEnd()
                    finish()
                }

            }
            if (pathTrace.size > 2) {
                prevData = pathTrace[pathTrace.size - 1]
                for (i in (pathTrace.size - 2) downTo 0) {
                    if (prevData.isSamePosition(pathTrace[i]))
                        pathTrace.removeAt(i)
                    else
                        break
                }
            }



        }
    }


    inner class DrawView(context: Context) : Drawable(context) {
        private var flag = false

        override fun onTouchEvent(event: MotionEvent): Boolean {
            currentX = event.x
            currentY = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!flag) {
                        flag = true
                        timer.start()
                    }
                }
            }
            return super.onTouchEvent(event)
        }

        override fun clearLayout() {
            super.clearLayout()
            pathTrace.clear()
            timer.cancel()
        }
    }

    inner class baseView(context: Context) : View(context) {
        private val startX = this.resources.displayMetrics.widthPixels / 2
        private val startY = this.resources.displayMetrics.heightPixels / 2

        private val theta = FloatArray(720) { (((it * (Math.PI / 180)) / 3) * 2).toFloat() }
        private val basePath = Path()
        private val basePaint = Paint()

        init {
            basePaint.style = Paint.Style.STROKE
            basePaint.strokeWidth = 2f
            basePaint.alpha = 50
            basePaint.isAntiAlias = true
            fitting.startX = startX
            fitting.startY = startY
        }

        override fun onDraw(canvas: Canvas) {
            basePath.moveTo(startX.toFloat(), startY.toFloat())
            for (t in theta)
                basePath.lineTo((t * Math.cos(2.5 * t) * 50 + startX).toFloat(), (t * Math.sin(2.5 * t) * 50 + startY).toFloat())

            canvas.drawPath(basePath, basePaint)
        }

    }
    @Throws(Exception::class)
    private fun onCap(bm: Bitmap) {
        try {
            val imgFile = "save.jpg"
            val imgPath = StringBuffer("sdcard/Download/")
            val saveFile = File(imgPath.toString())
            if (!saveFile.isDirectory) {
                saveFile.mkdirs()
            }
            imgPath.append(imgFile)
            var out = FileOutputStream(imgPath.toString())
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out)
            sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())))
        } catch (e: Exception) {

        } finally {
            if (System.out != null)
                System.out.close()
            // saveFile = null
        }
    }
    fun loading() {
        //로딩
        android.os.Handler().postDelayed(
                {
                    progressDialog = ProgressDialog(this@Spiral)
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    progressDialog.setIndeterminate(true)
                    progressDialog.setMessage("분석 중입니다.")
                    progressDialog.show()
                }, 0)
    }
    fun loadingEnd() {
        android.os.Handler().postDelayed(
                { progressDialog.dismiss() }, 0)
    }
    fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
        val safeClickListener = SafeClickListener {
            onSafeClick(it)
        }
        setOnClickListener(safeClickListener)
    }
}
