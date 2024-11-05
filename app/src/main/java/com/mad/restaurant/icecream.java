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

public class icecream extends AppCompatActivity {
    ListView icelistView;
    ArrayList<promo_model> iceList;
    icecreamadapter iceadapter=null;

    ImageView imageViewIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icecream);

            icelistView=findViewById(R.id.icecreamlist);
            iceList=new ArrayList<>();
            iceadapter=new icecreamadapter(this,R.layout.rowsubmarian,iceList);
            icelistView.setAdapter(iceadapter);
            //get all data from db
        //get all data from db

        Cursor cursor = dBhelper.getData("SELECT * FROM Promotions WHERE Title = 'icecream'");
        iceList.clear();
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String description = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            String price=cursor.getString(4);
            String discount= cursor.getString(5);
            iceList.add(new promo_model(id,title,description,image,price,discount));


        }
        iceadapter.notifyDataSetChanged();
        if(iceList.size()==0){
            //if there is no record in table of database
            Toast.makeText(this,"No record found...",Toast.LENGTH_LONG).show();

        }
        icelistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),FoodItemDetailsActivity.class);
                intent.putExtra("title",iceList.get(i).getTitle());
                intent.putExtra("desc",iceList.get(i).getDescription());
                intent.putExtra("price",iceList.get(i).getPrice());
                intent.putExtra("image",iceList.get(i).getImage());
                //   intent.putExtra("discount",discount);
                startActivity(intent);

            }
        });
    }
}
