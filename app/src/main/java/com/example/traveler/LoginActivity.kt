package com.example.traveler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.traveler.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val button_login = findViewById<ImageButton>(R.id.button_login)
        button_login.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}