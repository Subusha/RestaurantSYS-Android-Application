package com.mad.restaurant;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.mad.restaurant.add_promotions.dBhelper;

public class Drinks extends AppCompatActivity {

    ListView drlistView;
    ArrayList<promo_model> drList;
    drinksadapter dradapter = null;

    String title,description,discount,price;

    ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks);

        drlistView = findViewById(R.id.burgerlist);
        drList = new ArrayList<>();
        dradapter = new drinksadapter(this ,R.layout.rowsubmarian,drList);
        drlistView.setAdapter(dradapter);

        //get all data from db
        Cursor cursor = dBhelper.getData("SELECT * FROM Promotions WHERE Title = 'drinks'");
        drList.clear();
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            title=cursor.getString(1);
            description = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            price=cursor.getString(4);
            discount= cursor.getString(5);
            drList.add(new promo_model(id,title,description,image,price,discount));
        }


        dradapter.notifyDataSetChanged();

        if(drList.size()==0){
            //if there is no record in table of database
            Toast.makeText(this,"No record found...",Toast.LENGTH_LONG).show();

        }

        drlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),FoodItemDetailsActivity.class);
                intent.putExtra("title",drList.get(position).getTitle());
                intent.putExtra("desc",drList.get(position).getDescription());
                intent.putExtra("price",drList.get(position).getPrice());
                intent.putExtra("image",drList.get(position).getImage());
               intent.putExtra("position",position);
                startActivity(intent);
            }
        });

    }
}