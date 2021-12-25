package com.example.kleprep

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kleprep.Adapters.AlumniListAdapter
import com.example.kleprep.Interface.AlumniListAdapterInterface
import com.example.kleprep.data.Alumni
import com.example.kleprep.databinding.FragmentAlumniListBinding
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var database: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference
private lateinit var binding: FragmentAlumniListBinding
var alumniList = ArrayList<Alumni>()
/**
 * A simple [Fragment] subclass.
 * Use the [AlumniListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlumniListFragment : Fragment(), AlumniListAdapterInterface {
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
        var inputText = arguments?.getString("company_name")
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_alumni_list, container, false)
        //return inflater.inflate(R.layout.fragment_alumni_list, container, false)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference("Alumni").child(inputText!!)

        binding.companyTitleTv.text = inputText

        var recyclerView = binding.alumniNameRecyclerView
        recyclerView.adapter = AlumniListAdapter(alumniList, this)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                alumniList.clear()
                if(snapshot.exists()) {
                    for(alumni in snapshot.children) {
                        val name = alumni.child("name").value.toString()
                        val email = alumni.child("email").value.toString()
                        val companyName = alumni.child("company").value.toString()
                        val mobile = alumni.child("mobile").value.toString()
                        val batch = alumni.child("batch").value.toString()

                        val newAlumni = Alumni(batch, name, companyName, email, mobile)
                        Log.i("Value", "$newAlumni")
                        alumniList.add(newAlumni)
                        recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }
                Log.i("Value", "${alumniList}")
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
         * @return A new instance of fragment AlumniListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlumniListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", alumniList[position].email, null))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Request to help through KLE Prep")
        intent.putExtra(Intent.EXTRA_EMAIL, alumniList[position].email)
        if(context?.let { intent.resolveActivity(it.packageManager) } != null) {
            startActivity(Intent.createChooser(intent, "Send mail..."))
        }
    }
}