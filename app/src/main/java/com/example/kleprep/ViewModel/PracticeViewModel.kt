package com.example.kleprep.ViewModel

import androidx.lifecycle.ViewModel
import com.example.kleprep.data.Questions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class PracticeViewModel: ViewModel() {
    var questionsList = ArrayList<Questions>()
    var database = FirebaseDatabase.getInstance()
}