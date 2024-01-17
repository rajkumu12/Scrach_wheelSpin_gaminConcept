package com.example.scrachcard

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class CommonActivity : AppCompatActivity() {
    lateinit var tv_wheel:TextView
    lateinit var tv_scratch:TextView
    lateinit var tv_docScan:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        tv_wheel= findViewById(R.id.spinwheel)
        tv_scratch= findViewById(R.id.scrachs)
        tv_docScan= findViewById(R.id.scdocScann)



        tv_wheel.setOnClickListener {
            startActivity(Intent(this@CommonActivity,SpinWheelActivity::class.java))
        }
        tv_scratch.setOnClickListener {
            startActivity(Intent(this@CommonActivity,ScrachActivity::class.java))
        }

        tv_docScan.setOnClickListener {
            startActivity(Intent(this@CommonActivity,ScannedImageList::class.java))
        }
    }
}