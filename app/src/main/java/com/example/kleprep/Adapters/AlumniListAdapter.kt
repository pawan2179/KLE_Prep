package com.example.kleprep.Adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.kleprep.Interface.AlumniListAdapterInterface
import com.example.kleprep.R
import com.example.kleprep.data.Alumni

class AlumniListAdapter(var alumniList: ArrayList<Alumni>, var alumniListAdapterInterface: AlumniListAdapterInterface): RecyclerView.Adapter<AlumniListAdapter.AlumniListViewModel>() {
    inner class AlumniListViewModel(itemView: View): RecyclerView.ViewHolder(itemView) {
        val alumniName: TextView = itemView.findViewById(R.id.QuestionTitleTv)
        val alumniContact: TextView = itemView.findViewById(R.id.QuestionEditorialTv)
        val companyTv : TextView = itemView.findViewById(R.id.companyTagTv)

        val itemClickListener = itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                alumniListAdapterInterface.onItemClick(adapterPosition)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumniListViewModel {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return AlumniListViewModel(itemView)
    }

    override fun onBindViewHolder(holder: AlumniListViewModel, position: Int) {
        holder.alumniName.text = alumniList[position].name
        holder.alumniContact.text = alumniList[position].email
        Log.d("Alumni", "${alumniList[position]}")
        holder.companyTv.text = "Batch : ${alumniList[position].batch}, Company : ${alumniList[position].company.toString()}"
    }

    override fun getItemCount(): Int {
        return alumniList.size
    }
}