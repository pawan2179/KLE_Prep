package com.example.kleprep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kleprep.Adapters.TopicAdapter
import com.example.kleprep.databinding.FragmentPracticeBinding
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var binding: FragmentPracticeBinding
private lateinit var database: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference

/**
 * A simple [Fragment] subclass.
 * Use the [PracticeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PracticeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_practice, container, false)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_practice, container, false)
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Questions")
        var topicList = ArrayList<String>()
        var topicQuestionCount = ArrayList<Int>()

        var recyclerView = binding.recyclerView
        recyclerView.adapter = TopicAdapter(topicList, topicQuestionCount)
        recyclerView.layoutManager = LinearLayoutManager(activity)




        val questionListener = databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                topicList.clear()
                topicQuestionCount.clear()
                snapshot.children.forEach() {
                    topicList.add("${it.key}")
                    topicQuestionCount.add(it.key!!.length)
                    //Log.e("Practice", "${it.key} , ${it.value}")
                }
                recyclerView.adapter!!.notifyDataSetChanged()
                //Log.e("Topics", "${topicList}")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PracticeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PracticeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}