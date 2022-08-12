package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.YashMistry.sahay.Models.users;
import com.YashMistry.sahay.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText etPhNumber , etPass;
    Button btnLogin , btnSignup;
    String dbParent = "users";
    ProgressDialog loadingbar;
    CheckBox checkBox;
    TextView tvAdmin , tvNotAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etPass = findViewById(R.id.etpass_login);
        etPhNumber = findViewById(R.id.etphnumber_login);
        btnLogin = findViewById(R.id.btn_login_login);
        btnSignup = findViewById(R.id.btn_signup_login);
        loadingbar = new ProgressDialog(this);
        checkBox = findViewById(R.id.checkBox);
        tvAdmin = findViewById(R.id.tvAdmin);
        tvNotAdmin = findViewById(R.id.tvNotAdmin);
        tvNotAdmin.setVisibility(View.INVISIBLE);

        Paper.init(this);
        String userPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String userPassKey = Paper.book().read(Prevalent.userPassKey);

        if (userPassKey!= "" && userPhoneKey != ""){
            if (!TextUtils.isEmpty(userPassKey)  && !TextUtils.isEmpty(userPhoneKey)){
                AllowAccessDirect(userPhoneKey,userPassKey);
                loadingbar.setTitle("Logging In ");
                loadingbar.setMessage("Please wait");
                loadingbar.setCanceledOnTouchOutside(false);
                loadingbar.show();
            }
        }
        tvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setText("Login Admin");
                tvAdmin.setVisibility(View.INVISIBLE);
                tvNotAdmin.setVisibility(View.VISIBLE);
                dbParent = "admins";
            }
        });
        tvNotAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setText("Login");
                tvNotAdmin.setVisibility(View.INVISIBLE);
                tvAdmin.setVisibility(View.VISIBLE);
                dbParent = "users";

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phNumber = etPhNumber.getText().toString();
                String pass = etPass.getText().toString();
                if (TextUtils.isEmpty(phNumber.trim())){
                    etPhNumber.setError("enter phone number!");
                }
                else if(TextUtils.isEmpty(pass.trim())){
                    etPass.setError("enter your password!");
                }
                else
                {
                    AllowAccessToUser(phNumber , pass);
                }

            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this , Signup_activity.class);
                startActivity(intent);
            }
        });
    }

    private void AllowAccessDirect(String phNumber, String pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance("https://sahay-3b63e-default-rtdb.firebaseio.com/").getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(dbParent).child(phNumber).exists()){
//                  if user exists lets copy the credentials for further usage
                    users userdata = dataSnapshot.child(dbParent).child(phNumber).getValue(users.class);
                    if(userdata.getPass().equals(pass)) {

                        loadingbar.setTitle("Logging you In , wait.");
                        Prevalent.currentOnlineUser = userdata;
                        Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "WRONG PASSWORD!", Toast.LENGTH_SHORT).show();
                        etPass.setError("WRONG PASSWORD");
                    }
                }
                else
                {   loadingbar.dismiss();
                    Toast.makeText(LoginActivity.this, "USER DOESN'T EXIST , PLS CREATE AN ACCOUNT", Toast.LENGTH_LONG).show();
                    etPhNumber.setError("PHONE NUMBER NOT REGISTERED , PLS CREATE AN ACCOUNT FIRST!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });



    }

    private void AllowAccessToUser(String phNumber, String pass) {

        if (checkBox.isChecked()){
            Paper.book().write(Prevalent.userPhoneKey,phNumber);
            Paper.book().write(Prevalent.userPassKey,pass);
        }
        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Toast.makeText(LoginActivity.this, "2", Toast.LENGTH_SHORT).show();


                if (dataSnapshot.child(dbParent).child(phNumber).exists()){
//                  if user exists lets copy the credentials for further usage

                    Toast.makeText(LoginActivity.this, "3", Toast.LENGTH_SHORT).show();


                    users userdata = dataSnapshot.child(dbParent).child(phNumber).getValue(users.class);
                    if(userdata.getPass().equals(pass)) {
                        if (dbParent.equals("users"))
                        {
                            Toast.makeText(LoginActivity.this, "4", Toast.LENGTH_SHORT).show();


                            loadingbar.setTitle("Logging you In , wait.");
                            Intent intent = new Intent(LoginActivity.this , MainActivity.class);
                            Prevalent.currentOnlineUser = userdata;
                            startActivity(intent);
                        }
                        else if(dbParent.equals("admins")) {
                            loadingbar.setTitle("Logging you In , wait.");
                            Intent intent = new Intent(LoginActivity.this , AdminCategoryActivity.class);
                            startActivity(intent);
                        }
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "WRONG PASSWORD!", Toast.LENGTH_SHORT).show();
                        etPass.setError("WRONG PASSWORD");
                    }}
                else
                {   loadingbar.dismiss();
                    Toast.makeText(LoginActivity.this, "USER DOESN'T EXIST , PLS CREATE AN ACCOUNT", Toast.LENGTH_LONG).show();
                    etPhNumber.setError("PHONE NUMBER NOT REGISTERED , PLS CREATE AN ACCOUNT FIRST!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("EXIT");
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { finishAffinity(); }
        });
        builder.setNegativeButton("No" , null);
        builder.show();
    }
}
