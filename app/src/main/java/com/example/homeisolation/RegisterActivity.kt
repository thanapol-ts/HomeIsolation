package com.example.homeisolation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var txtEmailCreate: EditText
    lateinit var txtPasswordCreate: EditText
    lateinit var txtName: EditText
    lateinit var txtAddress: EditText
    lateinit var txtPhone: EditText

    lateinit var buttonSubmit: Button

    lateinit var email:String
    lateinit var password:String
    lateinit var name:String
    lateinit var address:String
    lateinit var phone:String

    private var mAuth: FirebaseAuth? = null
    lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtEmailCreate = findViewById<EditText>(R.id.txtEmailCreate)
        txtPasswordCreate = findViewById<EditText>(R.id.txtPasswordCreate)
        txtName = findViewById<EditText>(R.id.txtName)
        txtAddress = findViewById<EditText>(R.id.address)
        txtPhone = findViewById<EditText>(R.id.phone)

        buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://homeisolationo2auth-default-rtdb.asia-southeast1.firebasedatabase.app/")
        buttonSubmit.setOnClickListener {
            if(TextUtils.isEmpty(txtEmailCreate.text.toString()) || TextUtils.isEmpty(txtPasswordCreate.text.toString())){
                Toast.makeText(applicationContext,"Please Enter your E-mail and password",Toast.LENGTH_SHORT).show()
            }else {
                createAccount()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        var currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun createAccount() {
        email = txtEmailCreate.text.toString()
        password = txtPasswordCreate.text.toString()
        name = txtName.text.toString()
        address = txtAddress.text.toString()
        phone = txtPhone.text.toString()

        mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d("MyApp", "Create New User Success!")
                val user = mAuth!!.currentUser
                val databaseReference = database.reference.child("users").push()
                databaseReference.child("uid").setValue(user!!.uid)
                databaseReference.child("email").setValue(user.email)
                databaseReference.child("name").setValue(name)
                databaseReference.child("address").setValue(address)
                databaseReference.child("phone").setValue(phone)
                updateUI(user)
            } else {
                Log.w("MyApp", "Failure Process", task.exception)
                Toast.makeText(this@RegisterActivity, "Authentication Failed", Toast.LENGTH_SHORT)
                    .show()
                updateUI(null)
            }
        }
    }
    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            val uid = user.uid
            val email = user.email
            Toast.makeText(this@RegisterActivity,"Welcome: $email your ID is $uid",Toast.LENGTH_SHORT).show()
            val intentSession  = Intent(this,ListActivity::class.java)
            startActivity(intentSession)
        }
    }
}