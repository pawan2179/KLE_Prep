package com.example.kleprep

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kleprep.Adapters.QuestionsAdapter
import com.example.kleprep.Interface.QuestionRecyclerViewInterface
import com.example.kleprep.R
import com.example.kleprep.data.Questions
import com.example.kleprep.databinding.FragmentQuestionBinding
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding: FragmentQuestionBinding
private lateinit var database: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment(), QuestionRecyclerViewInterface {
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
        var inputText = arguments?.getString("name")
        Toast.makeText(activity, "${inputText}", Toast.LENGTH_SHORT).show()

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_question, container, false)
        //return inflater.inflate(R.layout.fragment_question, container, false)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Questions").child(inputText!!)
        var questionList = ArrayList<Questions>()

        binding.QuestionsTitleTv.text = inputText

        var recyclerView = binding.recyclerView
        recyclerView.adapter = QuestionsAdapter(questionList, this)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        databaseReference.addValueEventListener( object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                questionList.clear()
                if(snapshot.exists()) {
                    for(question in snapshot.children) {
                        var topic: String = question.child("topic").value.toString()
                        var title: String = question.child("title").value.toString()
                        var link: String = question.child("link").value.toString()
                        var resourceLink: String = question.child("resourceLink").value.toString()
                        var companyTag: String = question.child("companyTag").value.toString()

                        val newQuestion: Questions = Questions(topic, title, link, resourceLink, companyTag)
                        Log.i("Question", "${newQuestion}")
                        questionList.add(newQuestion)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }
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
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, "Item at $position clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onTextViewClicked(url: String) {
        //Log.i("Link", url)
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}