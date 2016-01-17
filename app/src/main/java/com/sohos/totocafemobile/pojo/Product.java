package com.sohos.totocafemobile.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hakan on 15.01.2016.
 */
public class Product implements Parcelable{
	private int ProductID;
    private String ProductName;
    private String Detail;
    private float Price;
	private float Credit;
	private int CategoryID;
	private int AvailabilityID;

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

	public Product() {

    }
	
	public Product(int ProductID,
                   String ProductName,
				   String Detail,
				   float Price,
				   float Credit,
                   int CategoryID,
                   int AvailabilityID) 
	{
		
        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.Detail = Detail;
		this.Price = Price;
		this.Credit = Credit;
		this.CategoryID = CategoryID;
        this.AvailabilityID = AvailabilityID;
    }

    public Product(Parcel input){
        ProductID = input.readInt();
        ProductName = input.readString();
        Detail = input.readString();
        Price = input.readFloat();
        Credit = input.readFloat();
        CategoryID = input.readInt();
        AvailabilityID = input.readInt();
    }

	/* ProductID */
	public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }
	
	/* ProductName */
    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }
	
	/* Detail */
    public String getDetail() {
        return Detail;
    }

    public void setDetail(String Detail) {
        this.Detail = Detail;
    }
	
	
	/* Price */
	public float getPrice() {
        return Price;
    }

    public void setPrice(float Price) {
        this.Price = Price;
    }
	
	/* Credit */
	public float getCredit() {
        return Credit;
    }

    public void setCredit(float Credit) {
        this.Credit = Credit;
    }
	
	/* CategoryID */
	public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }
	
	/* AvailabilityID */
	public int getAvailabilityID() {
        return AvailabilityID;
    }

    public void setAvailabilityID(int AvailabilityID) {
        this.AvailabilityID = AvailabilityID;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ProductID);
        dest.writeString(ProductName);
        dest.writeString(Detail);
        dest.writeFloat(Price);
        dest.writeFloat(Credit);
        dest.writeInt(CategoryID);
        dest.writeInt(AvailabilityID);

    }
}
