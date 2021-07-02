package kr.petchin.petchin.task

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kr.petchin.petchin.lib.myclass
import kr.petchin.petchin.ui.talk.TalkFragment


class getListTalk {

    companion object {

        fun BackgroundTask(
            progressBar: ProgressBar?,
            //transaction: FragmentTransaction,
            fragment: TalkFragment,
            url: String,
            value: ContentValues
        ) {
            //onPreExcute

            if(progressBar!=null) {
                progressBar.visibility = View.VISIBLE
            }

            CoroutineScope(Dispatchers.Main).launch {
                // Show progress from UI thread
                var data : String = ""
                CoroutineScope(Dispatchers.Default).async {
                    // background thread
                    //var data = ""
                    val myClass = myclass()
                    data = myClass.httpConnect(url,value).toString()

                }.await()
                // UI data update from UI thread
                // Hide Progress from UI thread
                if(progressBar!=null) {
                    progressBar.visibility = View.GONE
                }


                var fType : String = value.getAsString("fType")

                if(fType=="add"){
                    fragment.setListAddItems(data)
                }else{
                    fragment.setListData(data)
                }

            }

            /*------------------------------------------------------------------------------
            CoroutineScope(Dispatchers.Main).launch {
            //doInBackground
                val Title = async(Dispatchers.Default) {
                    val document = Jsoup.connect(Url).timeout(3000).get()
                    val elements = document.select("title")
                    elements[0].text().toString()

                }.await()
                //onPostExecute
                replaceFragment(transaction, Title)
                if(progressBar!=null) {
                    progressBar.visibility = View.GONE
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                // Show progress from UI thread
                var data = ""
                CoroutineScope(Dispatchers.Default).async {
                    // background thread
                    data = loadNetworkSomething()
                }.await()
                // UI data update from UI thread
                // Hide Progress from UI thread
            }
            -------------------------------------------------------------------------------*/


        }

    }
}