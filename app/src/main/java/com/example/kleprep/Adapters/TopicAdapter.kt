package com.example.kleprep.Adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kleprep.QuestionFragment
import com.example.kleprep.R

class TopicAdapter(val topicList: ArrayList<String>, val topicQuestionCount: ArrayList<Int>) : RecyclerView.Adapter<TopicAdapter.TopicsViewHolder>() {
    class TopicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.itemTopicTitleTv)
        val questionCount: TextView = itemView.findViewById(R.id.itemTopicQuestionCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.topic_item, parent, false)
        return TopicsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopicsViewHolder, position: Int) {
        holder.title.text = topicList[position]
        holder.questionCount.text = "Question Count = ${topicQuestionCount[position]}"

        holder.itemView.setOnClickListener (object : View.OnClickListener{
            override fun onClick(view : View?) {
                val activity = view!!.context as AppCompatActivity
                val questionFragment = QuestionFragment()
                var bundle = Bundle()
                bundle.putString("name", topicList[position])
                questionFragment.arguments = bundle
                activity.supportFragmentManager.beginTransaction().replace(R.id.frameLayout, questionFragment).addToBackStack(null).commit()
            }
        })
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

}

