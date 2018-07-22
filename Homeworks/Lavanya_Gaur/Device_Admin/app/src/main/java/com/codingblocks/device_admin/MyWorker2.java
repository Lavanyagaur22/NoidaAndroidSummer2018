package com.codingblocks.device_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import androidx.work.Worker;

public class MyWorker2 extends Worker {

    Context context=getApplicationContext();
    @NonNull
    @Override
    public Result doWork() {

        try {

            //setting camera config
            CameraConfig mCameraConfig = new CameraConfig()
                    .getBuilder(getApplicationContext())
                    .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
                    .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
                    .setImageFormat(CameraImageFormat.FORMAT_JPEG)
                    .setImageRotation(CameraRotation.ROTATION_270)
                    .build();






            return Result.SUCCESS;


        } catch (Exception e) {


            Log.e("error", e.getMessage(), e);

            return Result.FAILURE;


        }


    }



}
