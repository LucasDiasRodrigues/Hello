package com.apps.lucas.hello.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.apps.lucas.hello.Fragments.LoginSlideViewPagerFragment;

import java.util.ArrayList;

/**
 * Created by Lucas on 26/12/2016.
 */

public class LoginSlidesFragmentPagerAdapter extends FragmentPagerAdapter {


    private ArrayList<String> txts;
    private Context context;


    public LoginSlidesFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<String> txts) {
        super(fm);
        this.txts = txts;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        LoginSlideViewPagerFragment fragment = LoginSlideViewPagerFragment.novaInstancia(txts.get(position));


        return fragment;
    }

    @Override
    public int getCount() {
        return txts.size();
    }
}
