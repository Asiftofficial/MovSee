package com.astech.movsee.ui.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astech.movsee.R;
import com.astech.movsee.data.source.remote.response.GenreResponse;

import java.util.ArrayList;
import java.util.List;

public class MovieGenreAdapter extends RecyclerView.Adapter<MovieGenreAdapter.MovieGenreViewHolder> {
    private GenreCallback genreCallback;

    public void setGenreCallback(GenreCallback genreCallback) {
        this.genreCallback = genreCallback;
    }

    ArrayList<GenreResponse> listGenre = new ArrayList<>();

    public void setListGenre(List<GenreResponse> listGenre) {
        if (listGenre.size() == 0) return;
        this.listGenre.clear();
        this.listGenre.addAll(listGenre);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieGenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_item,parent,false);
        return new MovieGenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieGenreViewHolder holder, int position) {
        GenreResponse data = listGenre.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return listGenre.size();
    }

    public class MovieGenreViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGenre;

        public MovieGenreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenre = itemView.findViewById(R.id.text_genre);
        }

        public void bind(GenreResponse data){
            tvGenre.setText(data.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    genreCallback.genreId(data);
                }
            });


        }
    }

    public interface GenreCallback{
        void genreId(GenreResponse data);
    }
}
