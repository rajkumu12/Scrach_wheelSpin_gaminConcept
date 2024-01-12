package com.example.scrachcard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.anupkumarpanwar.scratchview.ScratchView;

import java.util.Objects;

public class ScrachActivity extends AppCompatActivity {
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrach);
        dialog = new Dialog(this);
        ScratchView scratchView = findViewById(R.id.scratchView);
        scratchView.setRevealListener(new ScratchView
                .IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {
                Toast.makeText(ScrachActivity.this,
                        "Revealed!", Toast.LENGTH_SHORT).show();
                dialog.setContentView(R.layout.popup_dialog);
                Objects.requireNonNull(dialog
                        .getWindow()).setBackgroundDrawable
                        (new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
            @Override
            public void onRevealPercentChangedListener
                    (ScratchView scratchView, float percent) {
                Log.d("Revealed", String.valueOf(percent));
            }
        });
    }
}