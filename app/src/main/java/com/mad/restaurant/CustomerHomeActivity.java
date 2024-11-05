package com.mad.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mad.restaurant.database.DBHelper;
import com.mad.restaurant.model.FoodAdapter;
import com.nex3z.notificationbadge.NotificationBadge;

public class CustomerHomeActivity extends AppCompatActivity {


    RecyclerView recyclerView_homeHeder;
    RecyclerView.LayoutManager layoutManager;
    DBHelper dbHelper;
    FoodAdapter adapter;

    NotificationBadge badge;

    Button btnCat1, btnCat2, btnCat3, btnCat4, btnCat5, btnCat6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_customer);

        recyclerView_homeHeder = (RecyclerView)findViewById(R.id.recyclerview_header);
        recyclerView_homeHeder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_homeHeder.setLayoutManager(layoutManager);

        dbHelper = new DBHelper(this);

        recyclerView_homeHeder.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView_homeHeder.setLayoutManager(layoutManager);
        adapter = new FoodAdapter(dbHelper.foodItemList(),this,recyclerView_homeHeder);
        recyclerView_homeHeder.setAdapter(adapter);


        btnCat1 = (Button)findViewById(R.id.btnCat1);
        btnCat2 = (Button)findViewById(R.id.btnCat2);
        btnCat3 = (Button)findViewById(R.id.btnCat3);
        btnCat4 = (Button)findViewById(R.id.btnCat4);
        btnCat5 = (Button)findViewById(R.id.btnCat5);
        btnCat6 = (Button)findViewById(R.id.btnCat6);

        btnCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(getApplicationContext(),"Redirect to Category 01",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CustomerHomeActivity.this,MainActivity.class);
                startActivity(intent);

            }
        });
        btnCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Redirect to Category 02",Toast.LENGTH_SHORT).show();
            }
        });
        btnCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Redirect to Category 03",Toast.LENGTH_SHORT).show();
            }
        });
        btnCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Redirect to Category 04",Toast.LENGTH_SHORT).show();
            }
        });
        btnCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Redirect to Category 05",Toast.LENGTH_SHORT).show();
            }
        });
        btnCat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Redirect to Category 06",Toast.LENGTH_SHORT).show();
            }
        });




    }



    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()){
            case R.id.addMenu:
                Intent intent = new Intent(CustomerHomeActivity.this,AddHomeItemActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
