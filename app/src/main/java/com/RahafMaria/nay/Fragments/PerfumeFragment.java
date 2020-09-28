package com.RahafMaria.nay.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.RahafMaria.nay.R;


public class PerfumeFragment extends Fragment {
    LinearLayout container_perfume_fragment;
    ChanelFragment chanelFragment;
    DiorFragment diorFragment;
    ArmaniFragment armaniFragment;
    Button dior_button, chanel_button,armani_button;



    public PerfumeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfume, container, false);
        dior_button = view.findViewById(R.id.dior_button);
        chanel_button = view.findViewById(R.id.chanel_button);
        armani_button = view.findViewById(R.id.armani_button);
        container_perfume_fragment = view.findViewById(R.id.container_perfume_fragment);
        chanelFragment = new ChanelFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_perfume_fragment, chanelFragment ); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

       dior_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diorFragment = new DiorFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_perfume_fragment, diorFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });

        chanel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chanelFragment = new ChanelFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_perfume_fragment, chanelFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });
        armani_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                armaniFragment = new ArmaniFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_perfume_fragment, armaniFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });

        return view;
    }
}