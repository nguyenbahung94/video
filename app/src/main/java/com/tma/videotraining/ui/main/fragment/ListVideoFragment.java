package com.tma.videotraining.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tma.videotraining.R;
import com.tma.videotraining.di.component.DaggerFragmentComponent;
import com.tma.videotraining.di.module.FragmentModule;
import com.tma.videotraining.ui.interactor.ListVideoFragmentInteractor;
import com.tma.videotraining.ui.main.adapter.VideoRecycleView;
import com.tma.videotraining.ui.model.NavDrawer.ListVideo;
import com.tma.videotraining.ui.view.ViewFragment;
import com.tma.videotraining.ui.view.interDrawer.ClickListener;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by nbhung on 7/7/2017.
 */

public class ListVideoFragment extends Fragment implements ViewFragment.ViewListVideoFragment {
    @Inject
    ListVideoFragmentInteractor listVideoFragmentInteractor;
    private ArrayList<ListVideo> mList;
    private RecyclerView mRecyclerView;
    private int TYPE_CAROUSEL = 100000000;

    public ListVideoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addListVideo();
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).build().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listvideo, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_listvideo);
        VideoRecycleView adapterVide0 = new VideoRecycleView(mList, getContext(), new ClickListener() {
            @Override
            public void onClick(View view, int positon) {
                showDetail(mList.get(positon));
                // Toast.makeText(getActivity(), mList.get(positon).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int posotion) {

            }
        });
        mRecyclerView.setAdapter(adapterVide0);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemViewCacheSize(100);
        adapterVide0.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "on attach listvideo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("onDetach", "listvideo");
    }

    public void addListVideo() {
        mList = new ArrayList<>();
        mList.add(new ListVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://techslides.com/demos/sample-videos/small.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://techslides.com/demos/sample-videos/small.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));
        mList.add(new ListVideo("http://www.html5videoplayer.net/videos/toystory.mp4", "Name of Video", "Time of Video"));

    }

    @Override
    public void showDetail(final ListVideo listVideo) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        View mview = getActivity().getLayoutInflater().inflate(R.layout.dialog_detail, null);
        final ImageView mImageView = (ImageView) mview.findViewById(R.id.img_ofdialog);
        mImageView.setImageResource(R.drawable.icon);
        final TextView mtvname = (TextView) mview.findViewById(R.id.tvname_ofdialog);
        mtvname.setText(listVideo.getName());
        final TextView mtvtime = (TextView) mview.findViewById(R.id.tvtime_ofdialog);
        mtvtime.setText(listVideo.getTime());
        final Button mbutton = (Button) mview.findViewById(R.id.btn_ofdialog);
        mBuilder.setView(mview);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listVideoFragmentInteractor.swap(listVideo, ListVideoFragment.this);

            }
        });
    }
}
