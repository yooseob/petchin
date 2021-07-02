package kr.petchin.petchin.lib

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.telephony.TelephonyManager
import android.widget.Toast
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class myclass {

    private var t: Toast? = null
    var DB: SQLiteDatabase? = null
    private val dialog: AlertDialog.Builder? = null
    var mPopupDlg: DialogInterface? = null
    private val telManager: TelephonyManager? = null

    fun MessageBox(context: Context?, message: String?) {
        if (t == null) {
            t = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        } else {
            t!!.setText(message)
        }
        t!!.show()
    }

    fun httpConnect(_url: String?, _params: ContentValues?): String? {


        var urlConn: HttpURLConnection? = null

        val sbParams = StringBuffer()


        if (_params == null) sbParams.append("") else {

            var isAnd = false

            var key: String
            var value: String
            for ((key1, value1) in _params.valueSet()) {
                key = key1
                value = value1.toString()


                if (isAnd) sbParams.append("&")
                sbParams.append(key).append("=").append(value)


                if (!isAnd) if (_params.size() >= 2) isAnd = true
            }
        }

        try {
            val url = URL(_url)
            urlConn = url.openConnection() as HttpURLConnection


            urlConn.requestMethod = "POST"
            urlConn.setRequestProperty("Accept-Charset", "UTF-8")
            urlConn.setRequestProperty(
                "Context_Type",
                "application/x-www-form-urlencoded;cahrset=UTF-8"
            )


            val strParams =
                sbParams.toString()
            val os: OutputStream = urlConn.getOutputStream()
            os.write(strParams.toByteArray(charset("UTF-8")))
            os.flush()
            os.close()


            if (urlConn.getResponseCode() !== HttpURLConnection.HTTP_OK) return ""


            val reader = BufferedReader(InputStreamReader(urlConn.getInputStream(), "UTF-8"))


            var line: String?
            var page: String? = ""


            while (reader.readLine().also { line = it } != null) {
                page += line
            }
            return page
        } catch (e: MalformedURLException) { // for URL.
            e.printStackTrace()
        } catch (e: IOException) { // for openConnection().
            e.printStackTrace()
        } finally {
            urlConn?.disconnect()
        }
        return ""
    }
}