package com.tma.videotraining.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.tma.videotraining.R;
import com.tma.videotraining.ui.model.NavDrawer.ListVideo;

import java.util.ArrayList;

/**
 * Created by nbhung on 7/7/2017.
 */

public class adapterListVideo extends BaseAdapter {
    private Context context;
    private ArrayList<ListVideo> mlist;

    public adapterListVideo(Context context, ArrayList<ListVideo> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = null;
        if (itemView == null) {
            LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflator.inflate(R.layout.list_video_row, viewGroup, false);
        }
        ListVideo listtam = (ListVideo) mlist.get(i);
        final Button btnDown = (Button) itemView.findViewById(R.id.btn_downVideo);
        final Button btnCancel = (Button) itemView.findViewById(R.id.btn_CancelVideo);
        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDown.setText("Pause");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnCancel.setText("cancel");
                    }
                });
            }
        });


        return itemView;
    }


}
