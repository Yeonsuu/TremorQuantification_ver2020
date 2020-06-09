package com.ahnbcilab.tremorquantification.tremorquantification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import android.os.CountDownTimer
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.ahnbcilab.tremorquantification.data.PathTraceData
import com.ahnbcilab.tremorquantification.functions.Drawable
import com.ahnbcilab.tremorquantification.functions.fitting
import com.ahnbcilab.tremorquantification.functions.main
import com.ahnbcilab.tremorquantification.tremorquantification.R.drawable.view
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.activity_line_test.*
import kotlinx.android.synthetic.main.activity_writing.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*

class WritingActivity : AppCompatActivity() {

    var filename: String = ""
    var path1: String = ""
    var Clinic_ID: String = ""
    var PatientName: String = ""
    var path: String = ""
    private val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    private var spiral_ref = storageRef.child("images/")
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private var firebase_write_url = firebaseDatabase.getReference("URLList")
    private var firebase_path = firebaseDatabase.getReference("URLList")
    private var uid : String =""
    var count : Int = 0
    private var image_path : String = ""
    var downurl : String = ""
    private var lastTimeBackPressed:Long = 0
    private var iswrite : Boolean = false

    private var currentX: Float = 0.toFloat()
    private var currentY: Float = 0.toFloat()

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
        setContentView(R.layout.activity_writing)

        val intent = intent
        Clinic_ID = intent.getStringExtra("ClinicID")
        PatientName = intent.getStringExtra("PatientName")
        var crts_num = intent.getStringExtra("crts_num")

        //의사 ID 얻어오기
        val user = FirebaseAuth.getInstance().currentUser
        uid = user!!.getUid()
        firebase_write_url = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Writing List")

        //원래 URL이 들어있는 개수를 구함 스토리지의 저장명으로 활용될 예정
        firebase_write_url.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                count = dataSnapshot.childrenCount.toInt()
                image_path = "$uid/$Clinic_ID/Writing/Image/$count.jpg"
                //정해진 이미지 레퍼런스를 설정한다
                spiral_ref= storageRef.child(image_path)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        filename = SimpleDateFormat("yyyyMMdd_HH_mm").format(Calendar.getInstance().time)


        val layout = writingcanvasLayout

        val view = DrawView(this)
        layout.addView(view)

        // 그림 그릴 때, 다시 그리는 버튼
        writingagain.setOnClickListener {
            timer.cancel()
            view.clearLayout()
            val intent = Intent(this, WritingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.putExtra("ClinicID", Clinic_ID)
            intent.putExtra("PatientName", PatientName)
            intent.putExtra("crts_num", crts_num)
            startActivity(intent)
        }

        // 그림 그리고 나서, 다음으로 넘어가는 버튼
        writingfinish.setOnClickListener {
            timer.cancel()
            if(!iswrite)
            {
                Toast.makeText(this, "글씨를 작성해주세요", Toast.LENGTH_LONG).show()
            }
            else
            {
                Toast.makeText(this, "이미지 저장 중...", Toast.LENGTH_LONG).show()
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
                }catch (e : java.io.FileNotFoundException){
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
                    while (!urlTask.isSuccessful);
                    val downloadUrl = urlTask.result

                    downurl = downloadUrl.toString()

                    val intent = Intent(this, Spiral::class.java)
                    intent.putExtra("Clinic_ID", Clinic_ID);
                    intent.putExtra("PatientName", PatientName);
                    intent.putExtra("path", "CRTS");
                    intent.putExtra("path1", "CRTS");
                    intent.putExtra("right_spiral", "no")
                    intent.putExtra("spiral_result", doubleArrayOf())
                    intent.putExtra("writing_downurl", downurl)
                    intent.putExtra("crts_right_spiral_downurl", String())
                    intent.putExtra("lorr", true)
                    intent.putExtra("crts_num", crts_num)
                    startActivity(intent)
                    val context = applicationContext
                    val inflater = layoutInflater
                    val customToast = inflater.inflate(R.layout.toast_custom, null)
                    val customtoast = Toast(context)
                    customtoast.view = customToast
                    customtoast.setGravity(Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL, 0, 0)
                    customtoast.duration = Toast.LENGTH_LONG
                    customtoast.show()
                    finish()
                }
            }
            backButton_write2.setOnClickListener {
                Log.v("dddddddddd", "ddddddddddd")
                val dlg = AlertDialog.Builder(this@WritingActivity)
                dlg.setTitle("종료")
                        .setMessage("이전 화면으로 되돌아가시겠습니까?")
                        .setPositiveButton("돌아가기") { dialogInterface, i ->
                            if (path1.equals("main")) {
                                onBackPressed()
                                finish()
                            } else {
                                onBackPressed();
                                finish()
                            }
                        }
                        .setNegativeButton("취소") { dialogInterface, i -> }
                        .show()
            }
            backButton2_write2.setOnClickListener {
                Log.v("dddddddddd", "ddddddddddd")
                val dlg = AlertDialog.Builder(this@WritingActivity)
                dlg.setTitle("돌아가기")
                        .setMessage("이전 화면으로 되돌아가시겠습니까?")
                        .setPositiveButton("돌아가기") { dialogInterface, i ->
                            onBackPressed();
                            finish()
                        }
                        .setNegativeButton("취소") { dialogInterface, i -> }
                        .show()
            }

            var prevData: PathTraceData? = null

            if (pathTrace.size > 2) {
                prevData = pathTrace[pathTrace.size - 1]
                for (i in (pathTrace.size - 2) downTo 0) {
                    if (prevData.isSamePosition(pathTrace[i]))
                        pathTrace.removeAt(i)
                    else
                        break
                }
            }

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


        }
    }

    inner class DrawView(context: Context) : Drawable(context) {
        private var flag = false

        override fun onTouchEvent(event: MotionEvent): Boolean {
            iswrite = true
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

    override fun onBackPressed() {
        Toast.makeText(this, "이전 화면으로 돌아가고 싶으시다면 ' < ' 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
    }

}