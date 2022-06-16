package com.yash.geet.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yash.geet.R

class WelcomeActivity : AppCompatActivity() {
    private lateinit var btnLetsMusic : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        supportActionBar?.hide()
        btnLetsMusic = findViewById(R.id.btnLetsMusic)
        btnLetsMusic.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }
}