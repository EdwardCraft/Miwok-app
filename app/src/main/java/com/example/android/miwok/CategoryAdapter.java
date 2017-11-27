package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by PEDRO on 22/09/2017.
 */
public class CategoryAdapter extends FragmentPagerAdapter {
    public static final String TAG = CategoryAdapter.class.getSimpleName();

    private Context context;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case Utils.NUMBERS_FRAGMENT: return new NumbersFragment();
            case Utils.COLORS_FRAGMENT: return new ColorsFragment();
            case Utils.FAMILY_FRAGMENT: return new FamilyFragment();
            case Utils.PHRASES_FRAGMENT: return new PhrasesFragment();
            default: break;
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case Utils.NUMBERS_FRAGMENT: return context.getString(R.string.category_numbers);
            case Utils.COLORS_FRAGMENT: return context.getString(R.string.category_colors);
            case Utils.FAMILY_FRAGMENT: return context.getString(R.string.category_family);
            case Utils.PHRASES_FRAGMENT: return context.getString(R.string.category_phrases);
            default: break;
        }

        return "error";


    }

    @Override
    public int getCount() {
        return Utils.NUMBERS_OF_FRAGMENTS;
    }


}
