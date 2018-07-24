package com.codingblocks.device_admin;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    String emailfinal;
    TextView textViewEmail;
    TextView clicktv;
    AlertDialog customDialog;
    AlertDialog alertDialog;
    Switch switch1;
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    private static final int ADMIN_INTENT = 15;
    private static final String description = "You need to activate Device Administrator to use this application";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switch1 = findViewById(R.id.switch1);
        textViewEmail = findViewById(R.id.tvemail);
        clicktv = findViewById(R.id.clicktv);

        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, MyAdminReceiver.class);

        switch1.setOnCheckedChangeListener(this);
        clicktv.setOnClickListener(this);

        Log.e("TAG", "onCreate:-------- ");

//        sharedPreferences = getSharedPreferences("my preference", MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        editor.putString("email", emailfinal);
//
//        editor.apply();


    }


    private ComponentName getActiveComponentName() { //gets the current active component

        ComponentName componentName = null;

        List<ComponentName> activeComponentList = mDevicePolicyManager.getActiveAdmins();

        Iterator<ComponentName> iterator = activeComponentList.iterator();

        while (iterator.hasNext()) {

            componentName = (ComponentName) iterator.next();
        }
        return componentName;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {

//        switchColor(b);

        if (b) {

            alertDialog = new AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    // .setCancelable(false)   bahr click karne pe cancellable nahi hoga
                    .setMessage("This app uses the Device\nAdministrator permission to monitor screen unlock attempts." +
                            "\n\nPlease activate this permission on the following screen." +
                            "\n\nYou can remove this permission at any time by switching off the alert emails." +
                            "\n\nThe app will never wipe your phone's data despite the warning shown.")

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.e("TAG", "onClick positive : yipee");

                            Log.e("TAG", "onClick: checked " + b);
                            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
                            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, description);
                            startActivityForResult(intent, ADMIN_INTENT);

                        }
                    })

                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.e("TAG", "onClick negative : nop");
                            switch1.setChecked(false);
                            mDevicePolicyManager.removeActiveAdmin(mComponentName);
                        }
                    })
                    .create();

            alertDialog.show();

        } else {
            Log.e("TAG", "onCheckedChanged: " + b);

        }

    }

    public void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADMIN_INTENT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Registered As Admin", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getApplicationContext(), "Failed to register as Admin", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }

        Log.e("TAG", "getDeviceName: " + capitalize(manufacturer) + " " + model);
        return capitalize(manufacturer) + " " + model;

    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }


    @Override
    public void onClick(View view) {

        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view, null, true);

        customDialog = new AlertDialog.Builder(this)
                .setTitle("Your Email here ! ")
                .setView(dialogView)

                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EditText emailEt = dialogView.findViewById(R.id.etEmail);
                        String email = emailEt.getText().toString();
                        emailfinal = email;
                        textViewEmail.setVisibility(View.VISIBLE);
                        textViewEmail.setText(email);

                        sharedPreferences = getSharedPreferences("my preference", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.apply();
                        Toast.makeText(MainActivity.this, "yo " + email, Toast.LENGTH_SHORT).show();

                    }
                })
                .create();
        customDialog.show();

    }

//    private void switchColor(boolean checked) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            switch1.getThumbDrawable().setColorFilter(checked ? Color.RED : Color.GREEN, PorterDuff.Mode.MULTIPLY);
//            switch1.getTrackDrawable().setColorFilter(!checked ? Color.BLUE : Color.WHITE, PorterDuff.Mode.MULTIPLY);
//        }
//    }


    public String senderemail() {
        return emailfinal;
    }


}
