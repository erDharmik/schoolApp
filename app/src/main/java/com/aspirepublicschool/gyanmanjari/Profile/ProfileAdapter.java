package com.aspirepublicschool.gyanmanjari.Profile;

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

    public ProfileAdapter(@NonNull FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }

/*  public HomeWorkDetailsAdapter(FragmentManager fm, int totleTabs) {
        super(fm);
        this.totleTabs = totleTabs;
    }
*/

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                PersonalDetails personalDetails = new PersonalDetails();
                return  personalDetails ;
            case 1:
                AddressFragment addressFragment = new AddressFragment();
                return addressFragment;
            case 2:
                TutionDetailsFragment tutionDetailsFragment = new TutionDetailsFragment();
                return tutionDetailsFragment;
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
