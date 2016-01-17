package com.sohos.totocafemobile.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sohos.totocafemobile.MyApplication;
import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.network.VolleySingleton;
import com.sohos.totocafemobile.pojo.CategoryTable;
import com.sohos.totocafemobile.restful.JSONParser;
import com.sohos.totocafemobile.restful.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.transform.ErrorListener;


public class FragmentMenu extends /**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMenu#newInstance} factory method to
 * create an instance of this fragment.
 */Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    ArrayList<String> data;
    ArrayList<CategoryTable> myCategoryTable = new ArrayList<CategoryTable>();
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMenu newInstance(String param1, String param2) {
        FragmentMenu fragment = new FragmentMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentMenu() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //volleySingleton = VolleySingleton.getInstance();
        //requestQueue = volleySingleton.getRequestQueue();
        //sendJsonRequest();
       /*
        boolean menuStatus = MyApplication.readFromPreferences(getActivity(), "menuStatus", false);
        int companyID = MyApplication.readFromPreferences(getActivity(), "companyID", -1);

        if (menuStatus) { // true ise demekki qr okuma falan başarılı.. Categorileri gösterme vakti
            getCompaniesFromService(companyID);
        }
        */
    }

    private void getCompaniesFromService(int companyID) {
        new AsyncLoadCategoriesOfCompany().execute(companyID);

    }
    protected class AsyncLoadCategoriesOfCompany extends AsyncTask<Integer, JSONObject, ArrayList<CategoryTable>> {
        ArrayList<CategoryTable> cateTable = null;

        @Override
        protected ArrayList<CategoryTable> doInBackground(Integer... params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {

                JSONObject jsonObj = api.getCategoriesOfCompany(params[0]);

                JSONParser parser = new JSONParser();

                cateTable = parser.parseCategory(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());

            }

            return cateTable;
        }

        @Override
        protected void onPostExecute(ArrayList<CategoryTable> result) {
            // TODO Auto-generated method stub
            CategoryTable myCategory = new CategoryTable();
            for (int i = 0; i < result.size(); i++) {
                data.add(result.get(i).getCategoryID() + " " + result.get(i).getCategoryName());

                myCategory.setCategoryID(result.get(i).getCategoryID());
                myCategory.setCategoryName(result.get(i).getCategoryName());
                myCategory.setAvailabilityID(result.get(i).getAvailabilityID());
                myCategory.setCompanyID(result.get(i).getCompanyID());

                myCategoryTable.add(myCategory);

            }

            //adapter.notifyDataSetChanged();

            Toast.makeText(getContext(),"Loading Completed",Toast.LENGTH_SHORT).show();
        }

    }


    private void sendJsonRequest() {
        String url = "";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, (String)null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                parseJSONResponse(response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();

                Log.d("Error = ", volleyError.toString());


            }
        });
        requestQueue.add(request);
    }

    private void parseJSONResponse(JSONObject response) {
        if(response == null || response.length() == 0){
            return;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

       

    }
}
