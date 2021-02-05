package com.astech.movsee.data.source;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.astech.movsee.data.source.remote.ApiResponse;
import com.astech.movsee.utils.AppExecutors;
import com.astech.movsee.vo.Resource;

public abstract class NetworkBoundResource<ResultType,RequestType> {
    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    private AppExecutors appExecutors;

    public NetworkBoundResource(AppExecutors appExecutors){
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));

        LiveData<ResultType> dbSource = loadFromDB();

        result.addSource(dbSource, resultType -> {
            result.removeSource(dbSource);

            if (shouldFetch(resultType)){
                fetchFromNetwork(dbSource);
            }else {
                result.addSource(dbSource, newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    protected void onFetchFailed(){

    }

    protected abstract LiveData<ResultType> loadFromDB();
    protected abstract boolean shouldFetch(ResultType data);
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
    protected abstract void saveCallResult(RequestType data);
    private void fetchFromNetwork(LiveData<ResultType> dbSource){
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(dbSource, resultType -> result.setValue(Resource.loading(resultType)));

        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);

            switch (response.status){
                case SUCCESS:
                    appExecutors.getDiskIO().execute(() -> {
                        saveCallResult(response.body);

                        appExecutors.getMainThread().execute(() -> result.addSource(loadFromDB(), newData -> result.setValue(Resource.success(newData))));
                    });
                    break;
                case EMPTY:
                    appExecutors.getMainThread().execute(() -> result.addSource(loadFromDB(), newData -> result.setValue(Resource.success(newData))));
                    break;
                case ERROR:
                    onFetchFailed();
                    result.addSource(dbSource,newData -> result.setValue(Resource.error(response.message,newData)));
                    break;
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData(){
        return result;
    }
}
