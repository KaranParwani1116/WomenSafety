package com.example.womensafety;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.example.womensafety.utilities.Preference;


public class MainActivity extends AppCompatActivity {

    private static final int Permission_All = 1;
    private ImageView AlertButton;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.Exit));
        builder.setMessage(getString(R.string.Exit_Message));
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("no", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.preference:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] Permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.SEND_SMS, Manifest.permission.READ_PHONE_STATE};

        //Initializing location Manager

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Checking Runtime Permissions

        if (!haspermission(this, Permissions)) {
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }

        //Initializing Views
        AlertButton = (ImageView) findViewById(R.id.alert);

        AlertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendsms();
            }
        });

    }

    private void sendsms() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String PhoneNumber1 = "";
        String PhoneNumber2 = "";
        String Message = "";
        Double latitude=0.0;
        Double longitude;

        if (preferences.contains(Preference.Number1)) {
            PhoneNumber1 = "+91" + preferences.getString(Preference.Number1, "");
        }

        if (preferences.contains(Preference.Number2)) {
            PhoneNumber2 = "+91" + preferences.getString(Preference.Number2, "");
        }

        if (preferences.contains(Preference.Message)) {
            Message = preferences.getString(Preference.Message, "");
        }

        LocationListener locationListener = new MyLocationListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            latitude=MyLocationListener.latitude;
            longitude=MyLocationListener.longitude;
            Message+= "\n My Location is https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude;
        }

        if(PhoneNumber1.length()>=3 && PhoneNumber2.length()>=3 && Message!=null)
        {
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(PhoneNumber1,null,Message,null,null);
            smsManager.sendTextMessage(PhoneNumber2,null,Message,null,null);
            Toast.makeText(this, "SMS Sent", Toast.LENGTH_LONG).show();
        }

    }

    public static boolean haspermission(Context context,String... permissions)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && context!=null && permissions!=null)
        {
            for (String Permission:permissions)
            {
                if(ActivityCompat.checkSelfPermission(context,Permission)!= PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }

        return true;
    }
}
