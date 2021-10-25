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

class MainActivity : AppCompatActivity() {
    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var buttonRegister: Button
    lateinit var buttonForget: Button

    lateinit var email:String
    lateinit var password:String

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtEmail = findViewById<EditText>(R.id.txtEmail)
        txtPassword = findViewById<EditText>(R.id.txtPassword)
        buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonRegister = findViewById<Button>(R.id.buttonRegister)
        buttonForget = findViewById<Button>(R.id.buttonForget)


        mAuth = FirebaseAuth.getInstance()

        buttonRegister.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin!!.setOnClickListener {
            if(TextUtils.isEmpty(txtEmail!!.text.toString())){
                Toast.makeText(applicationContext,"Please Enter your Email!!",Toast.LENGTH_SHORT).show()
            }else {
                if(TextUtils.isEmpty(txtPassword!!.text.toString())){
                    Toast.makeText(applicationContext,"Please Enter your Password!!",Toast.LENGTH_SHORT).show()
                }else {
                    signIn()
                }
            }
        }

        buttonForget!!.setOnClickListener {
            val intent = Intent(this,ForgetActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onStart() {
        super.onStart()
        var currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        email = txtEmail!!.text.toString()
        password = txtPassword!!.text.toString()
        mAuth!!.signInWithEmailAndPassword(email,password).addOnCompleteListener(this) {
                task -> if (task.isSuccessful){
            Log.d("MyApp","SignIn SuccessFul!")
            val user = mAuth!!.currentUser
            updateUI(user)
        }else{
            Log.w("MyApp","Failure Process",task.exception)
            Toast.makeText(this@MainActivity,"Authentication Failed", Toast.LENGTH_SHORT).show()
            updateUI(null)
        }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null){
            val uid = user.uid
            val email = user.email
            Toast.makeText(this@MainActivity,"Welcome: $email your ID is $uid", Toast.LENGTH_SHORT).show()
            val intentSession  = Intent(this,ListActivity::class.java)
            startActivity(intentSession)
        }
    }
}