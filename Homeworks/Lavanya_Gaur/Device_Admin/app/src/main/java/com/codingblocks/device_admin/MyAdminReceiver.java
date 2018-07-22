package com.codingblocks.device_admin;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.UserHandle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MyAdminReceiver extends DeviceAdminReceiver {

    public String dateToStr;


    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.e("TAG", "onEnabled: Device Administrator: Activated");
        super.onEnabled(context, intent);
    }

    @Override
    //callback whenever the password fails
    public void onPasswordFailed(Context context, Intent intent, UserHandle user) {
        super.onPasswordFailed(context, intent, user);

        Vibrator v = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        // Vibrate for 2 seconds
        v.vibrate(2000);

        Log.e("TAG", "onPasswordFailed--------------: ");

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        dateToStr = format.format(today);
        Log.e("TAG", "onPasswordFailed:  date " + dateToStr);

        callEmailSendWorker();
        Log.e("TAG", "onPasswordFailed:       ---------        ----------       emailsendcall");


//        try {
//
//            LongOperation l = new LongOperation();
//
//            l.execute();  //sends the email in background
//
////            Toast.makeText(context, l.get(), Toast.LENGTH_SHORT).show();
//
//
//        } catch (Exception e) {
//
//            Log.e("SendMail", e.getMessage(), e);
//
//        }
    }


    @Override
    public void onPasswordSucceeded(Context context, Intent intent, UserHandle user) {
        Log.e("TAG", "onPasswordSucceeded: **");
        super.onPasswordSucceeded(context, intent, user);
    }

//    public void callEmailSend() {
//        try {
//
////            LongOperation l = new LongOperation();
//            LongOperation l = new LongOperation();
//
//            l.execute();  //sends the email in background
//
//
//        } catch (Exception e) {
//
//            Log.e("SendMail", e.getMessage(), e);
//
//        }
//    }

    public void callEmailSendWorker() {

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(MyWorker.class)
                .setInitialDelay(0, TimeUnit.SECONDS)
                .build();


        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }



    public void taketheImage(){

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(MyWorker2.class)
                .setInitialDelay(0, TimeUnit.SECONDS)
                .build();


        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
    }


}
