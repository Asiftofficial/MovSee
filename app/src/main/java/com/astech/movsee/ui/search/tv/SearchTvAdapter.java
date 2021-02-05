package com.astech.movsee.ui.search.tv;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.data.source.remote.response.CatalogResponse;
import com.astech.movsee.ui.detail.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchTvAdapter extends RecyclerView.Adapter<SearchTvAdapter.SearchTvViewHolder> {
    private SearchTvCallback searchTvCallback;

    public void setSearchTvCallback(SearchTvCallback searchTvCallback) {
        this.searchTvCallback = searchTvCallback;
    }

    private ArrayList<CatalogResponse> listTvs = new ArrayList<>();

    public void setListTvs(List<CatalogResponse> listTvs) {
        if (listTvs == null || listTvs.size() == 0) return;
        this.listTvs.addAll(listTvs);
        notifyDataSetChanged();
    }

    public void clearTv(){
        listTvs.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item,parent,false);
        return new SearchTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTvViewHolder holder, int position) {
        CatalogResponse data = listTvs.get(position);
        if (position == listTvs.size()-1){
            searchTvCallback.pageCallback();
        }
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return listTvs.size();
    }

    public class SearchTvViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvScore;

        public SearchTvViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvScore = itemView.findViewById(R.id.score_value);
        }

        public void bind(CatalogResponse data) {
            if(data.getPoster().isEmpty()){
                imgPoster.setImageResource(R.color.background);
            }else {
                String url = "https://image.tmdb.org/t/p/w342" + data.getPoster();

                Glide.with(itemView.getContext())
                        .load(url)
                        .apply(RequestOptions.placeholderOf(R.color.background))
                        .into(imgPoster);
            }

            tvScore.setText(data.getScore());

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID,data.getCatalogId());
                intent.putExtra(DetailActivity.EXTRA_IDENTITY,"tv");
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public interface SearchTvCallback{
        void pageCallback();
    }
}
