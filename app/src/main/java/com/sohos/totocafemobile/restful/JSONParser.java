package com.sohos.totocafemobile.restful;

/**
 * Created by okano on 22.11.2015.
 */
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.sohos.totocafemobile.pojo.CategoryTable;
import com.sohos.totocafemobile.pojo.UserDetailsTable;

public class JSONParser {
	public static final String myLog = "Hakke";
    public JSONParser()
    {
        super();
    }
    /*
    public ArrayList<DeptTable> parseDepartment(JSONObject object)
    {
        ArrayList<DeptTable> arrayList=new ArrayList<DeptTable>();
        try {
            JSONArray jsonArray=object.getJSONArray("Value");
            JSONObject jsonObj=null;
            for(int i=0;i<jsonArray.length();i++)
            {
                jsonObj=jsonArray.getJSONObject(i);
                arrayList.add(new DeptTable(jsonObj.getInt("no"), jsonObj.getString("name")));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseDepartment", e.getMessage());
        }
        return arrayList;
    }
*/						
	public ArrayList<CategoryTable> parseCategory(JSONObject object)
	{
		ArrayList<CategoryTable> arrayList = new ArrayList<CategoryTable>();
		try{
			JSONArray jsonArray = object.getJSONArray("Value");
			JSONObject jsonObj = null;
			
			for(int i = 0; i < jsonArray.length(); i++)
			{
				jsonObj = jsonArray.getJSONObject(i);
				arrayList.add(new CategoryTable(
												jsonObj.getInt("CategoryID"),
												jsonObj.getString("CategoryName"),
												jsonObj.getInt("AvailabilityID"),
												jsonObj.getInt("CompanyID")
											   )
							 );
			}
			
			Log.d(myLog , "Categories: " + arrayList);
		}catch(JSONException e){
			Log.d(myLog , e.getMessage());
		}
		
		return arrayList;
	}


    public boolean parseUserAuth(JSONObject object)
    {     boolean userAtuh=false;
        try {
            userAtuh= object.getBoolean("Value");
            Log.d("HAKKE", "parseUserAuth: " +  userAtuh);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseUserAuth", e.getMessage());
        }

        return userAtuh;
    }

    public UserDetailsTable parseUserDetails(JSONObject object)
    {
        UserDetailsTable userDetail=new UserDetailsTable();

        try {
            JSONObject jsonObj=object.getJSONArray("Value").getJSONObject(0);

            userDetail.setName(jsonObj.getString("name"));
            userDetail.setSurname(jsonObj.getString("surname"));

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d("JSONParser => parseUserDetails", e.getMessage());
        }

        return userDetail;

    }

    public int parseGetUserID(JSONObject object){
        int userID = -1;
        try{
            userID = object.getInt("Value");

        } catch (JSONException e) {
            Log.d("JSONParser => parseGetUserID", e.getMessage());
        }
        return userID;
    }



}