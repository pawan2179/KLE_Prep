package com.example.kleprep.Adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kleprep.AlumniListFragment
import com.example.kleprep.R

class AlumniAdapter(val companyList: ArrayList<String>) : RecyclerView.Adapter<AlumniAdapter.AlumniViewHolder>() {
    inner class AlumniViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title : TextView= itemView.findViewById(R.id.itemTopicTitleTv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumniViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        return AlumniViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlumniViewHolder, position: Int) {
        holder.title.text = companyList[position]
        holder.itemView.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                val alumniListFragment = AlumniListFragment()
                val bundle = Bundle()
                bundle.putString("company_name", companyList[position])
                alumniListFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction().replace(R.id.frameLayout, alumniListFragment).addToBackStack(null).commit()
            }
        })
    }

    override fun getItemCount(): Int {
        return companyList.size
    }
}