package com.example.gagan.proj1.download;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.gagan.proj1.MyApplication;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Gagan on 4/19/2018.
 */

public class DownloadTask extends AsyncTask<String, Integer, Void> {
    private NotificationHelper mNotificationHelper;

    private static final String PEFERENCE_FILE = "preference";
    private static final String ISDOWNLOADED = "isdownloaded";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    Context context;
    public DownloadTask(Context context){
        this.context = context;
        mNotificationHelper = new NotificationHelper(context);
    }

    protected void onPreExecute(){
        //Create the notification in the statusbar
        mNotificationHelper.createNotification();
    }

    @Override
    protected Void doInBackground(String... urls) {
        //This is where we would do the actual download stuff
        //for now I'm just going to loop for 10 seconds
        // publishing progress every second

        int count;

        try {


            URL url = new URL(urls[0]);
            URLConnection connexion = url.openConnection();
            connexion.connect();

            int lenghtOfFile = connexion.getContentLength();
            Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
            String root = Environment.getExternalStorageDirectory().toString()+ "/share";

            File myDir = new File(root );
            myDir.createNewFile();
            InputStream input = new BufferedInputStream(url.openStream());
            //OutputStream output = new FileOutputStream("/sdcard/foldername/temp.zip");
            OutputStream output = new FileOutputStream(root+"download.jpg");
            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                //publishProgress(""+(int)((total*100)/lenghtOfFile));
                Log.d("%Percentage%",""+(int)((total*100)/lenghtOfFile));
                onProgressUpdate((int)((total*100)/lenghtOfFile));
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
//            File file = new File(Environment.getExternalStorageDirectory()
//                    + "/foldername/"+"_images.zip");
//            File path = new File(Environment.getExternalStorageDirectory()
//                    + "/foldername");
//            try {
//                ZipUtil.unzip(file,path);
//                settings = this.context.getSharedPreferences(PEFERENCE_FILE, 0);
//                editor = settings.edit();
//                editor.putBoolean(ISDOWNLOADED, true);
//                editor.commit();
//
//            } catch (IOException e) {
//                Log.d("ZIP UTILL",e.toString());
//                e.printStackTrace();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
    protected void onProgressUpdate(Integer... progress) {
        //This method runs on the UI thread, it receives progress updates
        //from the background thread and publishes them to the status bar
        mNotificationHelper.progressUpdate(progress[0]);
    }
    protected void onPostExecute(Void result)    {
        //The task is complete, tell the status bar about it
//        MyApplication.serviceState=false;
        mNotificationHelper.completed();
    }
}
