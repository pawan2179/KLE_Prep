package com.example.kleprep

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.kleprep.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var mAuth: FirebaseAuth? = null
    private var database: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database!!.getReference("Users")

        loadMainActivity()

        binding.signInBtn.setOnClickListener{
            if(binding.userEmailET.text.toString().isNullOrEmpty()
            or binding.userPasswordET.text.toString().isNullOrEmpty()) {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
            }
            else {
                loginToFirebase(binding.userEmailET.text.toString(),
                binding.userPasswordET.text.toString())
            }
        }
    }

    fun loginToFirebase(userEmail: String, userPassword: String) {
        mAuth!!.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener (this){ task->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    saveUser(userEmail, mAuth!!.uid!!)
                    loadMainActivity()
                }
                else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                    Log.e("error", "${task.exception!!.message}")
                }
            }
    }

    fun loadMainActivity() {
        val currentUser: FirebaseUser? = mAuth!!.currentUser
        if(currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)
            intent.putExtra("name", binding.userNameET.text.toString())
            intent.putExtra("batch", binding.userBatchET.text.toString())
            startActivity(intent)
        }
    }

    fun saveUser(email : String, uid : String) {
        val splitMail = splitString(email);
        databaseReference!!.child(splitMail).setValue(uid).addOnSuccessListener {
            Toast.makeText(this, "User saved successfully", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save user", Toast.LENGTH_SHORT).show()
            }
    }

    fun splitString(str: String) : String{
        val splitMail = str.split("@")
        return splitMail[0]
    }

    override fun onStart() {
        super.onStart()
        loadMainActivity()
    }
}