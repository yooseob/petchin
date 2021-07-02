package kr.petchin.petchin.ui.talk

import androidx.lifecycle.ViewModelProviders
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ListView
import kr.petchin.petchin.R
import kr.petchin.petchin.adapter.talk_adapter
import kr.petchin.petchin.data.talkListData
import kr.petchin.petchin.lib.FileCache
import kr.petchin.petchin.lib.myclass
import kr.petchin.petchin.task.getListTalk
import org.json.JSONObject


class TalkFragment : Fragment() {

    private lateinit var talkViewModel: TalkViewModel
    private lateinit var gAdapter : talk_adapter
    private lateinit var mContext : Context
    private lateinit var gListView : ListView
    private lateinit var root : View
    private var isLoading : Boolean = false
    private var keyword : String = ""
    private var chResult: String? = null
    private var totalRecord : Int = 0
    private var page : Int = 1
    private var lockListView : Boolean = false
    private var talkList = arrayListOf<talkListData>()
    private lateinit var activityMode : String
    private lateinit var myClass : myclass

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        talkViewModel =
                ViewModelProviders.of(this).get(TalkViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_talk, container, false)

        /*
        var intent = getIntent()
        if (intent.hasExtra("mode")) {
            activityMode = intent.getStringExtra("mode")
        }
        */
        setUI ()
        return root
    }

    private fun setUI () : Unit {
        mContext = requireContext()
        myClass = myclass()
        gListView = root.findViewById(R.id.list) as ListView
        //talkList = arrayListOf<talkListData>(talkListData("dd", "aa", "dd", "www"))

        gAdapter = talk_adapter(mContext, R.layout.list_talk_row, talkList)

        gListView.adapter = gAdapter


        gListView.setOnScrollListener(scrollListener);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            gListView.focusable = ListView.NOT_FOCUSABLE
        }
        gListView.itemsCanFocus = false
        gListView.choiceMode = ListView.CHOICE_MODE_SINGLE


        val values = ContentValues()
        values.put("fType", "list")
        val networkTask = getListTalk.BackgroundTask(null, this,"http://3.36.60.21/api/getrecipelist.asp",values)



    }

    fun setListData(result: String) : Unit {

        if(result!=""){

            talkList.clear()
            lockListView = true
            chResult = result

            val json : JSONObject = JSONObject(chResult)
            totalRecord = json.getInt("recordCount")
            var array = json.getJSONArray("entry")

            if(totalRecord>0){

                for (i in 0 until array.length()){

                    var obj : JSONObject = array.getJSONObject(i)
                    var cList = talkListData(obj.getInt("idx"),
                        obj.getString("r_name"),
                        obj.getString("r_mainImg"),
                        obj.getString("r_intro"),
                        obj.getString("regdate"))
                    talkList.add(cList)
                }
                gAdapter.notifyDataSetChanged()
                lockListView = false
            }

        }
    }

    fun setListAddItems(result: String) : Unit {


        if(result!=""){

            lockListView = true
            chResult = result

            val json : JSONObject = JSONObject(chResult)
            totalRecord = json.getInt("recordCount")
            var array = json.getJSONArray("entry")

            if(totalRecord>0){

                for (i in 0 until array.length()){

                    var obj : JSONObject = array.getJSONObject(i)
                    var cList = talkListData(obj.getInt("idx"),
                        obj.getString("r_name"),
                        obj.getString("r_mainImg"),
                        obj.getString("r_intro"),
                        obj.getString("regdate"))
                    talkList.add(cList)
                }
                gAdapter.notifyDataSetChanged()
                lockListView = false
            }

        }



    }

    private val scrollListener: AbsListView.OnScrollListener =
        object : AbsListView.OnScrollListener {

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }

            override fun onScroll(
                view: AbsListView,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {
                if (keyword.equals("")) {
                    val count = totalItemCount - visibleItemCount
                    if (totalItemCount > 0 && firstVisibleItem >= count && lockListView === false) {
                        if (totalRecord > 0) {
                            page++
                            //new getListRecipeTask((act_recipeList) mContext).execute("http://luckybebe.entogether.com/api/getrecipelist.asp?p="+page,"add");
                            val mUrl =
                                "http://3.36.60.21/api/getrecipelist.asp?p=$page"
                            val values = ContentValues()
                            values.put("fType", "add")
                            val networkTask = getListTalk.BackgroundTask(null, this@TalkFragment, mUrl, values)
                        } else {
                            myClass.MessageBox(mContext, "더 이상 게시물이 없습니다.")
                        }
                    }
                }
                /*
                val lastInScreen = firstVisibleItem + visibleItemCount
                if (lastInScreen == totalItemCount && !isLoading) {
                    //loadMoreItems(totalItemCount - 1)
                    Toast.makeText(mContext, "토스트 메세지 띄우기 입니다.", Toast.LENGTH_SHORT).show()
                    isLoading = true
                    //adapter.addAll(list); 쓰레드완료시
                    //adapter.notifyDataSetChanged ();
                    //isLoading = false;
                }*/
            }
        }





}