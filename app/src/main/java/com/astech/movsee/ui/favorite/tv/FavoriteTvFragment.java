package com.astech.movsee.ui.favorite.tv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.viewmodel.ViewModelFactory;

public class FavoriteTvFragment extends Fragment {
    private RecyclerView rvTvFavorite;
    private ProgressBar progressBar;
    private FavoriteTvAdapter adapter;
    private TextView tvEmptyData;


    public FavoriteTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        rvTvFavorite = view.findViewById(R.id.rv_favorite_tv);
        progressBar = view.findViewById(R.id.progress_bar);
        tvEmptyData = view.findViewById(R.id.empty_data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null){
            ViewModelFactory factory = ViewModelFactory.getInstance(getActivity());
            FavoriteTvViewModel viewModel = new ViewModelProvider(this,factory).get(FavoriteTvViewModel.class);

            adapter = new FavoriteTvAdapter();
            progressBar.setVisibility(View.VISIBLE);
            viewModel.getFavoriteTvs().observe(getViewLifecycleOwner(), tvEntities -> {
                rvTvFavorite.setLayoutManager(new GridLayoutManager(getContext(),3));
                rvTvFavorite.setHasFixedSize(true);
                rvTvFavorite.setAdapter(adapter);
                if (tvEntities != null){
                    progressBar.setVisibility(View.GONE);
                    if (tvEntities.size() == 0){
                        tvEmptyData.setVisibility(View.VISIBLE);
                    }
                    adapter.submitList(tvEntities);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}