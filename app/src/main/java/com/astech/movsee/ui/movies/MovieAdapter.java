package com.astech.movsee.ui.movies;

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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private MovieCallback movieCallback;

    public void setMovieCallback(MovieCallback movieCallback) {
        this.movieCallback = movieCallback;
    }

    private ArrayList<CatalogResponse> listMovie = new ArrayList<>();

    public void setListMovie(List<CatalogResponse> listMovie) {
        if (listMovie == null || listMovie.size() == 0) return;
        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        CatalogResponse model = listMovie.get(position);

        if (position == listMovie.size()-10){
            movieCallback.pageCallback();
        }

        if(model.getPoster().isEmpty()){
            holder.imgPoster.setImageResource(R.color.background);
        }else {
            String url = "https://image.tmdb.org/t/p/w342" + model.getPoster();

            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .apply(RequestOptions.placeholderOf(R.color.background))
                    .into(holder.imgPoster);
        }


        holder.tvScore.setText(model.getScore());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_ID,model.getCatalogId());
            intent.putExtra(DetailActivity.EXTRA_IDENTITY,"movie");
            holder.itemView.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvScore;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvScore = itemView.findViewById(R.id.score_value);
        }

    }

    public interface MovieCallback{
        void pageCallback();
    }

}
