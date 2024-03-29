package com.rishabh.newstand.home.news;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rishabh.newstand.R;
import com.rishabh.newstand.base.BaseFragment;
import com.rishabh.newstand.home.BaseHost;
import com.rishabh.newstand.home.news.headlines.HeadLinesFragment;
import com.rishabh.newstand.pojo.headlinesresponse.Article;
import com.rishabh.newstand.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment implements NewsView, HeadLinesFragment.IHeadlinesFragmentHost {


    private Unbinder unbinder;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.pager_title_strip)
    PagerTabStrip pagerTitleStrip;

    @Inject
    NewsPresenter newsPresenter;

    private INewsFragmentHost mHost;

    public interface INewsFragmentHost extends BaseHost {
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof INewsFragmentHost) {
            mHost = (INewsFragmentHost) getActivity();
        } else {
            throw new IllegalStateException("Host must Implement INewsFragmentHost");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        newsPresenter.attachView(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsPresenter.onViewCreated();
    }

    @Override
    public void setupViews() {
        setupViewPager();
    }

    /**
     * Method to setup ViewPager
     */
    private void setupViewPager() {

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        pagerAdapter.addFragment(HeadLinesFragment.getInstance(AppConstants.KEY_POPULAR),
                getTitle(AppConstants.KEY_POPULAR));

        pagerAdapter.addFragment(HeadLinesFragment.getInstance(AppConstants.KEY_BUSINESS),
                getTitle(AppConstants.KEY_BUSINESS));

        pagerAdapter.addFragment(HeadLinesFragment.getInstance(AppConstants.KEY_TECHNOLOGY),
                getTitle(AppConstants.KEY_TECHNOLOGY));

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);

    }

    /**
     * Method to return tabs title
     *
     * @param titleKey is used to extracting title
     * @return title
     */
    private String getTitle(String titleKey) {
        switch (titleKey) {
            case AppConstants.KEY_POPULAR:
                return getString(R.string.popular);
            case AppConstants.KEY_BUSINESS:
                return getString(R.string.business);
            case AppConstants.KEY_TECHNOLOGY:
                return getString(R.string.technology);

        }
        return "";
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * View pager adapter for headlines
     */
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<BaseFragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(HeadLinesFragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void openArticleDetail(Article article) {
        mHost.openArticleDetail(article);
    }

    @Override
    public void share(String url) {
        mHost.share(url);
    }
}
