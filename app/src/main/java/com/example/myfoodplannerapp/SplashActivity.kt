package com.example.myfoodplannerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myfoodplannerapp.Auth.loginActivity
import com.example.myfoodplannerapp.Auth.rgstrActivity
import com.example.myfoodplannerapp.HomeActivity.GuestMainActivity
import com.example.myfoodplannerapp.HomeActivity.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        val rgstrBtn: Button = findViewById(R.id.rgstrbutton)
        rgstrBtn.setOnClickListener{
            startActivity(Intent(this,rgstrActivity::class.java))
        }
        val lgnBtn: Button = findViewById(R.id.lgnbutton)
        lgnBtn.setOnClickListener{
            startActivity(Intent(this, loginActivity::class.java))
        }
    }

    fun start(view: View) {
        val intent = Intent(this, GuestMainActivity::class.java)
        startActivity(intent)
    }
}