package com.codingblocks.device_admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.work.Worker;

public class MyWorker extends Worker {
    public String dateToStr;

    @NonNull
    @Override
    public Result doWork() {

        Context context=getApplicationContext();

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        String email1=sharedPreferences.getString("email","");

        try {


            GMailSender sender = new GMailSender("lvngaur@gmail.com", "Android2018");


            sender.sendMail("Someone tried to unlock your " + MainActivity.getDeviceName(),


                    "The approximate timing was " + getDateToStr(), "lvngaur@gmail.com",


                    " lavanyagaur22@gmail.com");

            Log.e("TAG", "doInBackground----------: " + MainActivity.getDeviceName());

            Log.e("TAG", "doInBackground----------: "+email1 );


            return Result.SUCCESS;


        } catch (Exception e) {


            Log.e("error", e.getMessage(), e);

            return Result.FAILURE;


        }
    }

    public String getDateToStr() {
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        dateToStr = format.format(today);
        return dateToStr;

    }
}
