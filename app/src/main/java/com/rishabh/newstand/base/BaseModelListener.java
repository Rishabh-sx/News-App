package com.rishabh.newstand.base;

import com.rishabh.newstand.pojo.FailureResponse;


public interface BaseModelListener {
    void noNetworkError();
    void onErrorOccurred(FailureResponse failureResponse);
}