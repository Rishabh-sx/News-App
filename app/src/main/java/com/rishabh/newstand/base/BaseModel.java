package com.rishabh.newstand.base;

import com.rishabh.newstand.data.DataManager;
import com.rishabh.newstand.network.CommonResponseHandler;

import java.lang.ref.SoftReference;


public abstract class BaseModel<T extends BaseModelListener> implements CommonResponseHandler {

    private static final int NO_NETWORK = 999;
    private SoftReference<T> listener;

    protected BaseModel(T listener) {
        this.listener = new SoftReference<>(listener);
    }

    public void attachListener(T listener) {
        this.listener = new SoftReference<>(listener);
    }

    public void detachListener() {
        this.listener = null;
    }

    public T getListener() {
        return (listener != null) ? listener.get() : null;
    }

    public abstract void init();


    private void noNetworkAvailableError() {
        getListener().noNetworkError();
    }

    @Override
    public void onNetworkError() {
        noNetworkAvailableError();
    }

    public DataManager getDataManager() {
        return DataManager.getInstance();
    }
}
