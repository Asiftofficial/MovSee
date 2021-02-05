package com.astech.movsee.ui.favorite.tv;

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
import com.astech.movsee.data.source.local.entity.TvDetailEntity;
import com.astech.movsee.ui.detail.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class FavoriteTvAdapter extends PagedListAdapter<TvDetailEntity, FavoriteTvAdapter.FavoriteTvViewHolder> {

    public FavoriteTvAdapter(){
        super(DIFF_CALLBACK);
    }

    private static DiffUtil.ItemCallback<TvDetailEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TvDetailEntity>() {
                @Override
                public boolean areItemsTheSame(@NonNull TvDetailEntity oldItem, @NonNull TvDetailEntity newItem) {
                    return oldItem.getCatalogId().equals(newItem.getCatalogId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull TvDetailEntity oldItem, @NonNull TvDetailEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };


    @NonNull
    @Override
    public FavoriteTvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item,parent,false);
        return new FavoriteTvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvViewHolder holder, int position) {
        TvDetailEntity data = getItem(position);
        if (data != null){
            holder.bind(data);
        }
    }

    public static class FavoriteTvViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvScore;

        public FavoriteTvViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvScore = itemView.findViewById(R.id.score_value);
        }

        public void bind(TvDetailEntity data) {

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
                intent.putExtra(DetailActivity.EXTRA_IDENTITY,"tv");
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
