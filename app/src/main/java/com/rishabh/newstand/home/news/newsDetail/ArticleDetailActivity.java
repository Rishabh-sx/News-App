package com.rishabh.newstand.home.news.newsDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rishabh.newstand.R;
import com.rishabh.newstand.base.BaseActivity;
import com.rishabh.newstand.pojo.headlinesresponse.Article;
import com.rishabh.newstand.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends BaseActivity implements
        ArticleDetailView{

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Article article;
    private Bitmap bitmap;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivityTransitions();
        article = getArticle();
        ButterKnife.bind(this);
        ArticleDetailPresenter movieDetailPresenter = new ArticleDetailPresenter(this);
        movieDetailPresenter.initView();

    }

    
    private Article getArticle() {
        return getIntent().getParcelableExtra(AppConstants.KEY_ARTICLE);
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_details;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void initActivityTransitions() {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
    }

    private void applyPalette(Palette palette) {
        int primaryDark = ContextCompat.getColor(this,R.color.colorPrimaryDark);
        int primary = ContextCompat.getColor(this,R.color.colorPrimary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        //      updateBackground((FloatingActionButton) findViewById(R.id.share_fab), palette);
        supportStartPostponedEnterTransition();
        setTransparentStatusBar(palette);
    }

    
    private void setTransparentStatusBar(Palette palette) {
        int color = palette.getMutedColor(ContextCompat.getColor(this,R.color.colorPrimary));
            if (getWindow() != null)
                getWindow().setStatusBarColor(color);
    }

    
    @Override
    public void initViews() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }String itemTitle = article.getTitle();
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(itemTitle);
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,android.R.color.transparent));

        Glide.with(image.getContext())
                .load(article.getUrlToImage())
                .asBitmap().listener(new RequestListener<String, Bitmap>() {
            @Override
            public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }


            @Override
            public boolean onResourceReady(Bitmap resource, String model,
                                           Target<Bitmap> target,
                                           boolean isFromMemoryCache,
                                           boolean isFirstResource) {

                image.setImageBitmap(resource);
                if (image.getDrawable() != null) {
                    bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                        public void onGenerated(Palette palette) {
                            applyPalette(palette);
                        }
                    });
                }
                return true;
            }
        }).into(image);

        title.setText(article.getTitle());
        if (article.getContent() != null) {
            String content[] = article.getContent().split("\\s+(?=\\[)");
            if (content.length >= 2) {
                String viewMore = content[1];
                String message = content[0];
                description.setText(String.format("%s %s", message, viewMore.replace(viewMore,
                        getString(R.string.s_view_more))));
                addClickToViewMore();
            }
        }

    }

    private void addClickToViewMore() {
        Spannable span = Spannable.Factory.getInstance().newSpannable(description.getText().toString());
        span.setSpan(new ClickableSpan() {
                         @Override
                         public void onClick(@NonNull View v) {
                             try {
                                 String url = article.getUrl();
                                 Intent i = new Intent(Intent.ACTION_VIEW);
                                 i.setData(Uri.parse(url));
                                 startActivity(i);
                             } catch (Exception e) {
                                 Toast.makeText(ArticleDetailActivity.this, getString(R.string.s_brwoser_not_found), Toast.LENGTH_SHORT).show();
                             }
                         }
                     }, description.getText().toString().indexOf(getString(R.string.s_view_more)),
                description.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        span.setSpan(new ForegroundColorSpan(getColor(R.color.colorAccent)), description.getText().toString().indexOf(getString(R.string.s_view_more)),
                description.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        description.setText(span);
        description.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

