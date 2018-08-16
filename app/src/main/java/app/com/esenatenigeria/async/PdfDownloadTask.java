package app.com.esenatenigeria.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import app.com.esenatenigeria.interfaces.PdfDownloadInterface;

/**
 * Created by dev on 26/4/18.
 */

public class PdfDownloadTask {

    private static final String TAG = "Download Task";
    private static PdfDownloadInterface mDownloaded;
    public DownloadingTask downloadingTask;
    private Context context;
    private String downloadUrl = "", downloadFileName = "";
//    private ProgressDialog progressDialog;

    public PdfDownloadTask(Context context, String downloadUrl) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        try {
            downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'), downloadUrl.length());//Create file name by picking download file name from URL
        } catch (Exception e) {
            downloadFileName = String.valueOf(Calendar.getInstance().getTime());
        }
        Log.e(TAG, downloadFileName);

        //Start Downloading Task
        downloadingTask = new DownloadingTask();
        downloadingTask.execute();
    }

    public static void initDownloadInterface(PdfDownloadInterface anInterface) {
        mDownloaded = anInterface;
    }

    public class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ;
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
//                    progressDialog.dismiss();
                    mDownloaded.onDownloaded(outputFile, true);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 3000);
                    mDownloaded.onDownloaded(outputFile, false);
                    Log.e(TAG, "Loading Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                mDownloaded.onDownloaded(outputFile, false);
//                progressDialog.dismiss();
                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e(TAG, "Loading Failed with Exception - " + e.getLocalizedMessage());

            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());
                }
                //Get File if SD card is present
                apkStorage = new File(Environment.getExternalStorageDirectory(),
                        "NASS");

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[4096];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                fos.close();
                is.close();

            } catch (Exception e) {
                Log.e(TAG, "Loading Error Exception " + e.getMessage());
                e.printStackTrace();
                outputFile = null;
            }
            return null;
        }
    }
}
