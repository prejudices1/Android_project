package com.byted.chapter5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre);

        setTitle("北瓜视频");
        ImageView imageView = findViewById(R.id.imageViewpre);
        TextView textView = findViewById(R.id.textView);
        textView.setTextColor(android.graphics.Color.RED);
        textView.setTextSize(35);
        TextView con=findViewById(R.id.con);

        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump();
            }
        });

        String url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1431144262,3256824993&fm=26&gp=0.jpg";
        Glide.with(this).load(url).into(imageView);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                jump();
            }
        }, 2000);
    }

    private void jump() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
