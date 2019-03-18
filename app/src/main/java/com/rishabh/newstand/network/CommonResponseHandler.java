package com.rishabh.newstand.network;



/**
 * This is to be used for handling common responses
 * such as no network or authentication failed
 * */

public interface CommonResponseHandler {
    void onNetworkError();
}
