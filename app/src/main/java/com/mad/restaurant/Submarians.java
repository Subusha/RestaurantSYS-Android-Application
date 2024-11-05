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

public class Submarians extends AppCompatActivity {

    ListView slistView;
    ArrayList<promo_model>sList;
    submarianlistadapter sadapter=null;

    ImageView imageViewIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submarians);


        slistView=findViewById(R.id.submarianlist);
        sList=new ArrayList<>();
        sadapter=new submarianlistadapter(this,R.layout.rowsubmarian,sList);
        slistView.setAdapter(sadapter);



        //get all data from db

        Cursor cursor = dBhelper.getData("SELECT * FROM Promotions WHERE Title = 'submarians'");
        sList.clear();
        while(cursor.moveToNext()){
            int id=cursor.getInt(0);
            String title=cursor.getString(1);
            String description = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            String price=cursor.getString(4);
            String discount= cursor.getString(5);
            sList.add(new promo_model(id,title,description,image,price,discount));


        }
        sadapter.notifyDataSetChanged();
        if(sList.size()==0){
            //if there is no record in table of database
            Toast.makeText(this,"No record found...",Toast.LENGTH_LONG).show();

        }
        slistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),FoodItemDetailsActivity.class);
                intent.putExtra("title",sList.get(position).getTitle());
                intent.putExtra("desc",sList.get(position).getDescription());
                intent.putExtra("price",sList.get(position).getPrice());
                intent.putExtra("image",sList.get(position).getImage());
                //   intent.putExtra("discount",discount);
                startActivity(intent);
            }
        });
    }
}
