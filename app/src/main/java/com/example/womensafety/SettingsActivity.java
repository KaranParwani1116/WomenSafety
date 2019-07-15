package com.example.womensafety;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.womensafety.utilities.Preference;

public class SettingsActivity extends AppCompatActivity {
    private EditText PhoneNumber1;
    private EditText PhoneNumber2;
    private EditText Email;
    private EditText Message;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        PhoneNumber1=(EditText)findViewById(R.id.Number1);
        PhoneNumber2=(EditText)findViewById(R.id.Number2);
        Email=(EditText)findViewById(R.id.email);
        Message=(EditText)findViewById(R.id.messa);
        button=(Button)findViewById(R.id.Save);

        checkdata();

        storedata();
    }

    private void checkdata() {
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);

        if(prefs.contains(Preference.Message))
        {
            String message=prefs.getString(Preference.Message,"");
            Message.setText(message);
        }

        if(prefs.contains(Preference.Email))
        {
            String email=prefs.getString(Preference.Email,"");
            Email.setText(email);
        }

        if(prefs.contains(Preference.Number1))
        {
            String Number1=prefs.getString(Preference.Number1,"");
            PhoneNumber1.setText(Number1);
        }
        if(prefs.contains(Preference.Number2))
        {
            String Number2=prefs.getString(Preference.Number2,"");
            PhoneNumber2.setText(Number2);
        }
    }

    private void storedata() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Number1=PhoneNumber1.getText().toString().trim();
                String Number2=PhoneNumber2.getText().toString().trim();
                String Emails=Email.getText().toString().trim();
                String Messages=Message.getText().toString().trim();



                if(Emails.isEmpty() || Messages.isEmpty() || Number1.isEmpty() || Number2.isEmpty())
                {
                    Toast.makeText(SettingsActivity.this,"Fill All Your Emergency Details",Toast.LENGTH_LONG).show();
                }
                else
                {
                     Preference.Storedata(SettingsActivity.this,Number1,Number2,Emails,Messages);
                     Toast.makeText(SettingsActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
                     finish();
                }
            }
        });
    }

}
