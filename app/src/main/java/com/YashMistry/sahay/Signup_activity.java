package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.YashMistry.sahay.Prevalent.CommonIntent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Signup_activity extends AppCompatActivity {
    Button btnLogin , btnSignup;
    EditText etname , etnum , etpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnLogin = findViewById(R.id.btn_login_signup);
        btnSignup = findViewById(R.id.btn_signup_signup);
        etname = findViewById(R.id.etName_signup);
        etnum = findViewById(R.id.etphnumber_signup);
        etpass = findViewById(R.id.etpass_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etname.getText().toString();
                String pass = etpass.getText().toString();
                String phNumber = etnum.getText().toString();
                
                if(TextUtils.isEmpty(name.trim()) ){
                    etname.setError("enter your name");
                }
                if(TextUtils.isEmpty(pass.trim())){
                    etpass.setError("enter password");
                }
                if(TextUtils.isEmpty(phNumber.trim())){
                    etnum.setError("enter phone number");
                }
                else {
                    validatePhoneNumber(phNumber , name , pass);
                }
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Signup_activity.this,LoginActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
     new CommonIntent(Signup_activity.this,LoginActivity.class);
    }

    private void validatePhoneNumber(String phNumber, String name, String pass) {
        Toast.makeText(this, "phnumber"+phNumber+" name"+name+" pass"+pass, Toast.LENGTH_SHORT).show();
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("users").child(phNumber).exists()))
                {
                    Toast.makeText(Signup_activity.this, "creating your account", Toast.LENGTH_SHORT).show();
                    HashMap<String , Object> userdatamap = new HashMap<>();
                    userdatamap.put("phone" , phNumber);
                    userdatamap.put("name" , name);
                    userdatamap.put("pass" , pass);

                    RootRef.child("users").child(phNumber).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Signup_activity.this, "Your account was created :)", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Signup_activity.this , MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Signup_activity.this, "NETWORK ERROR :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Signup_activity.this, "USER ALREADY EXISTS!!! PLS LOGIN", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }
}