package com.astech.movsee.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.astech.movsee.R;
import com.google.android.material.tabs.TabLayout;

public class FavoriteFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        TabLayout tabs = view.findViewById(R.id.tabs);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager(),getContext());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity) getActivity()).getSupportActionBar().setElevation(0);
        }
    }
}