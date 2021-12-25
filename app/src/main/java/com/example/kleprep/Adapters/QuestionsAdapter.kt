package com.example.kleprep.Adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.kleprep.Interface.QuestionRecyclerViewInterface
import com.example.kleprep.R
import com.example.kleprep.data.Questions

class QuestionsAdapter(var questionList: ArrayList<Questions>, var questionInterface: QuestionRecyclerViewInterface): RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>() {

    private var questionRecyclerViewClickInterface : QuestionRecyclerViewInterface = questionInterface

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionNameTv : TextView = itemView.findViewById(R.id.QuestionTitleTv)
        val questionEditorialTv: TextView = itemView.findViewById(R.id.QuestionEditorialTv)
        val companyTagTv : TextView = itemView.findViewById(R.id.companyTagTv)

        val itemOnClick = itemView.setOnClickListener (object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (v != null) {
                    questionRecyclerViewClickInterface.onItemClick(v.id)
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return QuestionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.questionNameTv.text = questionList[position].title
        holder.questionEditorialTv.text = "Editorial"
        holder.companyTagTv.text = "Company Tags : ${questionList[position].companyTag}"

        holder.questionEditorialTv.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                questionRecyclerViewClickInterface.onTextViewClicked(questionList[position].resourceLink)
            }
        })
        holder.questionNameTv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                questionRecyclerViewClickInterface.onTextViewClicked(questionList[position].link)
            }
        })
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

}