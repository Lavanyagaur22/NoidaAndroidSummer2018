package com.codingblocks.sample4;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.androidhiddencamera.HiddenCameraFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent      serviceIntent;
    public  DemoCamService mainService;
    ServiceConnection sc;

    private HiddenCameraFragment mHiddenCameraFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn_using_service);
        button.setOnClickListener(this);


//        sc = new ServiceConnection()
//       {
//           @Override
//           public void onServiceConnected(ComponentName className, IBinder service)
//           {
//               mainService = ((DemoCamService.MainServiceBinder)service).getService();
//               Log.v("xxx", "[MainActivity]: onServiceConnected()");
//           }
//
//           @Override
//           public void onServiceDisconnected(ComponentName arg0)
//           {
//               mainService = null;
//               Log.v("xxx", "[MainActivity]: onServiceDisconnected()");
//           }
//       };
//
//
    }

    @Override
    public void onClick(View view) {
        if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mHiddenCameraFragment)
                    .commit();
            mHiddenCameraFragment = null;
        }

        Intent intent=new Intent(getBaseContext(),DemoCamService.class);
//        bindService(intent, sc,0);
        startService(intent);
    }


    @Override
    public void onBackPressed() {
        if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mHiddenCameraFragment)
                    .commit();
            mHiddenCameraFragment = null;
        }else { //Kill the activity
            super.onBackPressed();
        }
    }

    public void onClick1(View view) {

        Log.e("TAG", "onClick1: ----------------------__--__-" );
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        final Cursor cursor = getBaseContext().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

// Put it in the image view
        if (cursor.moveToFirst()) {
            final ImageView imageView =  findViewById(R.id.image);
            String imageLocation = cursor.getString(1);
            File imageFile = new File(imageLocation);
            if (imageFile.exists()) {   // TODO: is there a better way to do this?
                Bitmap bm = BitmapFactory.decodeFile(imageLocation);
                imageView.setImageBitmap(bm);
            }
        }

    }
}
