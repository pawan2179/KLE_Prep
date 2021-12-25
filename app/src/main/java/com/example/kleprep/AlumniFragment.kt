package com.example.kleprep

import android.os.Bundle
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kleprep.Adapters.AlumniAdapter
import com.example.kleprep.databinding.FragmentAlumniBinding
import com.example.kleprep.databinding.FragmentQuestionBinding
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding: FragmentAlumniBinding
private lateinit var database: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference

/**
 * A simple [Fragment] subclass.
 * Use the [AlumniFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlumniFragment : Fragment() {
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
        //return inflater.inflate(R.layout.fragment_alumni, container, false)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_alumni, container, false)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Alumni")
        var companyList = ArrayList<String>()

        var recyclerView = binding.alumniRecyclerView
        recyclerView.adapter = AlumniAdapter(companyList)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                companyList.clear()
                snapshot.children.forEach{
                    companyList.add("${it.key}")
                }
                recyclerView.adapter!!.notifyDataSetChanged()
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
         * @return A new instance of fragment AlumniFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlumniFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}