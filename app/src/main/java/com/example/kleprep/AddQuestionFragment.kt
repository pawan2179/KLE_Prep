package com.example.kleprep

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.kleprep.data.Alumni
import com.example.kleprep.data.Questions
import com.example.kleprep.databinding.FragmentAddQuestionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import java.lang.Error

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddQuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddQuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentAddQuestionBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_question, container, false)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Questions")
        var questionList = ArrayList<Questions>()

        binding.btnSubmitQuestion.setOnClickListener {
            if(binding.questionDetailsEt.text.isNullOrEmpty() || binding.topicNameEt.text.isNullOrEmpty()) {
                Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
            }

            val jsonString = binding.questionDetailsEt.text.toString()
            try {
                val obj = JSONObject(jsonString)
                val questionGroup = obj.getJSONArray("questions")
                for(i in 0 until questionGroup.length()) {
                    val question = questionGroup.getJSONObject(i)
                    val title = question.getString("Questions")
                    val link = question.getString("Link")
                    val resourceLink = question.getString("Resource Link")
                    val companyTag = question.getString("Company Tags")
                    val topic = binding.topicNameEt.text.toString()

                    val newQuestion = Questions(topic, title, link, resourceLink, companyTag)
                    questionList.add(newQuestion)
                }
                Toast.makeText(activity, "Saving data in database", Toast.LENGTH_SHORT).show()
                addQuestionList(questionList)
            } catch (err: Error) {
                Log.e("Adding Question", "$err")
            }
        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_add_question, container, false)
    }

    private fun addQuestionList(questionList: ArrayList<Questions>) {
        val questionReference = databaseReference.child(binding.topicNameEt.text.toString())
        for(i in 0 until questionList.size) {
            val newQuestionReference = questionReference.push()
            newQuestionReference.setValue(questionList[i])
                .addOnFailureListener {
                    Toast.makeText(activity, "Some Error occured", Toast.LENGTH_SHORT).show()
                }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddQuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddQuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}