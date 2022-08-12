package com.YashMistry.sahay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.YashMistry.sahay.Adapters.CategoryAdapter;
import com.YashMistry.sahay.Models.CategoryModel;
import com.YashMistry.sahay.Prevalent.CommonIntent;
import com.YashMistry.sahay.Prevalent.Prevalent;

import java.util.ArrayList;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView ivDrawerIcon, ivDrawerBackIcon;
    TextView tvusername;
    RecyclerView rvCategory;
    ArrayList<CategoryModel> CarrayList;
    ImageView ivMasonry , ivElectrical , ivPlumbing , ivCarpentry , ivGardening , ivAutoparts;
    ImageView cartIcon;
    TextView categoriesDrawer , ordersDrawer , logoutDrawer , settingsDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoriesDrawer = findViewById(R.id.categories_drawer);
        ordersDrawer = findViewById(R.id.orders_drawer);
        logoutDrawer = findViewById(R.id.logout_drawer);
        settingsDrawer = findViewById(R.id.settings_drawer);
        ivDrawerIcon = findViewById(R.id.drawerIcon);
        ivDrawerBackIcon = findViewById(R.id.userprofile);
        ivMasonry = findViewById(R.id.custom_category_img1);
        ivAutoparts = findViewById(R.id.custom_category_img2);
        ivCarpentry = findViewById(R.id.custom_category_img3);
        ivPlumbing = findViewById(R.id.custom_category_img4);
        ivElectrical = findViewById(R.id.custom_category_img5);
        ivGardening = findViewById(R.id.custom_category_img6);
        drawerLayout = findViewById(R.id.drawer);
        tvusername = findViewById(R.id.tvusername);
        cartIcon = findViewById(R.id.cartIcon);

        tvusername.setText(Prevalent.currentOnlineUser.getName());
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonIntent(MainActivity.this , CartActivity.class);
            }
        });
        ivDrawerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvusername.setText(Prevalent.currentOnlineUser.getName());

                openDrawer(drawerLayout);
            }
        });
        ivDrawerBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseDrawer(drawerLayout);
            }
        });

        ivMasonry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ProductListActivity.class);
                intent.putExtra("category","masonry");
                startActivity(intent);
            }
        });
        ivAutoparts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ProductListActivity.class);
                intent.putExtra("category","automobile");
                startActivity(intent);
            }
        });
        ivCarpentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ProductListActivity.class);
                intent.putExtra("category","carpentry");
                startActivity(intent);
            }
        });
        ivPlumbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ProductListActivity.class);
                intent.putExtra("category","plumbing");
                startActivity(intent);
            }
        });
        ivElectrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ProductListActivity.class);
                intent.putExtra("category","electrical");
                startActivity(intent);
            }
        });
        ivGardening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ProductListActivity.class);
                intent.putExtra("category","gardening");
                startActivity(intent);
            }
        });

        settingsDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonIntent(MainActivity.this , SettingsActivity.class);
            }
        });
        ordersDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "orders .. . . .. . . .. ", Toast.LENGTH_SHORT).show();
            }
        });
        categoriesDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "categories . . . . . . .", Toast.LENGTH_SHORT).show();
            }
        });
        logoutDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                lets create an alert dialog box for user to confirm if he/she want to out
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Paper.book().destroy();
                        new CommonIntent(MainActivity.this , LoginActivity.class);
                    }
                });
                builder.setNegativeButton("No",null);
                builder.setCancelable(false);
                builder.show();

            }
        });

    }
    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
    private static void CloseDrawer(DrawerLayout drawerLayout){
       if( drawerLayout.isDrawerOpen(GravityCompat.START))
       {
           drawerLayout.closeDrawer(GravityCompat.START);
       }
    }

}






















//        rvCategory.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
//        rvCategory.setItemAnimator(new DefaultItemAnimator());
//        CarrayList  = new ArrayList<>();
//        for (int i=0;i<categoryNamesArray.length;i++){
//            CategoryModel listModel = new CategoryModel();
//            listModel.setName(categoryNamesArray[i]);
//            listModel.setImg(categoryImgArray[i]);
//            CarrayList.add(listModel);
//        }
////        lets make a custom adapter for our category recycler view
//        CategoryAdapter categoryAdapter = new CategoryAdapter(MainActivity.this , CarrayList);
//        rvCategory.setAdapter(categoryAdapter);