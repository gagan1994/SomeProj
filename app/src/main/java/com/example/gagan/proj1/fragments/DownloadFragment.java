package com.example.gagan.proj1.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gagan.proj1.R;
import com.example.gagan.proj1.download.DownloadTask;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends BaseFragment {


    public DownloadFragment() {
    }

    private static String file_url = "https://api.androidhive.info/progressdialog/hive.jpg";
    private static String magnet = "magnet:?xt=urn:btih:844E6622A41DD75C105099CD8B56CF942BA19939&dn=Game.of.Thrones.S07E02.WEB-DL.x264-RARBG&tr=udp%3A%2F%2Ftracker.openbittorrent.com%3A80&tr=udp%3A%2F%2Ftracker.publicbt.com%3A80&tr=udp%3A%2F%2Ftracker.istole.it%3A6969&tr=udp%3A%2F%2Fopen.demonii.com%3A1337";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_download, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnDownload)
    public void onClickDownload() {
        new DownloadTask(getActivity()).execute(file_url);
    }

    @Override
    public String getThisTag() {
        return "DownloadFragment";
    }

    @Override
    public String getTitle() {
        return "Downloads";
    }
}
