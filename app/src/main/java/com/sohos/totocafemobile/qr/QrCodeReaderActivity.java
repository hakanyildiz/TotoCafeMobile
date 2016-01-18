package com.sohos.totocafemobile.qr;

/**
 * Created by hakan on 16.01.2016.
 *
 */
 import android.content.Intent;
 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.util.Log;

 import com.sohos.totocafemobile.R;
 import me.dm7.barcodescanner.zxing.ZXingScannerView;
 import com.google.zxing.Result;

public class QrCodeReaderActivity  extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_reader);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause

    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("HAKKE", rawResult.getText()); // Prints scan results
        Log.v("HAKKE", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Intent intent = new Intent(QrCodeReaderActivity.this, QrController.class ); // Simdilik Deneme

        //creating bundle for transferring qr code result to Controller class
        Bundle toQrController = new Bundle();
        toQrController.putString("qr",rawResult.getText());
        intent.putExtras(toQrController);

        startActivity(intent);

    }




}
