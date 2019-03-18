package com.rishabh.newstand.home.search.searched;

import com.rishabh.newstand.base.RxBaseModel;
import com.rishabh.newstand.data.api.ApiManager;
import com.rishabh.newstand.pojo.FailureResponse;
import com.rishabh.newstand.pojo.headlinesresponse.HeadlinesResponse;
import com.rishabh.newstand.utils.AppConstants;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;

class SearchRxModel extends RxBaseModel<HeadlinesResponse> {

    public SearchRxModel(SearchModelListener listener) {
        super(listener);
    }

    public void getSearch(String search) {
        ApiManager.getInstance().rxSearch(createResponsePayload(search))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
    }

    private HashMap<String, String> createResponsePayload(String search) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(AppConstants.KEY_QUERY, search);
        hashMap.put(AppConstants.KEY_LANGUAGE, "en");
        hashMap.put(AppConstants.KEY_API_KEY, AppConstants.NEWS_API_KEY);
        return hashMap;
    }

    @Override
    public void onNext(HeadlinesResponse headlinesResponse) {
        if (headlinesResponse.getArticles() != null && headlinesResponse.getArticles().size() > 0) {
            ((SearchModelListener) getListener()).onSearchResponse(headlinesResponse.getArticles());
        } else {
            FailureResponse failureResponse = new FailureResponse();
            failureResponse.setErrorCode(109);
            failureResponse.setMsg("Something went wrong");
            getListener().onErrorOccurred(failureResponse);
        }
    }
}
