package com.tma.videotraining.ui.main.adapter;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Asus on 7/12/2017.
 */

public class DownloadVideo extends AsyncTask<String, String, String> {
    private ProgressBar mProgressBar;
    private TextView tvCountfile;
    private Button btnDown, btnPause, btnResume;

    public DownloadVideo(ProgressBar mProgressBar, TextView tvCountfile, Button btnDown, Button btnPause, Button btnResume) {
        this.mProgressBar = mProgressBar;
        this.tvCountfile = tvCountfile;
        this.btnDown = btnDown;
        this.btnPause = btnPause;
        this.btnResume = btnResume;
    }

    @Override
    protected String doInBackground(String... params) {
        return null;
    }
}
