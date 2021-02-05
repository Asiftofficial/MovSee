package com.astech.movsee.ui.favorite.movies;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.data.source.local.entity.MovieDetailEntity;
import com.astech.movsee.ui.detail.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteMovieAdapter extends PagedListAdapter<MovieDetailEntity, FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    public FavoriteMovieAdapter(){
        super(DIFF_CALLBACK);
    }

    private static DiffUtil.ItemCallback<MovieDetailEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieDetailEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieDetailEntity oldItem, @NonNull MovieDetailEntity newItem) {
                    return oldItem.getCatalogId().equals(newItem.getCatalogId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull MovieDetailEntity oldItem, @NonNull MovieDetailEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item,parent,false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, int position) {
        MovieDetailEntity data = getItem(position);
        if (data != null){
            holder.bind(data);
        }
    }

    public static class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvScore;

        public FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvScore = itemView.findViewById(R.id.score_value);
        }

        public void bind(MovieDetailEntity data) {

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
                intent.putExtra(DetailActivity.EXTRA_FAVORITE,data);
                intent.putExtra(DetailActivity.EXTRA_IDENTITY,"movie");
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
