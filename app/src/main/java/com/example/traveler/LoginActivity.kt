package com.example.traveler

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button_login = findViewById<ImageButton>(R.id.button_login)
        button_login.setOnClickListener {
            startActivity(Intent(this, NaviActivity::class.java))
        }
    }
}