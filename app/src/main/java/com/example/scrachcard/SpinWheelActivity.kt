package com.example.scrachcard

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bluehomestudio.luckywheel.LuckyWheel
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget
import com.bluehomestudio.luckywheel.WheelItem
import java.util.Random

class SpinWheelActivity : AppCompatActivity() {
    private var lw: LuckyWheel? = null
    var wheelItems: MutableList<WheelItem>? = null
    private var points: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin_wheel)
        lw = findViewById(R.id.lwv)
        generateWheelItems()
        lw?.setLuckyWheelReachTheTarget(OnLuckyWheelReachTheTarget {
            val wheelItem = wheelItems!![points!!.toInt() - 1]
            val points_amount = wheelItem.text
            Toast.makeText(this@SpinWheelActivity, points_amount, Toast.LENGTH_LONG).show()
        })
        val start = findViewById<Button>(R.id.start)
        start.setOnClickListener {
            val random = Random()
            points = random.nextInt(10).toString()
            if (points == "0") {
                points = 1.toString()
            }
            lw?.rotateWheelTo(points!!.toInt())
        }
    }

    private fun generateWheelItems() {
        wheelItems = ArrayList()
        wheelItems?.add(
            WheelItem(
                Color.parseColor("#2E0202"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.chat
                ), "100 $"
            )
        )
        wheelItems?.add(
            WheelItem(
                Color.parseColor("#673AB7"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.coupon
                ), "0 $"
            )
        )
        wheelItems?.add(
            WheelItem(
                Color.parseColor("#FF9800"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.ice_cream
                ), "30 $"
            )
        )
        wheelItems?.add(
            WheelItem(
                Color.parseColor("#9C27B0"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.lemonade
                ), "6000 $"
            )
        )
        wheelItems?.add(
            WheelItem(
                Color.parseColor("#FFFF5722"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.orange
                ), "9 $"
            )
        )
        wheelItems?.add(
            WheelItem(
                Color.parseColor("#FF4B174E"), BitmapFactory.decodeResource(
                    resources,
                    R.drawable.shop
                ), "20 $"
            )
        )
        lw!!.addWheelItems(wheelItems)
    }
}