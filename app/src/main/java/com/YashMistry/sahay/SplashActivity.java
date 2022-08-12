package com.YashMistry.sahay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    TextView tvwlcm;
    ImageView ivSplashimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivSplashimg = findViewById(R.id.ivSplashimg);
        tvwlcm = findViewById(R.id.tvwlcm);

        AlphaAnimation animation1  = new AlphaAnimation(0,1);
        animation1.setDuration(3000);
        animation1.setRepeatCount(2);
        tvwlcm.startAnimation(animation1);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,LoginActivity.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, 3000);
//        Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//        startActivity(intent);
    }
}
//                     for main activity
//  btnLogout = findViewById(R.id.btn_logout);
//
//          btnLogout.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View view) {
//        Paper.book().destroy();
//        new CommonIntent(MainActivity.this , LoginActivity.class);
//        }
//        });

/* for main activity xml
*
* <?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/white"
    tools:context=".MainActivity">

    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="4dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.0"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:background="@color/VeryLightgray">

        <ImageView
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:src="@drawable/logout_buttton"
            android:layout_margin="5dp"
            android:layout_centerVertical="true"/>

        <ImageView
            android:id="@+id/btn_logout"
            android:layout_width="wrap_content"
            android:layout_height="50dp"
            android:layout_centerInParent="true"
            android:src="@drawable/inkpx_word_art" />
        <ImageView
            android:id="@+id/btnCart"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:src="@drawable/shopping_cart"
            android:layout_alignParentEnd="true"
            android:layout_margin="5dp"/>



    </RelativeLayout>


</androidx.constraintlayout.widget.ConstraintLayout>*/