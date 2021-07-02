package kr.petchin.petchin

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class act_splash : AppCompatActivity() {

    lateinit var mContext : Context
    /*
    init{
        instance = this
    }

    companion object {
        lateinit var instance: act_splash
        fun mContex() : Context {
            return instance.applicationContext
        }


    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mContext = this

        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val ni: NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = ni?.isConnectedOrConnecting == true

        if (!isConnected) {
            val intent = Intent(this, MainActivity::class.java)
            val dlg: AlertDialog.Builder = AlertDialog.Builder(this@act_splash,  android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
            dlg.setTitle("연결 오류") //제목
            dlg.setMessage("인터넷 연결을 확인하세요.") // 메시지
            dlg.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss() // 닫기
                finish()
            })
            dlg.show()

            /*
            val alert_internet_status: AlertDialog.Builder =
                AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT)
            alert_internet_status.setTitle("연결 오류")
            alert_internet_status.setMessage("인터넷 연결을 확인하세요.")
            alert_internet_status.setPositiveButton("닫기",
                DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss() // 닫기
                    finish()
                })
            alert_internet_status.show()
            */

        }else{
            val in2 = Intent(mContext, act_permission::class.java)
            in2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val mNotificationManager =
                    getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                val id = "petchin"
                val name: CharSequence = "펫"
                val description = "펫친 알림"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val mChannel = NotificationChannel(id, name, importance)
                mChannel.description = description
                mChannel.enableLights(false)
                mChannel.enableVibration(false)
                //mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                mNotificationManager.createNotificationChannel(mChannel)
            }
            startActivityForResult(in2, 1)
        }
    }

    fun init() : Unit {


        val extras = Bundle()
        extras.putString("idx", "1")
        extras.putString("pType", "1")
        val in2 = Intent(mContext, MainActivity::class.java)
        in2.putExtras(extras)
        in2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        mContext.startActivity(in2)
        finish()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            1 -> init()
            else -> {
                finish()
            }
        }
    }
    fun permissionBG(key: Boolean) {
        if (key) {
            //act_splash.setBackgroundResource(R.drawable.permission)
        } else {
            //act_splash.setBackgroundResource(R.drawable.splash)
        }
    }


}