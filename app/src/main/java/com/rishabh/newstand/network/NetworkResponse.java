package com.rishabh.newstand.network;



import android.support.annotation.NonNull;

import com.rishabh.newstand.pojo.FailureResponse;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class NetworkResponse<T> implements Callback<T> {
    private final CommonResponseHandler handler;

    public NetworkResponse(CommonResponseHandler handler){
        this.handler = handler;
    }

    protected abstract void onSuccess(T body);
    public abstract void onFailure(int code, FailureResponse failureResponse);
    public abstract void onError(Throwable t);




    protected void failure(int code, FailureResponse failureResponse){
        onFailure(code, failureResponse);
    }

    protected void error(Throwable t){
        if(t instanceof SocketTimeoutException || t instanceof UnknownHostException){
            handler.onNetworkError();
        }
        onError(t);
    }

    @Override
    public void onResponse(@NonNull Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            failure(response.code(),getFailureErrorBody(call.request().url().url().getFile(),response));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        error(t);
    }


    /**
     * Create your custom failure response out of server response
     * Also save Url for any further use
     * */
    protected final FailureResponse getFailureErrorBody(String url, Response<T> errorBody) {
        FailureResponse failureResponse = new FailureResponse();
        failureResponse.setMsg(errorBody.message());
        failureResponse.setErrorCode(errorBody.code());
        return failureResponse;
    }
}
