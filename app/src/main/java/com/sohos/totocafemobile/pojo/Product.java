package com.sohos.totocafemobile.pojo;

/**
 * Created by hakan on 15.01.2016.
 */
public class Product{
	private int ProductID;
    private String ProductName;
    private String Detail;
    private int Price;
	private int Credit;
	private int CategoryID;
	private int AvailabilityID;
	
	
	public Product() {

    }
	
	public Product(int ProductID,
                   String ProductName,
				   String Detail,
				   int Price,
				   int Credit,
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
	public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }
	
	/* Credit */
	public int getCredit() {
        return Credit;
    }

    public void setCredit(int Credit) {
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
	
	
    
	
	
}
