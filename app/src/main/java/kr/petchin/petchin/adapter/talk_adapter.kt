package kr.petchin.petchin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kr.petchin.petchin.R
import kr.petchin.petchin.data.talkListData
import kr.petchin.petchin.lib.ImageLoader


class talk_adapter(
    val mCotext: Context,
    val layoutResource: Int,
    val listData: ArrayList<talkListData>
): BaseAdapter()
{
    private var mInflater : LayoutInflater
    private var mList : ArrayList<talkListData>
    private lateinit var imageLoader : ImageLoader
    init {
        mInflater = LayoutInflater.from(mCotext)
        mList = listData
        imageLoader = ImageLoader(mCotext)
    }

    override fun getView(position: Int, converterView: View?, parent: ViewGroup?): View? {

        var mData : talkListData = mList.get(position)
        val view : View?
        val holder: ViewHolder

        if(converterView == null){
            view = mInflater.inflate(R.layout.list_talk_row, null)

            holder = ViewHolder()
            holder.ivThumbnail = view.findViewById(R.id.list_view_row_thumbnail) as ImageView
            holder.ivNew = view.findViewById(R.id.list_view_row_today) as ImageView
            holder.txtTitle = view.findViewById(R.id.list_view_row_title) as TextView
            holder.txtIntro = view.findViewById(R.id.list_view_row_intro) as TextView
            holder.txtDate = view.findViewById(R.id.list_view_row_regdate) as TextView
            holder.lt = view.findViewById(R.id.linearClick) as LinearLayout


            view.tag = holder
        }else{
            view = converterView
            holder = view.tag as ViewHolder
        }
        if(mData != null){


            holder.ivThumbnail?.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    //TODO
                }
            })

            var imgURL : String = mData.img

            if(imgURL==null||imgURL=="")
            {
                holder.ivThumbnail?.setImageResource(R.drawable.noimg);
            }else{
                imageLoader.DisplayImage(imgURL, holder.ivThumbnail);
            }

            if (mData.title.length>30){
                holder.txtTitle?.text = mData.title.substring(0,30) + ".."
            }else{
                holder.txtTitle?.text = mData.title
            }

            if (mData.intro.length >30){
                holder.txtIntro?.text = mData.intro.substring(0,30) + ".."
            }else{
                holder.txtIntro?.text = mData.intro
            }
            holder.txtDate?.text = mData.regdate.substring(0,10)


        }



        return view
    }

    override fun getCount() : Int = mList.size
    override fun getItem(position: Int) : Any? = mList.get(position)
    override fun getItemId(position: Int): Long = position.toLong()

    private class ViewHolder {

        var txtTitle: TextView? = null
        var txtIntro: TextView? = null
        var txtDate: TextView? = null
        var ivThumbnail: ImageView? = null
        var ivNew: ImageView? = null
        var lt: LinearLayout? = null

    }
}