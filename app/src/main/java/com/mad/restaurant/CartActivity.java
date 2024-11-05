package com.mad.restaurant;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mad.restaurant.database.DBHelper;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    int id;
    ListView listView;
    ArrayList<cart> cartlist;
    CartAdapter adapter = null;

    DBHelper dBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listView = (ListView)findViewById(R.id.cart_list);

        cartlist = new ArrayList<>();
        adapter = new CartAdapter(this,R.layout.cart_item,cartlist);
        listView.setAdapter(adapter);

        dBhelper = new DBHelper(this);

        Cursor cursor = dBhelper.getData("SELECT * FROM cart");
        cartlist.clear();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name  = cursor.getString(1);
            String desc = cursor.getString(2);
            int price = cursor.getInt(3);
            int qty = cursor.getInt(4);
            byte[] image = cursor.getBlob(5);

            cartlist.add(new cart(id,name,desc,price,qty,image));
        }
        adapter.notifyDataSetChanged();
        if(cartlist.size()==0){
            //if there is no record in table of database
            Toast.makeText(this,"Cart is Empty",Toast.LENGTH_SHORT).show();


        }


    }
}
