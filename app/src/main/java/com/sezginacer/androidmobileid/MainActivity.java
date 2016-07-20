package com.sezginacer.androidmobileid;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sezginacer.idgenerator.IDCreator;

public class MainActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CODE = 61;

    private void show(ListView listView){
        IDCreator idc = new IDCreator(this);
        String[] array = new String[7];
        array[0] = "Wi-Fi MAC Address\n" + idc.getMacAddress();
        array[1] = "Bluetooth MAC Address\n" + idc.getBluetoothHWaddr();
        array[2] = "IMEI Number\n" + idc.getIMEI();
        array[3] = "SIM Serial Number\n" + idc.getSimSerial();
        array[4] = "Android Version\n" + idc.getAndroidVersion();
        array[5] = "Android ID (that Android gives)\n" + idc.getAndroidID();
        array[6] = "Overall AndroidMobileID\n" + idc.getAndroidMobileID();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, android.R.id.text1, array);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_REQUEST_CODE);
        }
        else{
            ListView listView = (ListView) findViewById(R.id.listView);
            show(listView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ListView listView = (ListView) findViewById(R.id.listView);
                    show(listView);
                } else {
                    Toast.makeText(this, "In order to create ID, app have to access your phone's state!", Toast.LENGTH_SHORT).show();
                    finish();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
}
