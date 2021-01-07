package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.ahnbcilab.tremorquantification.data.PathTraceData
import com.ahnbcilab.tremorquantification.functions.Drawable
import com.ahnbcilab.tremorquantification.functions.SafeClickListener
import com.ahnbcilab.tremorquantification.functions.fitting
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_line.*
import kotlinx.android.synthetic.main.activity_line.test_title_line
import kotlinx.android.synthetic.main.activity_line_test.*
import kotlinx.android.synthetic.main.activity_spiral.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Semaphore
import android.text.TextPaint
import android.util.AttributeSet

class Line : AppCompatActivity() {

    var filename: String = ""
    var path1: String = ""
    var Clinic_ID: String = ""
    var PatientName: String = ""
    var left: Int = 0
    var count: Int = 0
    private var currentX: Float = 0.toFloat()
    private var currentY: Float = 0.toFloat()
    private val storage = FirebaseStorage.getInstance()
    val storageRef = storage.reference
    private var line_ref = storageRef.child("test/")
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private var firebase_line_url = firebaseDatabase.getReference("URLList")
    private var firebase_path = firebaseDatabase.getReference("URLList")
    private var uid: String = ""
    private var image_path: String = ""
    private var crts_right_spiral_downurl: String = ""
    private var crts_left_spiral_downurl: String = ""
    private var writing_downurl : String = ""
    private var lorr: Boolean = false
    private lateinit var progressDialog : ProgressDialog
    private var lastTimeBackPressed:Long = 0
    private var isdraw : Boolean = false

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
        setContentView(R.layout.activity_line)
        val intent = intent
        path1 = intent.getStringExtra("path")
        Clinic_ID = intent.getStringExtra("Clinic_ID")
        PatientName = intent.getStringExtra("PatientName")
        val spiral_result = intent.getDoubleArrayExtra("spiral_result")
        val left_spiral_result = intent.getDoubleArrayExtra("left_spiral_result")
        var crts_num = intent.getStringExtra("crts_num")
        left = intent.getIntExtra("left", -1);
        lorr = intent.getBooleanExtra("lorr", false)
        //의사 ID 얻어오기
        val user = FirebaseAuth.getInstance().currentUser
        uid = user!!.getUid()
        if(intent.hasExtra("crts_right_spiral_downurl"))
            crts_right_spiral_downurl = intent.getStringExtra("crts_right_spiral_downurl")
        if(intent.hasExtra("crts_left_spiral_downurl"))
            crts_left_spiral_downurl = intent.getStringExtra("crts_left_spiral_downurl")
        if(intent.hasExtra("writing_downurl"))
            writing_downurl = intent.getStringExtra("writing_downurl")
        //왼손/오른손에 따라 파이어베이스 레퍼런스를 다르게 설정
        if (path1.equals("main")) {
            test_title_line.visibility = View.INVISIBLE
            line_right_button.visibility = View.INVISIBLE
            line_left_button.visibility = View.INVISIBLE
            backButton_image_line.visibility = View.INVISIBLE

        } else {
            left = 1;
        }
        //crts에서 온 것이 아니라면 여기서 카운트 설정
        if (lorr) {
            firebase_line_url = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Line List").child("Right")
        } else {
            firebase_line_url = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Line List").child("Left")
        }
        //원래 URL이 들어있는 개수를 구함 스토리지의 저장명으로 활용될 예정
        firebase_line_url.addValueEventListener(object : ValueEventListener {
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
                line_ref= storageRef.child(image_path)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        //만약 crts에서 온 것 이라면 여기서 cnt 다시 초기화
        line_right_button.setOnClickListener {
            line_right_button.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.right_select_button))
            line_left_button.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.left_nonselect_button))
            firebase_line_url = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Line List").child("Right")
            readData(object : FirebaseCallback {
                override fun onCallBack(cnt: Int) {
                    count = cnt
                }

            })
            lorr = true
            left = 0;
        } //오른손 왼손 검사
        line_left_button.setOnClickListener {
            line_right_button.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.right_nonselect_button))
            line_left_button.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.left_select_button))
            firebase_line_url = firebaseDatabase.getReference("PatientList").child(Clinic_ID).child("Line List").child("Left")
            readData(object : FirebaseCallback {
                override fun onCallBack(cnt: Int) {
                    count = cnt
                }

            })
            lorr = false
            left = 1;
        }
        backButton_line.setOnClickListener {
            val dlg = AlertDialog.Builder(this@Line)
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
        backButton2_line.setOnClickListener {
            val dlg = AlertDialog.Builder(this@Line)
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
        filename = SimpleDateFormat("yyyyMMdd_HH_mm").format(Calendar.getInstance().time)


        val layout = writingcanvasLayout2
        val view = DrawView(this)
        val baseLine = baseView(this)
        layout.addView(view)
        layout.addView(baseLine)

        // 그림 그릴 때, 다시 그리는 버튼
        writingagain2.setOnClickListener {
            timer.cancel()
            view.clearLayout()
            val intent = Intent(this, Line::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            intent.putExtra("filename", "${Clinic_ID}_$filename.csv")
            intent.putExtra("PatientName", PatientName)
            intent.putExtra("Clinic_ID", Clinic_ID)
            intent.putExtra("path", path1)
            intent.putExtra("left_spiral_result", left_spiral_result)
            intent.putExtra("spiral_result", spiral_result)
            intent.putExtra("crts_num", crts_num)
            intent.putExtra("left", left)
            startActivity(intent)
            finish()
        }
        // 그냥 뒤로가기랑 그리다가 뒤로가기의 차이
        backButton_line.setOnClickListener {
            val dlg = AlertDialog.Builder(this@Line)
            dlg.setTitle("종료")
                    .setMessage("지금 종료하면 데이터를 모두 잃게됩니다. 종료하시겠습니까?")
                    .setPositiveButton("종료") { dialogInterface, i ->
                        if (path1.equals("main")) {
                            val intent = Intent(this@Line, Spiral_Task_Select::class.java)
                            intent.putExtra("Clinic_ID", Clinic_ID)
                            intent.putExtra("PatientName", PatientName)
                            intent.putExtra("path", path1)
                            intent.putExtra("task", "line");
                            startActivity(intent)
                            finish()
                        } else {
                            onBackPressed();
                            finish()
                        }
                    }
                    .setNegativeButton("취소") { dialogInterface, i -> }
                    .show()
        }
        backButton2_line.setOnClickListener {
            val dlg = AlertDialog.Builder(this@Line)
            dlg.setTitle("종료")
                    .setMessage("지금 종료하면 데이터를 모두 잃게됩니다. 종료하시겠습니까?")
                    .setPositiveButton("종료") { dialogInterface, i ->
                        if (path1.equals("main")) {
                            val intent = Intent(this@Line, Spiral_Task_Select::class.java)
                            intent.putExtra("Clinic_ID", Clinic_ID)
                            intent.putExtra("PatientName", PatientName)
                            intent.putExtra("path", path1)
                            intent.putExtra("task", "line");
                            startActivity(intent)
                            finish()
                        } else {
                            onBackPressed();
                            finish()
                        }
                    }
                    .setNegativeButton("취소") { dialogInterface, i -> }
                    .show()
        }


        // 그림 그리고 나서, 다음으로 넘어가는 버튼
        writingfinish2.setSafeOnClickListener {
            timer.cancel()
            var prevData: PathTraceData? = null
            if(!isdraw)
            {
                Toast.makeText(this, "직선을 그리고 다음버튼을 눌러주세요", Toast.LENGTH_LONG).show()
            }
            else
            {
                loading()
                if (lorr) {
                    image_path = "$uid/$Clinic_ID/Line/Right/Image/$count.jpg"
                } else {
                    image_path = "$uid/$Clinic_ID/Line/Left/Image/$count.jpg"
                }
                //정해진 이미지 레퍼런스를 설정한다
                line_ref = storageRef.child(image_path)
                //삭제를 위한 path저장
                firebase_path = firebaseDatabase.getReference("URL List").child(uid).child(Clinic_ID).child("Path")
                firebase_path.push().setValue(line_ref.path)
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
                } catch (e: java.io.FileNotFoundException) {
                    e.printStackTrace()
                }
                val baos = ByteArrayOutputStream()
                captureView.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                var uri = Uri.fromFile(File("sdcard/Download/"))
                val data = baos.toByteArray()
                //******************************** save image local ************************************
                try {
                    onCap(captureView)
                } catch (e: java.lang.Exception) {

                } finally {
                    captureView.recycle();
                }
                //******************************** save image firebase ************************************
                var uploadTask = line_ref.putBytes(data)
                uploadTask.addOnFailureListener() {
                    Toast.makeText(this, "image uploading failed", Toast.LENGTH_SHORT).show()
                }
                uploadTask.addOnSuccessListener() {
                    val urlTask = it.getStorage().getDownloadUrl()
                    while (!urlTask.isSuccessful);
                    val downloadUrl = urlTask.result
                    val downurl = downloadUrl.toString()

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
                    val data_path = image_path.replace("Image", "Data").replace("jpg", "csv")
                    val intent = Intent(this, AnalysisActivity::class.java)
                    intent.putExtra("filename", "${Clinic_ID}_$filename.csv")
                    intent.putExtra("path1", path1)
                    intent.putExtra("path", path)
                    intent.putExtra("Clinic_ID", Clinic_ID)
                    intent.putExtra("PatientName", PatientName)
                    intent.putExtra("task", "LineTask")
                    intent.putExtra("left", left);//left : 0, right : 1
                    intent.putExtra("right_spiral", "null")
                    intent.putExtra("spiral_result", spiral_result)
                    intent.putExtra("left_spiral_result", left_spiral_result)
                    intent.putExtra("writing_downurl", writing_downurl)
                    intent.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl)
                    intent.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl)
                    intent.putExtra("crts_num", crts_num)
                    intent.putExtra("data_path", data_path)
                    intent.putExtra("line_downurl", downurl)
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
            isdraw = true
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
        private val startX = this.resources.displayMetrics.widthPixels / 5 * 2
        private val startY = 100

        private val finalX = this.resources.displayMetrics.widthPixels / 5 * 2
        private val finalY = this.resources.displayMetrics.heightPixels - 100


        //private val theta = FloatArray(720) { (it * (Math.PI / 180)).toFloat() }
        private val basePath = Path()
        private val basePaint = Paint()

        init {
            basePaint.style = Paint.Style.STROKE
            basePaint.strokeWidth = 10f
            basePaint.alpha = 50
            basePaint.isAntiAlias = true
            fitting.startX = startX
            fitting.startY = startY
        }

        override fun onDraw(canvas: Canvas) {
            basePath.moveTo(startX.toFloat(), startY.toFloat())
            basePath.lineTo(finalX.toFloat(), finalY.toFloat())

            canvas.drawPath(basePath, basePaint)
        }
    }

    @Throws(Exception::class)
    private fun onCap(bm: Bitmap) {
        try {
            val imgFile = "line.jpg"
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

    private fun readData(firebaseCallback: FirebaseCallback)
    {
        //원래 URL이 들어있는 개수를 구함 스토리지의 저장명으로 활용될 예정
        firebase_line_url.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                count = dataSnapshot.childrenCount.toInt()
                // 0번, 1번, 2번.. 순으로 저장
                firebaseCallback.onCallBack(count)
                Log.v("직선 로그 3", ""+count)

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }

    private interface FirebaseCallback{
        fun onCallBack(cnt : Int)
    }

    fun loading() {
        //로딩
        android.os.Handler().postDelayed(
                {
                    progressDialog = ProgressDialog(this@Line)
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

    override fun onBackPressed() {
        /*if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            if (path1.equals("main")) {
                val intent = Intent(this@Line, Spiral_Task_Select::class.java)
                intent.putExtra("Clinic_ID", Clinic_ID)
                intent.putExtra("PatientName", PatientName)
                intent.putExtra("path", path1)
                intent.putExtra("task", "line");
                startActivity(intent)
                finish()
            } else {
                onBackPressed();
                finish()
            }
        }*/
        Toast.makeText(this, "이전 화면으로 돌아가고 싶으시다면 ' < ' 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();

    }

   /* inner class VerticalTextView(context:Context, attrs:AttributeSet):android.support.v7.widget.AppCompatTextView(context, attrs) {
        protected override fun onMeasure(widthMeasureSpec:Int, heightMeasureSpec:Int) {
            super.onMeasure(heightMeasureSpec, widthMeasureSpec)
            setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth())
        }
        // 텍스트뷰 가로 입력
        protected override fun onDraw(canvas:Canvas) {
            // Custom View를 생성할때 원하는 폰트 , 색상 , 크기 설정
            val textPaint = getPaint()
            textPaint.setColor(getCurrentTextColor())
            textPaint.drawableState = getDrawableState()
            // View를 그리위한 객체 Canvas
            canvas.save()
            canvas.translate(0F, getHeight().toFloat())
            canvas.rotate((-90).toFloat()) // 90도로 회전
            canvas.translate(getCompoundPaddingLeft().toFloat(), getExtendedPaddingTop().toFloat())
            getLayout().draw(canvas)
            canvas.restore() // Canvas 상태를 복원합니다.
        }
    }*/





}
