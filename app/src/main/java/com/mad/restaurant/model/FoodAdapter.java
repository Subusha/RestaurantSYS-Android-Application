package com.mad.restaurant.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mad.restaurant.R;
import com.mad.restaurant.database.DBHelper;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private List<FoodItem> foodlist;
    private Context context;
    private RecyclerView homeHeader_recyclerView;

    public static byte[] image;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt_foodName;
        public TextView txt_foodPrice;
        public TextView txt_foodDesc;
        public ImageView image_foodItem;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txt_foodName = (TextView) v.findViewById(R.id.itemName);
            txt_foodPrice = (TextView) v.findViewById(R.id.itemPrice);
            txt_foodDesc = (TextView) v.findViewById(R.id.itemDesc);
            image_foodItem = (ImageView) v.findViewById(R.id.itemImage);

        }
    }

    public void add(int position, FoodItem item) {
        foodlist.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        foodlist.remove(position);
        notifyItemRemoved(position);
    }

    public FoodAdapter(List<FoodItem> myDataset, Context c, RecyclerView recyclerView) {
        foodlist = myDataset;
        context = c;
        homeHeader_recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.home_header_items, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        final FoodItem item = foodlist.get(position);
        holder.txt_foodName.setText(item.getName());
        holder.txt_foodPrice.setText("Rs:"+String.valueOf(item.getPrice()));
        holder.txt_foodDesc.setText(item.getDescription());

        try{
            image = item.getImage();
            System.out.println("----------------"+image);
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            holder.image_foodItem.setImageBitmap(bitmap);
            System.out.println("--------"+bitmap);

        }catch (Exception e){
            e.printStackTrace();
        }



        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete Food Item?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(item.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DBHelper dbHelper = new DBHelper(context);
                        dbHelper.deleteFoodRecord(item.getId(), context);

                        foodlist.remove(position);
                        homeHeader_recyclerView.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, foodlist.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

    }

    private void goToUpdateActivity(int id){
      //  Intent goToUpdate = new Intent(context, UpdateRecordActivity.class);
      //  goToUpdate.putExtra("USER_ID", id);
      //  context.startActivity(goToUpdate);
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }
}
