package com.astech.movsee.di;

import android.content.Context;

import com.astech.movsee.data.source.MovSeeRepository;
import com.astech.movsee.data.source.local.LocalDataSource;
import com.astech.movsee.data.source.local.room.MovSeeDatabase;
import com.astech.movsee.data.source.remote.JsonHelper;
import com.astech.movsee.data.source.remote.RemoteDataSource;
import com.astech.movsee.utils.AppExecutors;

public class Injection {

    public static MovSeeRepository provideRepository(Context context){
        AppExecutors appExecutors = new AppExecutors();
        MovSeeDatabase db = MovSeeDatabase.getInstance(context);
        LocalDataSource localDataSource = LocalDataSource.getInstance(db.movSeeDao());
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JsonHelper(context));
        return MovSeeRepository.getInstance(remoteDataSource,localDataSource,appExecutors);
    }
}
