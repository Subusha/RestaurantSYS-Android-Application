package com.mad.restaurant;

public class promo_model {
    private int id;
    private String Title;
    private String Description;
    private byte[] image;
    private String price;
    private String discount;

    public promo_model(int id, String title, String description, byte[] image, String price, String discount) {
        this.id = id;
        Title = title;
        Description = description;
        this.image = image;
        this.price = price;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}


