package com.astech.movsee.ui.search.movies;

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

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchMovieViewHolder> {
    private SearchMoviesCallback searchMoviesCallback;

    public void setSearchMoviesCallback(SearchMoviesCallback searchMoviesCallback) {
        this.searchMoviesCallback = searchMoviesCallback;
    }

    private ArrayList<CatalogResponse> listMovies = new ArrayList<>();

    public void setListMovies(List<CatalogResponse> listMovies) {
        if (listMovies == null || listMovies.size() == 0) return;
        this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    public void clearMovies(){
        listMovies.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item,parent,false);
        return new SearchMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieViewHolder holder, int position) {
        CatalogResponse data = listMovies.get(position);

        if (position == listMovies.size()-1){
            searchMoviesCallback.pageCallback();
        }

        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class SearchMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvScore;

        public SearchMovieViewHolder(@NonNull View itemView) {
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
                intent.putExtra(DetailActivity.EXTRA_IDENTITY,"movie");
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public interface SearchMoviesCallback{
        void pageCallback();
    }
}
