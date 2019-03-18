package com.rishabh.newstand.base;

import com.rishabh.newstand.pojo.FailureResponse;

import java.lang.ref.SoftReference;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class RxBaseModel<T> implements Observer<T> {

    private SoftReference<BaseModelListener> listener;
    private CompositeDisposable disposable;

    public RxBaseModel(BaseModelListener listener) {
        attachListener(listener);
    }

    private void attachListener(BaseModelListener listener) {
        this.listener = new SoftReference<>(listener);
        disposable = new CompositeDisposable();
    }

    public void detachListener() {
        this.listener = null;
        flushDisposable();
    }

    public BaseModelListener getListener() {
        return (listener != null) ? listener.get() : null;
    }

    private void flushDisposable() {
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public void clearDisposable() {
        disposable.clear();
    }

    @Override
    public void onError(Throwable e) {
        if ((e instanceof SocketTimeoutException || e instanceof UnknownHostException)) {
            noNetworkAvailableError();
        } else {
            onSpecificErrorOccurred(e);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable.add(d);
    }

    @Override
    public void onComplete() {
        clearDisposable();
    }

    private void onSpecificErrorOccurred(Throwable e) {
        FailureResponse failureResponse = null;
        if (e != null && e instanceof HttpException) {
            failureResponse = new FailureResponse();
            failureResponse.setErrorCode(((HttpException) e).code());
            failureResponse.setMsg(((HttpException) e).message());
        }
        getListener().onErrorOccurred(failureResponse);
    }

    private void noNetworkAvailableError() {
        getListener().noNetworkError();
    }
}
