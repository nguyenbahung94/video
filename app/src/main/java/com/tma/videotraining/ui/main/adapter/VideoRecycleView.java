package com.tma.videotraining.ui.main.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tma.videotraining.R;
import com.tma.videotraining.ui.main.fragment.DownloadVideoRecycle;
import com.tma.videotraining.ui.model.NavDrawer.ListVideo;
import com.tma.videotraining.ui.view.interDrawer.ClickListener;

import java.util.List;

/**
 * Created by Asus on 7/12/2017.
 */

public class VideoRecycleView extends RecyclerView.Adapter<VideoRecycleView.VideoViewHolder> {
    private List<ListVideo> mListVideos;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ClickListener clickListener;

    public VideoRecycleView(List<ListVideo> mListVideos, Context mContext, ClickListener clickListener) {
        this.mListVideos = mListVideos;
        this.mContext = mContext;
        mLayoutInflater = LayoutInflater.from(mContext);
        this.clickListener = clickListener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mLayoutInflater.inflate(R.layout.list_video_row, parent, false);
        final VideoViewHolder viewHolder = new VideoViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, viewHolder.getLayoutPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        ListVideo ss = mListVideos.get(position);
        holder.mImageView.setImageResource(R.drawable.icon);
        holder.tvName.setText(ss.getName());
        holder.tvTime.setText(ss.getTime());
    }

    @Override
    public int getItemCount() {
        return mListVideos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageView;
        private TextView tvName, tvTime, tvCounfile;
        private Button btnDown, btnPause, btnResume, btnCancel;
        private ProgressBar mProgressBar;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image);
            tvName = (TextView) itemView.findViewById(R.id.name_ofvideo);
            tvTime = (TextView) itemView.findViewById(R.id.time_ofvideo);
            btnDown = (Button) itemView.findViewById(R.id.btn_downVideo);
            btnPause = (Button) itemView.findViewById(R.id.btn_PauseVideo);
            btnResume = (Button) itemView.findViewById(R.id.btn_ResumeVideo);
            btnCancel = (Button) itemView.findViewById(R.id.btn_CancelVideo);
            tvCounfile = (TextView) itemView.findViewById(R.id.tvcountfile);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.mprogessbar);
            btnResume.setOnClickListener(this);
            btnPause.setOnClickListener(this);
            btnDown.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_downVideo:
                    btnDown.setText("pausw");
                    DownloadVideoRecycle sss = new DownloadVideoRecycle(tvCounfile, mProgressBar);
                    sss.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,mListVideos.get(getLayoutPosition()).getUrl());
                    Log.e("possition", String.valueOf(getLayoutPosition()));
                    break;
                case R.id.btn_CancelVideo:
                    btnCancel.setText("download");
                    break;
            }

        }
    }


}
