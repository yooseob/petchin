package kr.petchin.petchin.ui.friend


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import kr.petchin.petchin.R
import kr.petchin.petchin.data.chatData
import java.util.*
import kotlin.collections.ArrayList


class FriendsFragment : Fragment() {

    private lateinit var friendsViewModel: FriendsViewModel
    val arrayOfListView = ArrayList<String>()
    val list: ArrayList<String> = ArrayList()
    private var chatList = arrayListOf<chatData>()
    private lateinit var mContext : Context
    private val firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = firebaseDatabase.getReference()
    private var ChatData = arrayListOf<chatData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        friendsViewModel =
                ViewModelProviders.of(this).get(FriendsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_friend, container, false)

        mContext = requireContext()

        val lstView: ListView = root.findViewById(R.id.lstChat)
        val editText: EditText = root.findViewById(R.id.edChatSend)
        val btnSend: Button = root.findViewById(R.id.btnChatSend)


        val adapter = ArrayAdapter<chatData>(
            mContext,
            android.R.layout.simple_list_item_1,
            chatList
        )
        lstView.adapter = adapter

        var userName = "user" + Random().nextInt(10000)

        btnSend.setOnClickListener { view ->
            val ChatData = chatData(userName, editText.text.toString()) // 유저 이름과 메세지로 chatData 만들기
            databaseReference.child("message").push().setValue(ChatData) // 기본 database 하위 message라는 child에 chatData를 list로 만들기
            editText.setText("")
        }


        databaseReference.child("message").addChildEventListener(object : ChildEventListener {
            // message는 child의 이벤트를 수신합니다.
            override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                Log.d("DDDDD", "onChildAdded: " + dataSnapshot.value)


                //val messageItem: chatData? = dataSnapshot.getValue(chatData::class.java)
                //adapter.add(messageItem)

                val i: Iterator<*> = dataSnapshot.children.iterator()
                while (i.hasNext()) {

                    var chat_user: String? = (i.next() as DataSnapshot).value as String?
                    var chat_msg: String? = (i.next() as DataSnapshot).value as String?
                    val ChatData = chatData(chat_msg, chat_msg)
                    Log.d("333333333333", "onChildAdded: $chat_user")
                    adapter.add(ChatData)
                }

                adapter.notifyDataSetChanged()

                //adapter.add(ChatData.userName + ": " + ChatData.message.toString()) // adapter에 추가합니다.
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {
                Log.d("MainActivity", "ChildEventListener - onChildChanged : $s")
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Log.d(
                    "MainActivity",
                    "ChildEventListener - onChildRemoved : " + dataSnapshot.getKey()
                )
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {
                Log.d("MainActivity", "ChildEventListener - onChildMoved$s")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(
                    "MainActivity",
                    "ChildEventListener - onCancelled" + databaseError.getMessage()
                )
            }
        })

        return root
    }
}