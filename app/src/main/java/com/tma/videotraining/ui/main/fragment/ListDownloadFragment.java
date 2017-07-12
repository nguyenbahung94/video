package com.tma.videotraining.ui.main.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.videotraining.R;
import com.tma.videotraining.ui.model.NavDrawer.ListVideo;
import com.tma.videotraining.Utils.NetWorkUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by nbhung on 7/7/2017.
 */

public class ListDownloadFragment extends Fragment {
    private View view;
    private Button btnDownload, btnPause, btnCancel;
    private String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private ProgressBar mProgressbar;
    private TextView tvStatus, tvCounFile;
    private boolean statusStop = false;
    private boolean statusCancel = false;
    private DownloadVideo mDownloadVideo;

    public ListDownloadFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_download, container, false);
        btnDownload = (Button) view.findViewById(R.id.btn_download);
        btnPause = (Button) view.findViewById(R.id.btnpause);
        btnCancel = (Button) view.findViewById(R.id.btncancel);
        mProgressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        tvStatus = (TextView) view.findViewById(R.id.statusoffile);
        tvCounFile = (TextView) view.findViewById(R.id.countfile);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetWorkUtils.isNetWorkConnected(getActivity())) {
                    btnCancel.setEnabled(true);
                    btnPause.setEnabled(true);
                    statusStop = false;
                    statusCancel = false;
                    mProgressbar.setProgress(0);
                    btnDownload.setEnabled(false);
                    tvStatus.setText("Downloading...");
                    mDownloadVideo = new DownloadVideo();
                    mDownloadVideo.execute(url);
                } else {
                    Toast.makeText(getActivity(), "Please connect a network", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!statusStop) {
                    tvStatus.setText("Paused");
                    btnPause.setText("Resume");
                    statusStop = true;
                } else {
                    if (NetWorkUtils.isNetWorkConnected(getActivity())) {
                        tvStatus.setText("Downloading...");
                        btnPause.setText("Pause");
                        statusStop = false;
                    } else {
                        Toast.makeText(getActivity(), "Please connect a network", Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvStatus.setText("canceled");
                tvCounFile.setText("0%");
                btnPause.setText("Pause");
                btnPause.setEnabled(false);
                btnCancel.setEnabled(false);
                statusCancel = true;
                btnDownload.setEnabled(true);
                mDownloadVideo.cancel(true);
                mProgressbar.setProgress(0);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "on attach download video", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("onDetach", "download");
    }

    public void swap(ListVideo listVideo, Fragment activity) {
        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", listVideo);
        videoFragment.setArguments(bundle);
        FragmentManager fragmentManager = activity.getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, videoFragment);
        fragmentTransaction.commit();
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.e("inside", "isExternalStorageWritable");
            return true;
        }
        Log.e("outside", "isExternalStorageWritable");
        return false;
    }

    private class DownloadVideo extends AsyncTask<String, String, String> {
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
                isExternalStorageWritable();

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
                    while (statusStop) {
                    }
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
            tvStatus.setText("Done");
            btnPause.setEnabled(false);
            btnCancel.setEnabled(false);

            btnDownload.setEnabled(true);
            if (s.isEmpty()) {
                return;
            }
            Toast.makeText(getActivity(), "Download complete", Toast.LENGTH_LONG).show();
            ListDownloadFragment.this.swap(new ListVideo(s, "Name of Video", "Time of Video"), ListDownloadFragment.this);
        }
    }/* Checks if external storage is available for read and write */

}
