package com.sohos.totocafemobile.qr;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sohos.totocafemobile.MyApplication;
import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.activities.MainActivity;
import com.sohos.totocafemobile.restful.JSONParser;
import com.sohos.totocafemobile.restful.RestAPI;

import org.json.JSONObject;

/**
 * Created by hakan on 17.01.2016.
 *
 *
 MyApplication.saveToPreferences(context, "menuStatus",true);
 MyApplication.saveToPreferences(context, "companyID" ,(int)CompanyID);
    MASA OTURMA İŞLEMİ BAŞARILI OLURSA menuStatus true , companyID de artık ne ise o olarak eklenmiş olacak
    DEFAULT OLARAK menuStatus false, companyID de -1 dir
    Masa oturma işlemi başarılı olduğunda MainActivity deki menü kısmı artık aktif olabilmelidir.
    O yüzden sharedpreferencesdan menuStatus çağrılıp kontrol ettirilir.
        -Eğer true ise companyID çağrılır ve ona göre menu listelenir.
        -Eğer false ise başka layout çalışır ve menu gösterimi olmaz.
 *
 *
 *
 */
public class QrController extends AppCompatActivity {

    public static double TableID = 0;
    public static double CompanyID = 0;
    public static double UserID;
    Toolbar mToolbar;
    Context context;
    Button btnQrSender;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_controller);
        context = this;

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /////get UserID from sharedPreferences..
        UserID = MyApplication.readFromPreferences(context, "UserID", -1 );

        btnQrSender = (Button) findViewById(R.id.btnQrSender);

        btnQrSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkingQr()){
                    new AsyncCheckAvailabilityOfTable().execute(TableID);
                }
            }
        });




    }//end onCreate Method



    private boolean checkingQr() {
        boolean result = false;

        String qrValue = "";
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            qrValue = bundle.getString("qr");

            String[] arr = qrValue.split("-");
            if(arr.length == 3){
                if(arr[0].equals("TotoCafe") && arr[1].matches("\\d+") && arr[2].matches("\\d+")){
                    CompanyID = Double.parseDouble(arr[1]);
                    TableID = Double.parseDouble(arr[2]);
                    Toast.makeText(this, arr[0] + " , " + arr[1] + " , " + arr[2], Toast.LENGTH_LONG).show();
                    TextView tvDetails = (TextView) findViewById(R.id.tvDetails);
                    tvDetails.setText("You want to sit "+TableID+" named table \n If it is true :>>");
                    result = true;

                }
                else{
                    Toast.makeText(this, "Thief!", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Wrong QR CODE!", Toast.LENGTH_SHORT).show();
                result = false;
            }
        }
    return result;
    }//end checkingQr Method

    protected class AsyncCheckAvailabilityOfTable extends AsyncTask<Double, JSONObject, Boolean> {

        Double TableID = null;
        @Override
        protected Boolean doInBackground(Double... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.CheckAvailabilityOfTable(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);
                TableID = params[0];
                Log.d("TID ChckAvailable: ", "TableID: " + TableID );
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCheckAvailabilityOfTable", e.getMessage());

            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait... Check Availability of Table!",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            Log.d("Result on AsyncCheckAvailabilityOfTable : " , result.toString());
            //Check user validity
            if (result) { //Result -->
                Toast.makeText(context, "Table is Available. Now starting CheckTableControllerExist?",Toast.LENGTH_SHORT).show();
                new AsyncCheckTableControllerIsExist().execute(TableID);
                //Intent i = new Intent(LoginActivity.this,
                //      HomeActivity.class);
                //i.putExtra("android_id",android_id);
                //startActivity(i);
            }
            else // İLK kez giriş. Register yap.
            {
                Toast.makeText(context, "Table FROZEN, Contact Manager!",Toast.LENGTH_SHORT).show();

                //  new AsyncAnonymousRegister().execute(android_id);
            }

        }

    }

    protected class AsyncCheckTableControllerIsExist extends AsyncTask<Double, JSONObject, Boolean> {
        //ASYNCTASK 1. PARAMETRE -- doInBackground
        //2.PARAMETRE -> onProgressUpdate
        //3.PARAMETRE -> onPostExecute ündür!
        double TableID=0;
        @Override
        protected Boolean doInBackground(Double... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.CheckTableControllerIsExist(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);
                TableID =params[0];
                Log.d("TableID in AsyncCheckTableControllerIsExist", "Table id:" +TableID);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCheckTableControllerIsExist", e.getMessage());

            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait... Check Table Controller Exist",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            Log.d("Result on AsyncCheckTableControllerIsExist : " , result.toString());

            //Check user validity
            if (result) { //Result --> Git Login Yap
                Toast.makeText(context, "Masa Boş, sırada InsertRequestTableViaQr işlemi var!",Toast.LENGTH_SHORT).show();
                //UserID taa login register dan geliyor buralara
                Log.d("QRCODEbeforeInsertRequest", UserID + "," + CompanyID + "," + TableID);
                QrRequestTable qrRequestTable = new QrRequestTable(UserID,CompanyID,TableID);
                new AsyncInsertRequestTableViaQr().execute(qrRequestTable);

            }
            else // İLK kez giriş. Register yap.
            {
                Toast.makeText(context, "Masa Şuan Kullanımda!",Toast.LENGTH_SHORT).show();

            }

        }

    }

    protected class AsyncInsertRequestTableViaQr extends AsyncTask<QrRequestTable, Void, Void> {

        @Override
        protected Void doInBackground(QrRequestTable... params) {

            RestAPI api = new RestAPI();
            try {

                api.InsertRequestTableViaQr(params[0].getUserID(),params[0].getCompanyID(),params[0].getTableID());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncInsertRequestTableViaQr", e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //Log.d("Result on the INSERT REQUEST TABLE", result.toString());
            Toast.makeText(context , "Insert Request Table Via Qr - BAŞARILI!! " , Toast.LENGTH_SHORT).show();
            //Intent i = new Intent(RegisterActivity.this, HomeActivity.class);
            //startActivity(i);

            //ARTIK SERVER tarafından masa onayı beklendiği için son check Request işlemi kaldı
            QrRequestTable qrRequestTable = new QrRequestTable(UserID,CompanyID,TableID);
            new AsyncCheckRequestTableFlag().execute(qrRequestTable);
        }

    }

    //SON ASNYTASK CheckRequestTableFlag --
    //InsertRequestTableViaQR metotu ile flag = 0 olarak yollandı.
    //Sırada cevap bekliyoruz. CheckRequestTableFlag dan flag değişirse
    // bool olarak 'TRUE' döndürürse artık masa bizimdir!
    protected class AsyncCheckRequestTableFlag extends AsyncTask<QrRequestTable,Void,Boolean>{
        double TableID = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context,"Waiting for response from Server...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(QrRequestTable... params) {
            RestAPI api = new RestAPI();
            boolean authentication = false;
            try {
                do{
                    // Call the User Authentication Method in API
                    JSONObject jsonObj = api.CheckRequestTableFlag(params[0].getUserID(),params[0].getCompanyID(),params[0].getTableID());

                    //Parse the JSON Object to boolean
                    JSONParser parser = new JSONParser();
                    authentication = parser.parseUserAuth(jsonObj);
                    TableID = params[0].getTableID();
                    Thread.sleep(2000);
                }while(authentication);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCheckRequestTableFlag", e.getMessage());

            }
            return authentication;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            Log.d("SONUC ", result.toString());

            if(result){ // result is true! Response is receiving from server!!
                Intent i = new Intent(context, MainActivity.class);

                MyApplication.saveToPreferences(context, "menuStatus",true);
                MyApplication.saveToPreferences(context, "companyID" ,(int)CompanyID);
                startActivity(i);

            }
            else{
                try {
                    Thread.sleep(1000);
                    QrRequestTable qrRequestTable = new QrRequestTable(UserID,CompanyID,TableID);
                    new AsyncCheckRequestTableFlag().execute(qrRequestTable);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }




}
