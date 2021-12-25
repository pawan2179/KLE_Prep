package com.example.kleprep

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.kleprep.databinding.FragmentContactBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var binding: FragmentContactBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : Fragment() {
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
        //return inflater.inflate(R.layout.fragment_contact, container, false)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_contact, container, false)

        val data = "This project is the Senior Design Project 2021-22 implemented by SDP Batch No. 22 [Prince Kumar,Rajnish Pandey, " +
                "Pawan Kumar Singh and Shivam Kumar Gupta.]\n\nSpecial thank to our senior design project guide Prof. Kiran M.R and Prof. Naveen K N for their constant support and guidance.\n\n\n" +
                "Admin Contact Details:\n" +
                "Prince Kumar: kumar.prince77213@gmail.com\n" +
                "Rajnish Pandey: rajnishbihta705@gmail.com\n" +
                "Pawan Kumar Singh: pawansingh4418@gmail.com\n" +
                "Shivam Kumar Gupta: shivamgupta@gmail.com\n\n\n\n"+
                "Guide Contact Details:\n" +
                "Kiran M.R: kiranmr@kletech.ac.in\n"+
                "Naveen K N: naveenkn@kletech.ac.in\n"

        binding.dataTv.text = data

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}