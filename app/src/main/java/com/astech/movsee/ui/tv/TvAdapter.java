package com.astech.movsee.ui.tv;

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

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private TvCallback tvCallback;

    public void setTvCallback(TvCallback tvCallback) {
        this.tvCallback = tvCallback;
    }

    private ArrayList<CatalogResponse> listTvs = new ArrayList<>();

    public void setListTvs(List<CatalogResponse> listTvs) {
        if (listTvs == null || listTvs.size() == 0 ) return;
        this.listTvs.addAll(listTvs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TvViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.catalog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        CatalogResponse model = listTvs.get(position);

        if (position == listTvs.size()-10){
            tvCallback.pageCallback();
        }

        holder.bind(model);


    }

    @Override
    public int getItemCount() {
        return listTvs.size();
    }


    public static class TvViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvScore;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvScore = itemView.findViewById(R.id.score_value);
        }


        public void bind(final CatalogResponse model) {

            if(model.getPoster().isEmpty()){
                imgPoster.setImageResource(R.color.background);
            }else {
                String url = "https://image.tmdb.org/t/p/w342" + model.getPoster();

                Glide.with(itemView.getContext())
                        .load(url)
                        .apply(RequestOptions.placeholderOf(R.color.background))
                        .into(imgPoster);
            }

            tvScore.setText(model.getScore());

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_ID,model.getCatalogId());
                intent.putExtra(DetailActivity.EXTRA_IDENTITY,"tv");
                itemView.getContext().startActivity(intent);
            });
        }
    }

    public interface TvCallback{
        void pageCallback();
    }
}
