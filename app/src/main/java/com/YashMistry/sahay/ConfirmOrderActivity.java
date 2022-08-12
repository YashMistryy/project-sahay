package com.YashMistry.sahay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.YashMistry.sahay.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrderActivity extends AppCompatActivity {
    EditText etFullName , etAddress , etPhoneNumber , etCity;
    Button btnConfirm;
    String totalPrice;
    DatabaseReference detailRef  , orderRef , cartRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        totalPrice = getIntent().getStringExtra("totalPrice");
        Toast.makeText(this, "total"+totalPrice, Toast.LENGTH_SHORT).show();
        cartRef = FirebaseDatabase.getInstance().getReference().child("cart").child("users view").child(Prevalent.currentOnlineUser.getPhone());
        detailRef = FirebaseDatabase.getInstance().getReference().child("users").child(Prevalent.currentOnlineUser.getPhone());
        orderRef = FirebaseDatabase.getInstance().getReference().child("orders").child(Prevalent.currentOnlineUser.getPhone());
        etFullName = findViewById(R.id.etFullName);
        etPhoneNumber = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAdd);
        etCity = findViewById(R.id.etCity);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDetail();
            }
        });

    }

    private void checkDetail() {
//    lets validate the details entered by user
        if (TextUtils.isEmpty(etFullName.getText().toString().trim())){
            etFullName.setError("enter your name");
        } else  if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())){
            etPhoneNumber.setError("enter your phone number");
        } else  if (TextUtils.isEmpty(etAddress.getText().toString().trim())){
            etAddress.setError("enter your address");
        } else  if (TextUtils.isEmpty(etCity.getText().toString().trim())){
            etCity.setError("enter your city");
        }else{
            confirmOrder();
        }
    }

    private void confirmOrder() {
        String currentTime , currentDate;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd , mm ,yyyy");
        currentDate = dateFormat.format(calendar.getTime());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss a");
        currentTime = timeFormat.format(calendar.getTime());

        String dateOfOrder = currentDate+currentTime;
        HashMap<String , Object> prodMap = new HashMap<>();
        prodMap.put("totalPrice" , totalPrice);
        prodMap.put("name" , etFullName.getText().toString());
        prodMap.put("address" , etAddress.getText().toString());
        prodMap.put("city" , etCity.getText().toString());
        prodMap.put("phone" , etPhoneNumber.getText().toString());
        prodMap.put("dateOfOrder" , dateOfOrder);
        prodMap.put("state" , "not shiped");

        orderRef.updateChildren(prodMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ConfirmOrderActivity.this, "Order confirmed!!!", Toast.LENGTH_SHORT).show();
                    cartRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ConfirmOrderActivity.this, "Your cart has been emptied :)", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ConfirmOrderActivity.this, "cart did not get deleted :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else
                {
                    Toast.makeText(ConfirmOrderActivity.this, "Something wrong happened , order not confirmed!!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}