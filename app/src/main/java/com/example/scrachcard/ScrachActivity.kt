package com.example.scrachcard

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.anupkumarpanwar.scratchview.ScratchView
import com.anupkumarpanwar.scratchview.ScratchView.IRevealListener
import java.util.Objects

class ScrachActivity : AppCompatActivity() {
    var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrach)

        dialog = Dialog(this)
        val scratchView = findViewById<ScratchView>(R.id.scratchView)
        scratchView.setRevealListener(object : IRevealListener {
            override fun onRevealed(scratchView: ScratchView) {
                Toast.makeText(
                    this@ScrachActivity,
                    "Revealed!", Toast.LENGTH_SHORT
                ).show()
                dialog!!.setContentView(R.layout.popup_dialog)
                Objects.requireNonNull(
                    dialog!!
                        .window
                )?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog!!.show()
            }

            override fun onRevealPercentChangedListener(scratchView: ScratchView, percent: Float) {
                Log.d("Revealed", percent.toString())
            }
        })
    }
}