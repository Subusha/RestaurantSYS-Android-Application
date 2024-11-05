package com.mad.restaurant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.mad.restaurant.PromotionsMaster;
import com.mad.restaurant.model.FoodItem;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Restaurant.db";

    private static final String TABLE_NAME_FOOD = "food";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FOOD_NAME = "name";
    private static final String COLUMN_FOOD_PRICE = "price";
    private static final String COLUMN_FOOD_DESCRIPTION = "description";
    private static final String COLUMN_FOOD_IMAGE = "image";


    private static final String CREATE_TABLE_FOOD = " CREATE TABLE " + TABLE_NAME_FOOD + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FOOD_NAME + " TEXT NOT NULL, " +
            COLUMN_FOOD_PRICE + " INTEGER NOT NULL, " +
            COLUMN_FOOD_DESCRIPTION + " TEXT NOT NULL, " +
            COLUMN_FOOD_IMAGE + " BLOB);";


    private static final String TABLE_NAME_CART = "cart";
    private static final String CART_COLUMN_ID = "_id";
    private static final String CART_COLUMN_NAME = "name";
    private static final String CART_COLUMN_DESC = "description";
    private static final String CART_COLUMN_PRICE = "price";
    private static final String CART_COLUMN_QTY = "qty";
    private static final String CART_COLUMN_IMAGE = "image";

    private static final String SQL_CREATE_CART = "CREATE TABLE "+TABLE_NAME_CART+" ("+
            CART_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CART_COLUMN_NAME + " TEXT NOT NULL, " +
            CART_COLUMN_DESC + " TEXT NOT NULL, " +
            CART_COLUMN_PRICE + " INTEGER NOT NULL, " +
            CART_COLUMN_QTY + " INTEGER NOT NULL, " +
            CART_COLUMN_IMAGE + " BLOB);";

    private static final String SQL_CREATE_PROMO="CREATE TABLE "+ PromotionsMaster.promotions.Table_Name+" ("+
            PromotionsMaster.promotions.Col_Name_id+" integer primary key  AUTOINCREMENT , "+
            PromotionsMaster.promotions.Col_Name_title+" Text,"+
            PromotionsMaster.promotions.Col_Name_description+" Text,"+
            PromotionsMaster.promotions.Col_Name_image+" BLOB,"+
            PromotionsMaster.promotions.Col_Name_price+" Text,"+
            PromotionsMaster.promotions.Col_Name_descount+" Text)";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOOD);

        db.execSQL(SQL_CREATE_PROMO);
        db.execSQL(SQL_CREATE_CART);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_FOOD);

        db.execSQL("DROP TABLE IF EXISTS "+PromotionsMaster.promotions.Table_Name);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_CART);
        this.onCreate(db);
    }

    public void addFoodItem(FoodItem item, Context context) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, item.getName());
        values.put(COLUMN_FOOD_PRICE, item.getPrice());
        values.put(COLUMN_FOOD_DESCRIPTION, item.getDescription());
        values.put(COLUMN_FOOD_IMAGE, item.getImage());

        // insert
        long result = db.insert(TABLE_NAME_FOOD, null, values);

        if (result > 0) {
            System.out.println("Success----------");
            Toast.makeText(context, "Success.", Toast.LENGTH_LONG).show();
        } else {
            System.out.println("Failed-----------");
            Toast.makeText(context, "Failed.", Toast.LENGTH_LONG).show();
        }

        db.close();
    }

    public List<FoodItem> foodItemList() {

        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_FOOD;

        List<FoodItem> foodList = new LinkedList<>();

        Cursor cursor = db.rawQuery(query, null);
        FoodItem food;

        if (cursor.moveToFirst()) {
            do {
                food = new FoodItem();

                food.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                food.setName(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME)));
                food.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_PRICE)));
                food.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_DESCRIPTION)));
                food.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_FOOD_IMAGE)));
                foodList.add(food);

            } while (cursor.moveToNext());
        }

        return foodList;
    }

    public FoodItem getFoodItem(int id) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT  * FROM " + TABLE_NAME_FOOD + " WHERE _id=" + id;
        Cursor cursor = db.rawQuery(query, null);

        FoodItem receivedFood = new FoodItem();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedFood.setName(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_NAME)));
            receivedFood.setPrice(cursor.getInt(cursor.getColumnIndex(COLUMN_FOOD_PRICE)));
            receivedFood.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_FOOD_DESCRIPTION)));
            receivedFood.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_FOOD_IMAGE)));
        }

        return receivedFood;

    }

    public void deleteFoodRecord(int id, Context context) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_NAME_FOOD + " WHERE _id='" + id + "'");
            Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed." + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    public void updateFoodRecord(int id, Context context, FoodItem updatedFood) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.execSQL("UPDATE  " + TABLE_NAME_FOOD + " SET " + COLUMN_FOOD_NAME + " ='" + updatedFood.getName() + "'," + COLUMN_FOOD_PRICE + " ='" + updatedFood.getPrice() + "'," + COLUMN_FOOD_DESCRIPTION + " ='" + updatedFood.getDescription() + "'," + COLUMN_FOOD_IMAGE + " ='" + updatedFood.getImage() + "'  WHERE _id='" + id + "'");
            Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Updated Failed." + e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }



    public boolean addCart(String name, String desc, int price, int qty, byte[] image) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_COLUMN_NAME,name);
        values.put(CART_COLUMN_DESC,desc);
        values.put(CART_COLUMN_PRICE, price);
        values.put(CART_COLUMN_QTY, qty);
        values.put(CART_COLUMN_IMAGE,image);

        long result = db.insert(TABLE_NAME_CART, null, values);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    public long addInfo(String title, String description, byte[] image, String price, String discount) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(PromotionsMaster.promotions.Col_Name_title,title);
        values.put(PromotionsMaster.promotions.Col_Name_description,description);
        values.put(PromotionsMaster.promotions.Col_Name_image,image);
        values.put(PromotionsMaster.promotions.Col_Name_price,price);
        values.put(PromotionsMaster.promotions.Col_Name_descount,discount);

        long newrow=sqLiteDatabase.insert(PromotionsMaster.promotions.Table_Name,null,values);

        return newrow;
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+PromotionsMaster.promotions.Table_Name,null);

        return res;
    }
    public boolean updateData(String title,String description,byte[] image,String price,String discount,String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put(PromotionsMaster.promotions.Col_Name_title,title);
        contentValues.put(PromotionsMaster.promotions.Col_Name_description,description);
        contentValues.put(PromotionsMaster.promotions.Col_Name_image,image);
        contentValues.put(PromotionsMaster.promotions.Col_Name_price,price);
        contentValues.put(PromotionsMaster.promotions.Col_Name_descount,discount);
        // contentValues.put(PromotionsMaster.promotions.Col_Name_id,Integer.parseInt(id));

        sqLiteDatabase.update(PromotionsMaster.promotions.Table_Name,contentValues,"Id = ?",new String[] {id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.delete(PromotionsMaster.promotions.Table_Name,"Id= ?",new String[] {id});
    }
    public Cursor getData(String sql) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);

    }

}
