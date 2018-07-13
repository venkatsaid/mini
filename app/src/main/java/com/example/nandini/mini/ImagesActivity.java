package com.example.nandini.mini;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.zip.Inflater;

public class ImagesActivity extends AppCompatActivity
{
    public static final int PICK_IMAGE = 1;
    private int count;
    private Bitmap[] thumbnails;
    private boolean[] thumbnailsselection;
    private String[] arrPath;
    private ImageAdapter imageAdapter;
    ArrayList<String> f = new ArrayList<String>();// list of file paths
    File[] listFile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        GridView imagegrid = (GridView) findViewById(R.id.PhoneImageGrid);
        imageAdapter = new ImageAdapter();
        imagegrid.setAdapter(imageAdapter);
        getFromSdcard();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundColor(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(ImagesActivity.this);

                alertDialogBuilder.setTitle("Add Image");
                alertDialogBuilder.setMessage("Please take Photo");

                alertDialogBuilder.setPositiveButton("Camera", null);
                alertDialogBuilder.setNegativeButton("Gallery", null);

                alertDialogBuilder.setNegativeButton("Gallery", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        try
                        {
                            int random = (int) (Math.random() * 50 + 1);

                            File roo1 = new File(Environment.getExternalStorageDirectory() + "/photo_hider/");
                            if (!roo1.exists()) {
                                roo1.mkdirs();
                            }

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                            String timeStamp = dateFormat.format(new Date());
                            String imageFileName = "picture_" + timeStamp + ".jpg";

                            Log.v("", "imagefilename : " +imageFileName);
                            File sdImageMainDirectory1 = new File(roo1, imageFileName);
                            sdImageMainDirectory1.renameTo(sdImageMainDirectory1);
                            Uri outputFileUri1 = Uri.fromFile(sdImageMainDirectory1);
                            Log.v("", "outputFileUri1" + outputFileUri1);
//                            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            intent1.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri1);
//                            startActivityForResult(intent1, 111);
                            Intent client = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            client.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri1);
                            startActivityForResult(client, 115);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });


                alertDialogBuilder.setPositiveButton("Camera", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        try
                        {
                            int random = (int) (Math.random() * 50 + 1);

                            File roo1 = new File(Environment.getExternalStorageDirectory() + "/photo_hider/");
                            if (!roo1.exists()) {
                                roo1.mkdirs();
                            }

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                            String timeStamp = dateFormat.format(new Date());
                            String imageFileName = "picture_" + timeStamp + ".jpg";


                            Log.v("", "clientimagefilename" +imageFileName);
                            File sdImageMainDirectory1 = new File(roo1, imageFileName);
                            sdImageMainDirectory1.renameTo(sdImageMainDirectory1);
                            Uri outputFileUri1 = Uri.fromFile(sdImageMainDirectory1);
                            Log.v("", "outputFileUri1" + outputFileUri1);
                            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent1.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri1);
                            startActivityForResult(intent1, 115);

                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                alertDialogBuilder.show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==115&& resultCode==RESULT_OK){
            getFromSdcard();
        }
    }

    public void getFromSdcard()
    {

        File file= new File(android.os.Environment.getExternalStorageDirectory(),"/photo_hider/");
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.v("","ImageName"+file);

        if (file.isDirectory())
        {

            listFile = file.listFiles();

            for (int i = 0; i < listFile.length; i++)
            {

                f.add(listFile[i].getAbsolutePath());

            }

        }
    }




    public class ImageAdapter extends BaseAdapter
    {

        private LayoutInflater mInflater;

        public ImageAdapter()
        {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return f.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;
            if (convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.galleryitem, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.thumbImage);

                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }


            Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
            holder.imageview.setImageBitmap(myBitmap);
            return convertView;
        }
    }



    class ViewHolder
    {
        ImageView imageview;
    }



    int doubleBackToExitPressed = 1;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            System.exit(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this, "Please press Back again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressed=1;
            }
        }, 2000);
    }

}