package com.sohos.totocafemobile.activities;

/**
 * Created by hakan on 30.12.2015.
 */
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sohos.totocafemobile.MyApplication;
import com.sohos.totocafemobile.R;
import com.sohos.totocafemobile.pojo.UserDetailsTable;
import com.sohos.totocafemobile.restful.JSONParser;
import com.sohos.totocafemobile.restful.RestAPI;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "HAKKE";

    @InjectView(R.id.input_name) EditText etName;
    @InjectView(R.id.input_surname) EditText etSurname;
    @InjectView(R.id.input_email) EditText etEmail;
    @InjectView(R.id.input_password) EditText etPassword;
    @InjectView(R.id.RadioGroupGender) RadioGroup rgGender;
    @InjectView(R.id.rbMale) RadioButton rbMale;
    @InjectView(R.id.rbFemale) RadioButton rbFemale;
    @InjectView(R.id.btn_signup) Button btnSignup;
    @InjectView(R.id.btnDate) Button btnDatePicker;
    @InjectView(R.id.link_login) TextView _loginLink;
    int year_x, month_x, day_x;
    static final int DIALOG_ID = 0;
    Context context;
    public String birthDate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        context = this;
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);
        showDialogOnButtonClick();


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void showDialogOnButtonClick(){
        Button btn = (Button) findViewById(R.id.btnDate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }
    protected Dialog onCreateDialog(int id){
        if(id == DIALOG_ID){
            return new DatePickerDialog(this, dpickerListener,year_x,month_x,day_x);
        }
        return  null;
    }
    private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_x = year;
            month_x = monthOfYear + 1;
            day_x = dayOfMonth;
            //Toast.makeText(RegisterActivity.this, year_x+"/"+month_x+"/"+day_x,Toast.LENGTH_LONG).show();
        }
    };

    public void signup() {
        Log.d(TAG, "Signup");
        birthDate = year_x + "-" + month_x + "-" + day_x;
        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnSignup.setEnabled(false);
        /*
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Base);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();
*/
        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        double gender = 1;
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == rbMale.getId()) {
            gender = 1;
        } else if (selectedId == rbFemale.getId()) {
            gender = 2;
        }

        Log.d("My date : ", birthDate);
        UserDetailsTable userDetail = new UserDetailsTable(name,surname,email,password,birthDate,gender);

        // TODO: Implement your own signup logic here.
        new AsyncCreateUser().execute(userDetail);

        /*
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
        */
    }


    protected class AsyncCreateUser extends
            AsyncTask<UserDetailsTable, Void, Void> {
        public String email = null;
        @Override
        protected Void doInBackground(UserDetailsTable... params) {

            RestAPI api = new RestAPI();
            try {

                api.CreateNewUser(params[0].getName(), params[0].getSurname(), params[0].getEmail(),
                        params[0].getPassword(), params[0].getBirthDate(),params[0].getGender());
                email = params[0].getEmail();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCreateUser", e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(SignupActivity.this , "Registration is success! " , Toast.LENGTH_SHORT).show();
            new AsyncGetUserID().execute(email);
            //Intent i = new Intent(SignupActivity.this, MainActivity.class);
            //startActivity(i);
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
                MyApplication.saveToPreferences(context, "logged_in", true);
                MyApplication.saveToPreferences(context, "UserID", result);
                Log.d(TAG, "Registered User ID is : " +  MyApplication.readFromPreferences(context,"UserID", -1));
                Intent intent = new Intent(SignupActivity.this,
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



    public void onSignupSuccess() {
        btnSignup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btnSignup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = etName.getText().toString();
        String surname = etSurname.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        //validations!
        if(name.isEmpty() || name.length() < 3){
            etName.setError("at least 3 characters");
            valid = false;
        } else{
            etName.setError(null);
        }

        if(surname.isEmpty() || surname.length() < 3){
            etSurname.setError("at least 3 characters");
            valid = false;
        } else{
            etSurname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("enter a valid email address");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etPassword.setError(null);
        }

        if(birthDate.isEmpty()){
            valid = false;
        }

          return valid;
    }
}