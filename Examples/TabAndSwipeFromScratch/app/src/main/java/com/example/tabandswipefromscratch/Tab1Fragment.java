package com.example.tabandswipefromscratch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {


    public static final String TAG = "Tab1Fragment";
    protected static final String KEY_TAB1_NUM = "key_tab1_my_num";
    private TextView tv1, tv2, tv3;
    protected int myNum=0;
    private Button button;

    public Tab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(savedInstanceState != null){
            myNum = savedInstanceState.getInt(KEY_TAB1_NUM, 99);
        }
        return inflater.inflate(R.layout.fragment_tab1, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv1 = getActivity().findViewById(R.id.tv_gourmet1);
        tv2 = getActivity().findViewById(R.id.tv_gourmet2);
        tv3 = getActivity().findViewById(R.id.tv_gourmet3);//gain references to views
        button = getActivity().findViewById(R.id.button_tab1_increment);

        tv2.setText("The Num= "+((MainActivity)getActivity()).theNum);
        tv3.setText("Gourmet # "+myNum);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementNum(v);
            }
        });

    }


    public void incrementNum(View view){
        myNum++;
        Log.d(TAG, "incrmented my num= "+myNum);
        tv3.setText("Gourmet # "+myNum);
        ((MainActivity)getActivity()).theNum = myNum;


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_TAB1_NUM, myNum);
    }
}