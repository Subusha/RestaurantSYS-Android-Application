package com.mad.restaurant;

import android.provider.BaseColumns;

public final class PromotionsMaster {
    private PromotionsMaster() {
    }
    public static class promotions implements BaseColumns{
        public static final String Table_Name="Promotions";
        public static final String Col_Name_id="Id";
        public static final String Col_Name_title="Title";
        public static final String Col_Name_description="Description";
        public static final String Col_Name_image="image";
        public static final String Col_Name_price="price";
        public static final String Col_Name_descount="Discount";



    }
}
