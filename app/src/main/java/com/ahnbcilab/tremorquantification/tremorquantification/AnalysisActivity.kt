package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.ahnbcilab.tremorquantification.functions.main
import com.ahnbcilab.tremorquantification.functions.main1

class AnalysisActivity : AppCompatActivity() {
    private val filename: String by lazy { intent.extras.getString("filename") }
    var path1 : String = ""
    var Clinic_ID : String = ""
    var PatientName : String = ""
    var task: String = ""
    var right_spiral : String = ""
    var left : Int = 0  ;
    var data_path : String = ""
    var line_downurl : String = ""
    var spiral_downurl : String = ""
    var crts_right_spiral_downurl : String = ""
    var crts_left_spiral_downurl : String = ""
    var writing_downurl : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        val intent = intent
        left = intent.getIntExtra("left", -1) ;
        path1 = intent.getStringExtra("path1")
        Clinic_ID = intent.getStringExtra("Clinic_ID")
        PatientName = intent.getStringExtra("PatientName")
        task = intent.getStringExtra("task")
        var spiral_result = intent.getDoubleArrayExtra("spiral_result")
        var left_spiral_result = intent.getDoubleArrayExtra("left_spiral_result")
        var crts_num = intent.getStringExtra("crts_num")
        right_spiral = intent.getStringExtra("right_spiral")
        if (intent.hasExtra("left_spiral_downurl"))
            spiral_downurl = intent.getStringExtra("left_spiral_downurl")
        if (intent.hasExtra("right_spiral_downurl"))
            spiral_downurl = intent.getStringExtra("right_spiral_downurl")
        if (intent.hasExtra("line_downurl"))
            line_downurl = intent.getStringExtra("line_downurl")
        if (intent.hasExtra("crts_right_spiral_downurl"))
            crts_right_spiral_downurl = intent.getStringExtra("crts_right_spiral_downurl")
        if (intent.hasExtra("crts_left_spiral_downurl"))
            crts_left_spiral_downurl = intent.getStringExtra("crts_left_spiral_downurl")
        if (intent.hasExtra("writing_downurl"))
            writing_downurl = intent.getStringExtra("writing_downurl")
        if (intent.hasExtra("data_path"))
            data_path = intent.getStringExtra("data_path")


        val dialog = ProgressDialog(this)
        dialog.setMessage("Analysing...")
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", DialogInterface.OnClickListener {dialog, which -> run {
            dialog.dismiss()
            val cancel_Intent = Intent(this, PatientListActivity::class.java)
            cancel_Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(cancel_Intent)
        }})
        dialog.show()

        if(task.equals("SpiralTask")&&path1.equals("main")){//단독으로 나선을 수행했을 경우
            spiral_result = main.main("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID, data_path)
            dialog.dismiss()
            val intent1 = Intent(this, SpiralResultActivity::class.java)
            intent1.putExtra("spiral_result", spiral_result)
            intent1.putExtra("path1", path1)
            intent1.putExtra("left", left) ;
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            intent1.putExtra("spiral_downurl", spiral_downurl);
            startActivity(intent1)
            finish()
        }
        else if(task.equals("SpiralTask")&&path1.equals("CRTS")&&right_spiral.equals("no")){//crts 오른쪽 나선 끝난 기점
            spiral_result = main.main("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID, data_path)
            dialog.dismiss()
            val intent1 = Intent(this, Spiral::class.java)
            intent1.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl)
            intent1.putExtra("spiral_result", spiral_result)
            intent1.putExtra("path", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            intent1.putExtra("right_spiral", "yes")
            intent1.putExtra("writing_downurl", writing_downurl)
            intent1.putExtra("crts_num", crts_num)
            intent1.putExtra("lorr", false) //왼손이라는 표시를 해주어야 함
            startActivity(intent1)

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
        else if(task.equals("SpiralTask")&&path1.equals("CRTS")&&right_spiral.equals("yes")){//crts 왼손 끝난 기점
            left_spiral_result = main.main("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID, data_path)
            dialog.dismiss()
            val intent1 = Intent(this, Line::class.java)
            intent1.putExtra("spiral_result", spiral_result)
            intent1.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl)
            intent1.putExtra("crts_left_spiral_downurl", spiral_downurl)
            intent1.putExtra("left_spiral_result", left_spiral_result)
            intent1.putExtra("writing_downurl", writing_downurl)
            intent1.putExtra("path", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            intent1.putExtra("crts_num", crts_num)
            startActivity(intent1)
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
        //여기까지 나선! 그 다음 직선
        else if(task.equals("LineTask")&&path1.equals("CRTS")){
            var result1 = main1.main1("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID, data_path)
            dialog.dismiss()
            val intent1 = Intent(this, WritingResult::class.java)
            intent1.putExtra("crts_right_spiral_downurl", crts_right_spiral_downurl)
            intent1.putExtra("crts_left_spiral_downurl", crts_left_spiral_downurl)
            intent1.putExtra("line_result", result1)
            intent1.putExtra("left_spiral_result", left_spiral_result)
            intent1.putExtra("path1", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            intent1.putExtra("spiral_result", spiral_result)
            intent1.putExtra("crts_num", crts_num)
            intent1.putExtra("edit", "no")
            intent1.putExtra("writing_downurl", writing_downurl)
            intent1.putExtra("line_downurl", line_downurl)
            intent1.putExtra("left", left) ; //left : 0, right : 1
            startActivity(intent1)
            finish()

        }
        else{
            var result1 = main1.main1("${this.filesDir.path}/testData/$filename", applicationContext, Clinic_ID, data_path)
            dialog.dismiss()
            val intent1 = Intent(this, LineResultActivity::class.java)
            intent1.putExtra("line_result", result1)
            intent1.putExtra("path1", path1)
            intent1.putExtra("Clinic_ID", Clinic_ID)
            intent1.putExtra("PatientName", PatientName)
            intent1.putExtra("left", left) ;
            intent1.putExtra("line_downurl", line_downurl);
            startActivity(intent1)
            finish()
        }

    }
}
