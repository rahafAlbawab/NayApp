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


public class CareFragment extends Fragment {
    LinearLayout container_fragment;
    HairCareFragment hairCareFragment;
    SkinCareFragment skinCareFragment;
    Button Hair_button, Skin_hutton;


    public CareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_care, container, false);
        Hair_button = view.findViewById(R.id.Hair_Care_button);
        Skin_hutton = view.findViewById(R.id.Skin_Care_button);
        container_fragment = view.findViewById(R.id.container_fragment);
        hairCareFragment = new HairCareFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragment, hairCareFragment ); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

        Hair_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hairCareFragment = new HairCareFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_fragment, hairCareFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });
        Skin_hutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skinCareFragment = new SkinCareFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_fragment, skinCareFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });

        return view;
    }
}