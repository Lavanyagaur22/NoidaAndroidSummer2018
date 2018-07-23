package com.codingblocks.device_admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.work.Worker;

import static android.content.Context.MODE_PRIVATE;

public class MyWorker extends Worker {
    public String dateToStr;

    @NonNull
    @Override
    public Result doWork() {

        Context context=getApplicationContext();

        SharedPreferences sharedPreferences= context.getSharedPreferences("my preference", MODE_PRIVATE);
        String email1=sharedPreferences.getString("email", "" );

        try {


            //add your default code, was just checking !!! :)
            GMailSender sender = new GMailSender("example@email.com", "lol, cant get it");


            sender.sendMail("Someone tried to unlock your " + MainActivity.getDeviceName(),


                    "The approximate timing was " + getDateToStr(), "lvngaur@gmail.com",


                    " lavanyagaur22@gmail.com");

            Log.e("TAG", "doInBackground----------: " + MainActivity.getDeviceName());

            Log.e("TAG", "doInBackground----------: "+ email1 );


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
