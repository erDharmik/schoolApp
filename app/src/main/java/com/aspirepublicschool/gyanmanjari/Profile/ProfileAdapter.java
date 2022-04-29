package com.aspirepublicschool.gyanmanjari.Profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.aspirepublicschool.gyanmanjari.NewRegister.TuitionDetailActivity;
import com.aspirepublicschool.gyanmanjari.Profile.Fragment.AddressFragment;
import com.aspirepublicschool.gyanmanjari.Profile.Fragment.FacultyDetails;
import com.aspirepublicschool.gyanmanjari.Profile.Fragment.PaymentDetails;
import com.aspirepublicschool.gyanmanjari.Profile.Fragment.PersonalDetails;
import com.aspirepublicschool.gyanmanjari.Profile.Fragment.SecurityDetails;
import com.aspirepublicschool.gyanmanjari.Profile.Fragment.TutionDetailsFragment;

public class ProfileAdapter extends FragmentPagerAdapter {
    private int totleTabs;
    Context ctx;
    String number, stu_id, sc_id;

    public ProfileAdapter(@NonNull FragmentManager fm, int totleTabs, String stu_id, String sc_id, String number) {
        super(fm);
        this.totleTabs = totleTabs;
        this.stu_id = stu_id;
        this.sc_id = sc_id;
        this.number = number;
    }

/*  public HomeWorkDetailsAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }
*/



    @Override
    public Fragment getItem(int i) {

        Bundle bundle = new Bundle();
        bundle.putString("stu_id", stu_id);
        bundle.putString("sc_id", sc_id);
        bundle.putString("number", number);

        switch (i) {
            case 0:
                PersonalDetails personalDetails = new PersonalDetails();
                personalDetails.setArguments(bundle);
                return  personalDetails ;
            case 1:
                AddressFragment addressFragment = new AddressFragment();
                addressFragment.setArguments(bundle);
                return addressFragment;
            case 2:
                TutionDetailsFragment tutionDetailsFragment = new TutionDetailsFragment();
                tutionDetailsFragment.setArguments(bundle);
                return tutionDetailsFragment;
            case 3:
                PaymentDetails paymentDetails = new PaymentDetails();
                paymentDetails.setArguments(bundle);
                return paymentDetails;
//            case 3:
//                FacultyDetails facultyDetails = new FacultyDetails();
//                return facultyDetails;
//            case 3:
//                SecurityDetails securityDetails = new SecurityDetails();
//                return securityDetails;
//            case 4:
//                PaymentDetails paymentDetails = new PaymentDetails();
//                return paymentDetails;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totleTabs;
    }
}
