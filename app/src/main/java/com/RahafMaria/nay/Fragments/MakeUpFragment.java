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


public class MakeUpFragment extends Fragment {

    LinearLayout container_makeup_fragment;
    EyesFragment eyesFragment;
    FaceFragment faceFragment;
    LipsFragment lipsFragment;
    NailsFragment nailsFragment;
    Button eyes_button, face_button,lips_button,nail_button;
    public MakeUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_up, container, false);
        eyes_button = view.findViewById(R.id.eyes_button);
        face_button = view.findViewById(R.id.face_button);
        lips_button = view.findViewById(R.id.lips_button);
        nail_button = view.findViewById(R.id.nail_button);
        container_makeup_fragment = view.findViewById(R.id.container_makeup_fragment);
        eyesFragment = new EyesFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_makeup_fragment, eyesFragment ); // give your fragment container id in first parameter
        transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
        transaction.commit();

        eyes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eyesFragment = new EyesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_makeup_fragment, eyesFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });

        face_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceFragment = new FaceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_makeup_fragment, faceFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });
        lips_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lipsFragment = new LipsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_makeup_fragment, lipsFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });
        nail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nailsFragment = new NailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_makeup_fragment, nailsFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();

            }
        });
        return  view;
    }
}