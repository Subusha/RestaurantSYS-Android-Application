package com.mad.restaurant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class submarianlistadapter extends BaseAdapter {


    private Context context;
    private int layout;
    private ArrayList<promo_model> submarianlist;

    public submarianlistadapter(Context context, int layout, ArrayList<promo_model> submarianlist) {
        this.context = context;
        this.layout = layout;
        this.submarianlist = submarianlist;
    }

    @Override
    public int getCount() {
        return submarianlist.size();
    }

    @Override
    public Object getItem(int i) {
        return submarianlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class viewHolder{
        ImageView imageView;
        TextView txtTitle,txtDescription,txtimage,txtprice,txtdiscount;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row=view;
        viewHolder holder=new viewHolder();

        if(row==null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.txtTitle=row.findViewById(R.id.submarianrowname1);
            holder.txtDescription=row.findViewById(R.id.submarianrowname2);
            holder.imageView = row.findViewById(R.id.promoFoodImage);
            holder.txtprice=row.findViewById(R.id.submarianrowname4);
            holder.txtdiscount=row.findViewById(R.id.submarianrowname5);
            row.setTag(holder);

        }
        else{
            holder=(viewHolder)row.getTag();
        }
        promo_model promo_model=submarianlist.get(i);
        byte[] image = promo_model.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageView.setImageBitmap(bitmap);
        holder.txtTitle.setText(promo_model.getTitle());
        holder.txtDescription.setText(promo_model.getDescription());
        holder.txtprice.setText(promo_model.getPrice());
        holder.txtdiscount.setText(promo_model.getDiscount());


        /*byte[] recordImage = modelmeal.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0,recordImage.length);
        holder.imageView.setImageBitmap(bitmap);            */

        return row;
    }
}
