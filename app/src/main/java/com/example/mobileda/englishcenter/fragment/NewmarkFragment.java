package com.example.mobileda.englishcenter.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileda.englishcenter.R;

import butterknife.ButterKnife;

public class NewmarkFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "debug";

    public NewmarkFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newmark, container, false);
        ButterKnife.bind(this, rootView);




        return rootView;
    }


}