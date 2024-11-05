package com.mad.restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mad.restaurant.database.DBHelper;
import com.mad.restaurant.model.FoodAdapter;
import com.mad.restaurant.model.FoodItem;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddHomeItemActivity extends AppCompatActivity {

    EditText txtName,txtPrice,txtDesc;
    ImageView imageFood;
    Button btnAddFood;
    DBHelper dbHelper;

   private static String msg = "";

    final int IMAGE_REQUEST_CODE = 999;
    public static String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_item);

        txtName = (EditText)findViewById(R.id.name);
        txtPrice = (EditText)findViewById(R.id.price);
        txtDesc = (EditText)findViewById(R.id.description);
        imageFood = (ImageView)findViewById(R.id.food_image);
        btnAddFood = (Button)findViewById(R.id.btn_addFoodItem);

        dbHelper = new DBHelper(this);

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFoodItem();
            }
        });


        imageFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(
                        AddHomeItemActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        IMAGE_REQUEST_CODE
                );

            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == IMAGE_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }else{
                Toast.makeText(getApplicationContext(),"You do not have permission to access gallery",Toast.LENGTH_LONG).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bt = BitmapFactory.decodeStream(inputStream);
                imageFood.setImageBitmap(bt);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public static byte[] imageViewToByte(ImageView image){
        Bitmap bt = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bt.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public boolean foodValidate(){

        boolean name = txtName.getText().toString().isEmpty();
        boolean price = txtPrice.getText().toString().isEmpty();
        boolean desc = txtDesc.getText().toString().isEmpty();

        if (name){
            msg = "Food Name can not be Empty.";
            return false;
        }else if(desc){
            msg = "Food Description can not be Empty";
            return false;
        }else if(price){
            msg = "Food price can not be Empty";
            return false;
        }else{
            int intPrice = Integer.parseInt(txtPrice.getText().toString());
            if(intPrice<=0){
                msg = "Food price can not be zero or Negetive";
                return false;
            }
            msg = "------Error---";
            return true;
        }
    }

    private void saveFoodItem(){

        if(foodValidate() == false){

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }else {

            String name = txtName.getText().toString().trim();
            int price = Integer.parseInt(txtPrice.getText().toString());
            String desc = txtDesc.getText().toString().trim();
            try {

                byte[] image = imageViewToByte(imageFood);
                FoodItem item = new FoodItem(name, price, desc, image);
                dbHelper.addFoodItem(item, this);

            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Image Not Add",Toast.LENGTH_LONG).show();
            }

            //create new person


            //finally redirect back home
            // NOTE you can implement an sqlite callback then redirect on success delete
            goBackHome();

        }

    }

    private void goBackHome(){
        startActivity(new Intent(AddHomeItemActivity.this, HomeActivity.class));
    }


}
