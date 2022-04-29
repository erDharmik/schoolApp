package com.aspirepublicschool.gyanmanjari.Profile.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aspirepublicschool.gyanmanjari.R;

public class PaymentDetails extends Fragment {

String stu_id, sc_id, number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_details, container, false);

        stu_id = getArguments().getString("stu_id");
        sc_id = getArguments().getString("sc_id");
        number = getArguments().getString("number");

        return view;
    }
}