package com.mad.restaurant;

public class cart{

    private int Id;
    private String Name;
    private String Desc;
    private int Price;
    private int Qty;
    private byte[] Image;

    public cart( int id, String name, String desc, int price, int qty, byte[] image) {
        Id = id;
        Name = name;
        Desc = desc;
        Price = price;
        Qty = qty;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        this.Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }
}
