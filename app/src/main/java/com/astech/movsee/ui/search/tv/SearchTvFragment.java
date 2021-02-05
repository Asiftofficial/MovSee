package com.astech.movsee.ui.search.tv;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.viewmodel.ViewModelFactory;

public class SearchTvFragment extends Fragment {
    private RecyclerView rvTv;
    private SearchTvAdapter adapter;
    private ProgressBar pbTv;
    private SearchView searchView;
    private SearchTvViewModel viewModel;
    private TextView tvCheckInternet;
    private int page = 1;


    public SearchTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

        ViewModelFactory factory = ViewModelFactory.getInstance(getContext());
        viewModel = new ViewModelProvider(this,factory).get(SearchTvViewModel.class);
        rvTv = view.findViewById(R.id.rv_search_tv);
        rvTv.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvTv.setHasFixedSize(true);
        adapter = new SearchTvAdapter();
        rvTv.setAdapter(adapter);
        pbTv = view.findViewById(R.id.progress_bar);
        tvCheckInternet = view.findViewById(R.id.check_internet);

        if (isOnline()){
            SearchManager sm = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
            if (sm != null){
                searchView = view.findViewById(R.id.searchView_tv);
                searchView.setSearchableInfo(sm.getSearchableInfo(getActivity().getComponentName()));
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        page = 1;
                        adapter.clearTv();
                        if (!newText.isEmpty()){
                            pbTv.setVisibility(View.VISIBLE);
                            rvTv.setVisibility(View.VISIBLE);
                            viewModel.getSearchTvs(newText,String.valueOf(page)).observe(getViewLifecycleOwner(), data -> {
                                if (data != null){
                                    switch (data.status){
                                        case SUCCESS:
                                            pbTv.setVisibility(View.GONE);
                                            if (data.data != null){
                                                adapter.setListTvs(data.data);
                                            }
                                            break;
                                        case ERROR:
                                            pbTv.setVisibility(View.GONE);
                                            Toast.makeText(getContext(),data.msg,Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            });

                            adapter.setSearchTvCallback(new SearchTvAdapter.SearchTvCallback() {
                                @Override
                                public void pageCallback() {
                                    page++;
                                    viewModel.getSearchTvs(newText,String.valueOf(page)).observe(getViewLifecycleOwner(), data -> {
                                        if (data != null){
                                            switch (data.status){
                                                case SUCCESS:
                                                    pbTv.setVisibility(View.GONE);
                                                    if (data.data != null){
                                                        adapter.setListTvs(data.data);
                                                    }
                                                    break;
                                                case ERROR:
                                                    pbTv.setVisibility(View.GONE);
                                                    Toast.makeText(getContext(),data.msg,Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }
                                    });
                                }
                            });
                        }else {
                            rvTv.setVisibility(View.GONE);
                        }
                        return false;
                    }
                });
            }
        }else {
            rvTv.setVisibility(View.GONE);
            tvCheckInternet.setVisibility(View.VISIBLE);
            page = 1;
            adapter.clearTv();
        }

    }

    private boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null){
            return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
        }
        return false;
    }
}