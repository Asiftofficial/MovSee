package com.astech.movsee.ui.favorite;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.astech.movsee.R;
import com.astech.movsee.ui.favorite.movies.FavoriteMoviesFragment;
import com.astech.movsee.ui.favorite.tv.FavoriteTvFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    public SectionPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    int[] TAB_TITLE = new int[]{
            R.string.tab1,
            R.string.tab2
    };

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new FavoriteMoviesFragment();
        }else {
            return new FavoriteTvFragment();
        }
    }

    @Override
    public int getCount() {
        return TAB_TITLE.length;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return context.getResources().getString(TAB_TITLE[position]);
    }
}
