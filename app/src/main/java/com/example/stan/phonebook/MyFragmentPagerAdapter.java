package com.example.stan.phonebook;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;


/**
 * Created by Stan on 9/15/2016.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    private Context context;
    private FragmentManager sfm;

    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        //this.sfm = fm;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return FriendFragment.newInstance("", "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return ContactFragment.newInstance("", "Page # 2");
            case 2: // Fragment # 0 - This will show FirstFragment different title
                return ContactFragment.newInstance("", "Page # 2");
            case 3: // Fragment # 0 - This will show FirstFragment different title
                return ContactFragment.newInstance("", "Page # 2");
            default:
                return null;
        }
//        Bundle args = new Bundle();
////        args.putInt(InfoFragment.ARG_POSITION, position);
////        newFragment.setArguments(args);
//        FragmentTransaction transaction = sfm.beginTransaction();
//
//        // Replace current fragment in the fragment_container view (ContactFragment) with this fragment (InfoFragment),
//        // and add the transaction to the back stack
//        transaction.setCustomAnimations(android.R.anim.slide_in_left,
//                android.R.anim.slide_out_right);
//        transaction.replace(R.id.fragment_container, newFragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction (bring up the new view)
//        transaction.commit();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}