package kr.petchin.petchin

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import kr.petchin.petchin.lib.myclass


class act_permission : AppCompatActivity(){

    private var mContext: Context? = null
    private val PACKAGE_URL_SCHEME = "package:"
    private val PERMISSON = 1
    private var myClass: myclass? = null
    private lateinit var act_Splash : act_splash


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.act_splash);
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        )
        mContext = this
        myClass = myclass()
        act_Splash = act_splash()
        grantExternalStoragePermission()
    }


    private fun grantExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                act_Splash.permissionBG(true)
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA
                    ),
                    PERMISSON
                )
            } else {
                // 항상허용
                setResult(1)
                finish()
            }
        } else {
            //Intent intent = new Intent();
            setResult(1)
            finish()
        }
    }

    private fun showPermissionSet() {

        val Builder: AlertDialog.Builder =
            AlertDialog.Builder(mContext, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth)
        Builder.setTitle("필수권한 설정")
        Builder.setMessage("앱에 실행에 필요한 필수권한을 허용해주세요.")
        Builder.setPositiveButton("설 정", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse(PACKAGE_URL_SCHEME + packageName)
            startActivity(intent)
        })
        Builder.setNeutralButton("닫 기", DialogInterface.OnClickListener { dialog, which ->
            act_Splash.finish()
            finish()
        })
        Builder.show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSON -> {
                if( grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    act_Splash.permissionBG(false)
                    setResult(1)
                    finish()
                }else {
                    showPermissionSet()
                }
            }
        }
    }
    /*
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>?,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSON -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                (act_splash.mContext as act_splash).permissionBG(false)
                setResult(1)
                finish()
            } else {
                showPermissionSet()
            }
        }
    }*/
}