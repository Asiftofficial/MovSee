package com.astech.movsee.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.di.Injection;
import com.astech.movsee.ui.detail.DetailViewModel;
import com.astech.movsee.ui.detail.video.VideoViewModel;
import com.astech.movsee.ui.favorite.movies.FavoriteMoviesViewModel;
import com.astech.movsee.ui.favorite.tv.FavoriteTvViewModel;
import com.astech.movsee.ui.movies.MoviesViewModel;
import com.astech.movsee.ui.movies.genre.MovieGenreViewModel;
import com.astech.movsee.ui.search.movies.SearchMovieViewModel;
import com.astech.movsee.ui.search.tv.SearchTvViewModel;
import com.astech.movsee.ui.tv.TvViewModel;
import com.astech.movsee.ui.tv.genre.TvGenreViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private MovSeeRepository movSeeRepository;
    private static ViewModelFactory INSTANCE;

    private ViewModelFactory(MovSeeRepository movSeeRepository){
        this.movSeeRepository = movSeeRepository;
    }

    public static ViewModelFactory getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ViewModelFactory.class){
                if (INSTANCE == null){
                    INSTANCE = new ViewModelFactory(Injection.provideRepository(context));
                }
            }
        }

        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(MoviesViewModel.class)){
            return (T) new MoviesViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(DetailViewModel.class)){
            return (T) new DetailViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(VideoViewModel.class)){
            return (T) new VideoViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(TvViewModel.class)){
            return (T) new TvViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(FavoriteMoviesViewModel.class)){
            return (T) new FavoriteMoviesViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(FavoriteTvViewModel.class)){
            return (T) new FavoriteTvViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(SearchMovieViewModel.class)){
            return (T) new SearchMovieViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(SearchTvViewModel.class)){
            return (T) new SearchTvViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(MovieGenreViewModel.class)){
            return (T) new MovieGenreViewModel(movSeeRepository);
        }else if (modelClass.isAssignableFrom(TvGenreViewModel.class)){
            return (T) new TvGenreViewModel(movSeeRepository);
        }

        throw new IllegalArgumentException("Unknown ViewModel Class : " + modelClass.getName());
    }

}
