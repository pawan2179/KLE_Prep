package com.example.kleprep

import android.app.Activity.RESULT_OK
import android.content.ContentValues
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
import com.example.kleprep.R
import com.example.kleprep.data.Alumni
import com.example.kleprep.data.AlumniTeam
import com.example.kleprep.databinding.FragmentAddAlumniBinding
import com.google.android.gms.common.internal.Objects
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import org.json.JSONObject
import java.io.*
import java.lang.Error
import java.nio.charset.Charset

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding: FragmentAddAlumniBinding
private lateinit var database: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference
private const val REQ_CODE = 1000
private const val FILE_NAME = "file.csv"

/**
 * A simple [Fragment] subclass.
 * Use the [AddAlumniFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddAlumniFragment : Fragment() {
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
        //return inflater.inflate(R.layout.fragment_add_alumni, container, false)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_alumni, container, false)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Alumni")

        var alumniList =  ArrayList<Alumni>()

        binding.btnUploadJSON.setOnClickListener {

            if(binding.alumniCompanyNameEt.text.isNullOrEmpty() || binding.alumniJSONListEt.text.isNullOrEmpty()) {
                Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show()
            }
            val jsonString = binding.alumniJSONListEt.text.toString()
            try {
                val obj = JSONObject(jsonString)
                val alumniGroup = obj.getJSONArray("alumni")
                for(i in 0 until alumniGroup.length()) {
                    val alumni = alumniGroup.getJSONObject(i)
                    val name = alumni.getString("name")
                    val batch = alumni.getString("batch")
                    val email = alumni.getString("email")
                    val company = alumni.getString("company name")
                    val mobile = alumni.getString("mobile")

                    val newAlumni = Alumni(batch, name, company, email, mobile)

                    alumniList.add(newAlumni)
                }
                Toast.makeText(activity, "Saving data in database", Toast.LENGTH_SHORT).show()
                addAlumniList(alumniList)
            } catch (err: Error) {
                Log.e("Adding Alumni", "$err")
            }
            //Log.i("Alumni", "$alumniList")
        }


        return binding.root
    }

    private fun addAlumniList(alumniList: ArrayList<Alumni>) {
        val companyReference = databaseReference.child(binding.alumniCompanyNameEt.text.toString())
        for(i in 0 until alumniList.size) {
            val newAlumniReference = companyReference.push()
            newAlumniReference.setValue(alumniList[i])
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
         * @return A new instance of fragment AddAlumniFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddAlumniFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}