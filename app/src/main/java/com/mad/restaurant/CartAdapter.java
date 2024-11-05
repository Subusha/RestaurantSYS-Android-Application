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
import java.util.List;

public class CartAdapter extends BaseAdapter {

    Context context;
    int layout;
    ArrayList<cart> cartArrayList;

    public CartAdapter(Context context, int layout, ArrayList<cart> cartArrayList) {
        this.context = context;
        this.layout = layout;
        this.cartArrayList = cartArrayList;
    }

    @Override
    public int getCount() {
        return cartArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class viewHolder{
        ImageView imageView;
        TextView name,desc,price,qty;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row=view;
        viewHolder holder=new viewHolder();

        if(row==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.name=row.findViewById(R.id.cartItem_foodName);
            holder.desc=row.findViewById(R.id.cartItem_foodCat);
            holder.price=row.findViewById(R.id.cartItem_foodPrice);
            holder.qty=row.findViewById(R.id.cartItem_foodQty);
            holder.imageView=row.findViewById(R.id.cartItem_image);
            row.setTag(holder);
        }
        else{
            holder=(viewHolder)row.getTag();
        }
        cart modelCart=cartArrayList.get(position);
        byte[] image = modelCart.getImage();
        holder.name.setText(modelCart.getName());
        holder.desc.setText(modelCart.getDesc());
        holder.price.setText(String.valueOf(modelCart.getPrice()));
        holder.qty.setText(String.valueOf(modelCart.getQty()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
