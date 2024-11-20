package com.example.myfoodplannerapp.Auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myfoodplannerapp.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class rgstrActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rgstr)

        mAuth = FirebaseAuth.getInstance()
        val edEmail:EditText =findViewById(R.id.edEmail)
        val edPassword:EditText =findViewById(R.id.edPassword)
        val btnRgstr:Button = findViewById(R.id.rgstrBtn)
        btnRgstr.setOnClickListener {
            if(edEmail.text.toString().isNotEmpty()&&edPassword.text.toString().isNotEmpty()){
            mAuth.createUserWithEmailAndPassword(edEmail.text.toString(),edPassword.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful)
                        Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()

                }
            }
            else
                Toast.makeText(this,"Empty",Toast.LENGTH_SHORT).show()
        }
    }

    fun login(view: View) {
        startActivity(Intent(this, loginActivity::class.java))
    }
}