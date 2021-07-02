package kr.petchin.petchin.ui.myinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kr.petchin.petchin.R

class MyinfoFragment : Fragment() {

    private lateinit var myInfoViewModel: MyInfoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        myInfoViewModel =
                ViewModelProviders.of(this).get(MyInfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myinfo, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        myInfoViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}