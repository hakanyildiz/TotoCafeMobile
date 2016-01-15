package com.sohos.totocafemobile.pojo;

/**
 * Created by hakan on 15.01.2016.
 */
public class CategoryTable{
	private int CategoryID;
    private String CategoryName;
    private int AvailabilityID;
    private int CompanyID;
	
	public CategoryTable() {

    }
	
	public CategoryTable (int CategoryID,
                          String CategoryName,
                          int AvailabilityID,
                          int CompanyID) 
	{
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.AvailabilityID = AvailabilityID;
        this.CompanyID = CompanyID;
    }
	/* CategoryID */
	public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        this.CategoryID = CategoryID;
    }
	
	/* CategoryName */
    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String CategoryName) {
        this.CategoryName = CategoryName;
    }
	
	/* AvailabilityID */
	public int getAvailabilityID() {
        return AvailabilityID;
    }

    public void setAvailabilityID(int AvailabilityID) {
        this.AvailabilityID = AvailabilityID;
    }
	
	/* CompanyID */
	public int getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(int CompanyID) {
        this.CompanyID = CompanyID;
    }
    
	
	
}
