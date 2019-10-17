package com.kiran.moviesdekho.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kiran.moviesdekho.Config;
import com.kiran.moviesdekho.R;
import com.kiran.moviesdekho.adapter.GenresAdapter;
import com.kiran.moviesdekho.adapter.ProductionCompaniesAdapter;
import com.kiran.moviesdekho.model.movie.MovieResponse;
import com.kiran.moviesdekho.rest.API;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {
    private final static String TAG = MovieDetailsActivity.class.getSimpleName();
    private Context context;
    private static Retrofit retrofit = null;
    private Toolbar toolbar;
    private TextView tv_title, tv_original_title;
    private WebView webView;
    private ImageView img_movie;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView genres_recycler_view;
    private RecyclerView production_companies_rv;
    private AppBarLayout appBarLayout;
    private String title, overview;
    private final static String API_KEY = "8c2cca40d0d357c045dd2a2f44fe587a";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        this.context = getApplicationContext();

        //Find views
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        img_movie = (ImageView) findViewById(R.id.image);
        tv_title = (TextView) findViewById(R.id.title);
        tv_original_title = (TextView) findViewById(R.id.original_title);
        webView = (WebView) findViewById(R.id.desc);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        genres_recycler_view = (RecyclerView) findViewById(R.id.genres_recycler_view);
        production_companies_rv = (RecyclerView) findViewById(R.id.production_companies_rv);

        //setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        genres_recycler_view.setHasFixedSize(true);
        genres_recycler_view.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        production_companies_rv.setHasFixedSize(true);
        production_companies_rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        Intent iGet = getIntent();
        int movieId = iGet.getIntExtra("movieId", 0);


        API.movies().movieDetails(movieId, Config.API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                final MovieResponse movie = response.body();

                title = movie.getTitle();
                overview = movie.getOverview();
                if (actionBar != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setTitle(title);
                }

                appBarLayout.setExpanded(true);

                // hiding & showing the title when toolbar expanded & collapsed
                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    boolean isShow = false;
                    int scrollRange = -1;

                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (scrollRange == -1) {
                            scrollRange = appBarLayout.getTotalScrollRange();
                        }
                        if (scrollRange + verticalOffset == 0) {
                            collapsingToolbarLayout.setTitle(title);
                            isShow = true;
                        } else if (isShow) {
                            collapsingToolbarLayout.setTitle(title);
                            isShow = false;
                        }
                    }
                });
                collapsingToolbarLayout.setTitle(title);
                renderMovie(movie);
                progressBar.setVisibility(View.GONE);
                coordinatorLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

    }
        public void renderMovie (MovieResponse movie){
            tv_title.setText(movie.getTitle());
            tv_original_title.setText(context.getString(R.string.tv_original_title, movie.getOriginalTitle(), movie.getReleaseDate().toString()));
            webView.setBackgroundColor(Color.parseColor("#ffffff"));
            webView.setFocusableInTouchMode(false);
            webView.setFocusable(false);
            webView.getSettings().setDefaultTextEncodingName("UTF-8");
            WebSettings ws = webView.getSettings();
            Resources res = getResources();
            ws.setDefaultFontSize(15);
            String mimeType = "text/html; charset=UTF-8";
            String encoding = "utf-8";
            String text = "<html>"
                    + "<head>"
                    + "<style type=\"text/css\">body{color: #525252;}"
                    + "</style></head>"
                    + "<body><h1>Overview:</h1>"
                    +  "<p>" + overview.toString() +"</p>"
                    + "</body>"
                    + "</html>";

            webView.loadData(text, mimeType, encoding);

            Picasso.get()
                    .load(Config.IMAGE_URL_BASE_PATH + movie.getBackdropPath())
                    .into(img_movie, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) img_movie.getDrawable()).getBitmap();
                            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                @Override
                                public void onGenerated(Palette palette) {

                                }
                            });
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(getApplicationContext(), "Error loading picture!", Toast.LENGTH_SHORT).show();
                        }

                    });
            genres_recycler_view.setAdapter(
                    new GenresAdapter(
                            movie.getGenres(),
                            R.layout.list_item_genre,
                            context
                    )
            );

            production_companies_rv.setAdapter(
                    new ProductionCompaniesAdapter(
                            movie.getProductionCompanies(),
                            R.layout.list_item_pc,
                            context
                    )
            );

        }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_movie_details, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem menuItem){
            switch (menuItem.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    break;
                case R.id.menu_share:
                    String formattedString = android.text.Html.fromHtml(overview).toString();
                    Intent share = new Intent();
                    share.setAction(Intent.ACTION_SEND);
                    share.putExtra(Intent.EXTRA_TITLE, title);
                    share.putExtra(Intent.EXTRA_PACKAGE_NAME, getPackageName());
                    share.putExtra(Intent.EXTRA_TEXT, formattedString);
                    share.setType("text/plain");
                    startActivity(share);
                    break;
                default:
                    return super.onOptionsItemSelected(menuItem);
            }
            return true;
        }

        @Override
        public void onStart () {
            super.onStart();
        }

        @Override
        public void onStop () {
            super.onStop();
        }

        @Override
        protected void onPause () {
            super.onPause();
        }

        @Override
        protected void onResume () {
            super.onResume();
        }

        @Override
        protected void onDestroy () {
            super.onDestroy();
        }





}