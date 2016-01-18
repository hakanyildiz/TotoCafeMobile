package com.sohos.totocafemobile.activities;

/**
 * Created by hakan on 30.12.2015.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings.Secure;

import com.sohos.totocafemobile.MyApplication;
import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.restful.JSONParser;
import com.sohos.totocafemobile.restful.RestAPI;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    public static final String PREF_NAME = "COOKIE";

    private static final String TAG = "HAKKE";
    private static final int REQUEST_SIGNUP = 0;
    Context context;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
    @InjectView(R.id.btnAnonymousLogin) Button _loginAnonimButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        checkLogged();

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //login();

                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();

                // TODO: Implement your own authentication logic here.
                // Execute the AsyncLogin class
                new AsyncLogin().execute(email,password);

            }
        });

        _loginAnonimButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String android_id = Secure.getString(getApplication().getContentResolver(),
                        Secure.ANDROID_ID);

                new AsyncAnonymousLogin().execute(android_id);
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    private void checkLogged() {

        //SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        Intent i = null;
        //if(sharedPreferences.getBoolean("logged_in",false)){ // NO LOGGED

        boolean result = MyApplication.readFromPreferences(context,"logged_in",false);

        Log.d("HAKKE", "My current LOGIN ACTIVITY : logged_in SharedPreferences is : " + result);


        if(result){
            //user already logged
            i = new Intent(getBaseContext(),MainActivity.class);
            startActivity(i);
        }

    }

    public void login() {
        //Login Success e eklenir.
        //saveToLoggedInPreferences(getBaseContext(),"logged_in",true);

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Base);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        // Execute the AsyncLogin class
        new AsyncLogin().execute(email,password);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);


    }

    // NORMAL LOGİN
    protected class AsyncLogin extends AsyncTask<String, JSONObject, Boolean> {

        String email=null;
        String pass = null;
        @Override
        protected Boolean doInBackground(String... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {
                // Call the User Authentication Method in API
                JSONObject jsonObj = api.UserAuthentication(params[0],
                        params[1]);
                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);
                email=params[0];
                pass = params[1];
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLogin", e.getMessage());
            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            Log.d(TAG,"My email:" + email + " My Password: " + pass);
            Log.d(TAG,"Result from Service for userAuthentication is : "+ result.toString());
            //Check user validity
            if (result) {
                MyApplication.saveToPreferences(getBaseContext(),"logged_in",true);

                new AsyncGetUserID().execute(email);
            }
            else
            {
                MyApplication.saveToPreferences(getBaseContext(),"logged_in",false);
                Toast.makeText(context, "Not valid username/password ",Toast.LENGTH_SHORT).show();
            }

            Log.d(TAG,"Logged in is now : " + MyApplication.readFromPreferences(getBaseContext(),"logged_in",false));

        }

    }

    protected class AsyncGetUserID extends AsyncTask<String, JSONObject, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            RestAPI api = new RestAPI();
            int userID = -1;
            try {
                // Call the User Authentication Method in API
                JSONObject jsonObj = api.GetUserID(params[0]);
                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userID = parser.parseGetUserID(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncGetUserID", e.getMessage());
            }
            return userID;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Integer result) {
            // TODO Auto-generated method stub

            //Check user validity
            if (result != -1) {
                MyApplication.saveToPreferences(context,"UserID",result);
                Log.d(TAG, "UserID is now : " + MyApplication.readFromPreferences(getBaseContext(), "UserID", -1));

                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);

                //MyApplication.createBundleSendingEmail(email, intent);

                startActivity(intent);
            }
            else
            {
                Toast.makeText(context, "GetUserID is failed! :/ ",Toast.LENGTH_SHORT).show();
            }

        }

    }

    //ANONYMOUS LOGİN
    protected class AsyncAnonymousLogin extends AsyncTask<String, JSONObject, Boolean> {

        String android_id=null;
        @Override
        protected Boolean doInBackground(String... params) {

            RestAPI api = new RestAPI();
            boolean userAuth = false;
            try {

                // Call the User Authentication Method in API
                JSONObject jsonObj = api.AnonymousAuthentication(params[0]);

                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userAuth = parser.parseUserAuth(jsonObj);
                android_id =params[0];
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncLogin", e.getMessage());

            }
            return userAuth;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub

            //Check user validity
            if (result) { //Result --> Git Login Yap
                Toast.makeText(context, "Anonymous Login...",Toast.LENGTH_SHORT).show();
                /*
                Intent i = new Intent(LoginActivity.this,
                        MainActivity.class);
                MyApplication.createBundleSendingEmail(android_id, i);

                startActivity(i);
                */
                MyApplication.saveToPreferences(getBaseContext(),"logged_in",true);
                Log.d(TAG, "Loggedin With Anonim is now : " + MyApplication.readFromPreferences(getBaseContext(), "logged_in", false));

                new AsyncGetAnonymousID().execute(android_id);

            }
            else // İLK kez giriş. Register yap.
            {
                Toast.makeText(context, "Anonymous Registration...",Toast.LENGTH_SHORT).show();

                new AsyncAnonymousRegister().execute(android_id);
            }

        }

    }

    //ANONYMOUS REGİSTER
    protected class AsyncAnonymousRegister extends AsyncTask<String, Void, Void> {

        String android_id=null;
        @Override
        protected Void doInBackground(String... params) {

            RestAPI api = new RestAPI();

            try {

                api.CreateNewAnonymous(params[0]);


            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncAnonymousRegister", e.getMessage());

            }
            return  null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            MyApplication.saveToPreferences(getBaseContext(),"logged_in",true);
            Log.d(TAG, "Register: logged with Anonim is now : " + MyApplication.readFromPreferences(getBaseContext(), "logged_in", false));

            new AsyncGetAnonymousID().execute(android_id);


        }

    }

    protected class AsyncGetAnonymousID extends AsyncTask<String, JSONObject, Integer> {

        @Override
        protected Integer doInBackground(String... params) {

            RestAPI api = new RestAPI();
            int userID = -1;
            try {
                // Call the User Authentication Method in API
                JSONObject jsonObj = api.GetAnonymousID(params[0]);
                //Parse the JSON Object to boolean
                JSONParser parser = new JSONParser();
                userID = parser.parseGetUserID(jsonObj);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncGetAnonymousID", e.getMessage());
            }
            return userID;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            Toast.makeText(context, "Please Wait...",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Integer result) {
            // TODO Auto-generated method stub

            //Check user validity
            if (result != -1) {
                MyApplication.saveToPreferences(context,"UserID",result);
                Log.d(TAG, "anonim.. UserID is now : " + MyApplication.readFromPreferences(getBaseContext(), "UserID", -1));

                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                //MyApplication.createBundleSendingEmail(email, intent);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(context, "getAnonymousID is failed! ",Toast.LENGTH_SHORT).show();
            }

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }





    //COOKIE adlı preference a login basarılı olursa logged_in  true ekliyoruz
    public static void saveToLoggedInPreferences(Context context, String preferenceName, boolean preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putBoolean(preferenceName, preferenceValue);
        editor.apply();

    }
}