package com.sohos.totocafemobile.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sohos.totocafemobile.MyApplication;
import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.activities.MainActivity;
import com.sohos.totocafemobile.activities.ProductActivity;
import com.sohos.totocafemobile.adapters.AdapterCategory;
import com.sohos.totocafemobile.adapters.CustomItemClickListener;
import com.sohos.totocafemobile.network.VolleySingleton;
import com.sohos.totocafemobile.pojo.CategoryTable;
import com.sohos.totocafemobile.restful.JSONParser;
import com.sohos.totocafemobile.restful.RestAPI;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import javax.xml.transform.ErrorListener;


public class FragmentMenu extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView listMovieHits;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private AdapterCategory adapterCategory;
    ArrayList<String> data;
    ArrayList<CategoryTable> categoryList = new ArrayList<CategoryTable>();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =inflater.inflate(R.layout.fragment_menu, container, false);
        boolean menuStatus = MyApplication.readFromPreferences(getActivity(), "menuStatus", false);
       // int companyID = MyApplication.readFromPreferences(getActivity(), "companyID", -1);

        listMovieHits = (RecyclerView) layout.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterCategory = new AdapterCategory(getActivity(), categoryList, new CustomItemClickListener() {
            @Override
            public void onItemClicked(View v, int position) {
                int categoryID = categoryList.get(position).getCategoryID();
                Log.d("HAKKE", "clicked categoryID " + categoryID);
                Intent i = new Intent(getActivity(), ProductActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("categoryID" , categoryID);
                i.putExtras(bundle);

                startActivity(i);

            }
        });
        listMovieHits.setAdapter(adapterCategory);

        /*
        if (!menuStatus) { // true ise demekki qr okuma falan başarılı.. Categorileri gösterme vakti
            getCompaniesFromService(companyID);
        }

*/      getCompaniesFromService(13);
        return layout;
    }

    private void getCompaniesFromService(int companyID) {
        new AsyncLoadCategoriesOfCompany().execute(companyID);

    }


    protected class AsyncLoadCategoriesOfCompany extends AsyncTask<Integer, JSONObject, ArrayList<CategoryTable>> {
        ArrayList<CategoryTable> cateTable = new ArrayList<CategoryTable>();

        @Override
        protected ArrayList<CategoryTable> doInBackground(Integer... params) {
            // TODO Auto-generated method stub

            RestAPI api = new RestAPI();
            try {
                JSONObject jsonObj = api.getCategoriesOfCompany(params[0]);
                JSONParser parser = new JSONParser();
                cateTable = parser.parseCategory(jsonObj);
                Log.d("HAKKE",  "KATEGORI in cateTable:  " +  cateTable.get(0).getCategoryName());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLoadDeptDetails", e.getMessage());

            }

            return cateTable;
        }

        @Override
        protected void onPostExecute(ArrayList<CategoryTable> result) {
            // TODO Auto-generated method stub
            Log.d("HAKKE", "konum : "  + result.get(0).getCategoryName());
            Log.d("HAKKE", "result.size : "  + result.toString());

            for (int i = 0; i < result.size(); i++) {
                //data.add(result.get(i).getCategoryID() + " " + result.get(i).getCategoryName());

                CategoryTable myCategory = new CategoryTable();

                Log.d("HAKKE", "My Category NAME: " + result.get(i).getCategoryName());

                myCategory.setCategoryID(result.get(i).getCategoryID());
                myCategory.setCategoryName(result.get(i).getCategoryName());
                myCategory.setAvailabilityID(result.get(i).getAvailabilityID());
                myCategory.setCompanyID(result.get(i).getCompanyID());

                categoryList.add(myCategory);
            }
            //send to current categoryList to adapterCategory Class
            adapterCategory.setCategoryList(categoryList);
            //adapterCategory.notifyDataSetChanged();
            //adapter.notifyDataSetChanged();
            Toast.makeText(getContext(),"Getting Categories Completed",Toast.LENGTH_SHORT).show();
        }

    }




}
