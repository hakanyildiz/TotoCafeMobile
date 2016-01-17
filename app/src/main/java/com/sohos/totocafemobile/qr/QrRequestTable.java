package com.sohos.totocafemobile.qr;

/**
 * Created by hakan on 10.12.2015.
 */
public class QrRequestTable {


    double UserID, CompanyID , TableID;

    public QrRequestTable( double UserID,double CompanyID,  double TableID) {
        super();
        this.UserID = UserID;
        this.CompanyID = CompanyID;
        this.TableID = TableID;
    }

    public QrRequestTable() {
        super();
        this.UserID = 0;
        this.CompanyID = 0;
        this.TableID = 0;


    }

    public double getUserID() {
        return UserID;
    }
    public void setUserID(Double UserID) {
        this.UserID = UserID;
    }

    public double getCompanyID() {
        return CompanyID;
    }
    public void setCompanyID(Double CompanyID) {
        this.CompanyID = CompanyID;
    }

    public double getTableID() {
        return TableID;
    }
    public void setTableID(Double TableID) {
        this.TableID = TableID;
    }


}
