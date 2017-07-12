package com.tma.videotraining.ui.main.fragment;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Asus on 7/13/2017.
 */

public class DownloadVideoRecycle extends AsyncTask<String, String, String> {
    private TextView tvCounFile;
    private ProgressBar mProgressbar;

    public DownloadVideoRecycle(TextView tvCounFile, ProgressBar mProgressbar) {
        this.tvCounFile = tvCounFile;
        this.mProgressbar = mProgressbar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //show before doinbackground run
    }

    @Override
    protected String doInBackground(String... strings) {
        int count = 0;
        String path = "";
        try {
            URL url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.connect();
            int fileLength = urlConnection.getContentLength();

            File file = Environment.getExternalStorageDirectory();
            Log.e("file", file.getAbsolutePath());
//                InputStream input = new BufferedInputStream(url.openStream(), 10 * 1024);

            File xmlFile = new File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/videonew.mp4");
            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
            }
            OutputStream output = new FileOutputStream(xmlFile);
            InputStream input = urlConnection.getInputStream();
            //  File file = Environment.getExternalStorageDirectory();

//                File dir = new File(root.getAbsolutePath() + "/download");
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                File ff = null, ff1 = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//                    File tam1[] = getActivity().getExternalCacheDirs();
//                    ff = tam1[tam1.length - 1];
//                    Log.e("filecaches", ff.getAbsolutePath());
//
//                    File tam2[] = getActivity().getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS);
//                    ff1 = tam2[tam2.length - 1];
//                    Log.e("filedownloads", ff1.getAbsolutePath());
//
//                }
            byte data[] = new byte[2014];
            long total = 0;
            while ((count = input.read(data)) != -1) {
//                while (statusStop) {
//                }
                total += count;
                publishProgress("" + (int) ((total * 100) / fileLength));
                output.write(data, 0, count);
                if (isCancelled()) {
                    break;
                }
            }
//                output.flush();
            output.close();
            input.close();

            path = xmlFile.getAbsolutePath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        tvCounFile.setText(values[0] + "%");
        mProgressbar.setProgress(Integer.parseInt(values[0]));
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}

