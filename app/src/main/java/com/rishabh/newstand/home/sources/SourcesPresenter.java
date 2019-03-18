package com.rishabh.newstand.home.sources;

import com.rishabh.newstand.base.BasePresenter;



public class SourcesPresenter extends BasePresenter<SourcesView> implements SourcesModelListener {


    private SourcesModel model;

    public SourcesPresenter() {
        super(null);
    }

    @Override
    protected void setModel() {
        model = new SourcesModel(this);
    }

    @Override
    protected void destroy() {
        model.detachListener();
        model = null;
    }

    @Override
    protected void initView() {
    }

    public void onViewCreated() {
        model.getSources();
        setupView();
    }

    public void fragmentConfigChanged() {
        setupView();
    }

    private void setupView() {
        if (getView() != null)
            getView().initView();
        getView().initListeners();
    }
}
