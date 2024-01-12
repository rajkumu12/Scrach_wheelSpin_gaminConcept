package com.example.scrachcard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinWheelActivity extends AppCompatActivity {
    private LuckyWheel lw;
    List<WheelItem> wheelItems;
    private String points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel);


        lw = findViewById(R.id.lwv);
        generateWheelItems();


        lw.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                WheelItem wheelItem = wheelItems.get(Integer.parseInt(points) - 1);
                String points_amount = wheelItem.text;
                Toast.makeText(SpinWheelActivity.this, points_amount, Toast.LENGTH_LONG).show();
            }
        });

        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                points = String.valueOf(random.nextInt(10));
                if (points.equals("0")) {

                    points = String.valueOf(1);
                }
                lw.rotateWheelTo(Integer.parseInt(points));

            }
        });

    }

    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#2E0202"), BitmapFactory.decodeResource(getResources(),
                R.drawable.chat), "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#673AB7"), BitmapFactory.decodeResource(getResources(),
                R.drawable.coupon), "0 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#FF9800"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ice_cream), "30 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#9C27B0"), BitmapFactory.decodeResource(getResources(),
                R.drawable.lemonade), "6000 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#FFFF5722"), BitmapFactory.decodeResource(getResources(),
                R.drawable.orange), "9 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#FF4B174E"), BitmapFactory.decodeResource(getResources(),
                R.drawable.shop), "20 $"));
        lw.addWheelItems(wheelItems);
    }
}