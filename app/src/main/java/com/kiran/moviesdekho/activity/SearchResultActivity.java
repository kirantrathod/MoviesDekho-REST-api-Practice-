package com.kiran.moviesdekho.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SearchView;

import com.kiran.moviesdekho.Config;
import com.kiran.moviesdekho.R;
import com.kiran.moviesdekho.adapter.SearchResultsAdapter;
import com.kiran.moviesdekho.model.movie.Movie;
import com.kiran.moviesdekho.model.search.SearchResponse;
import com.kiran.moviesdekho.rest.API;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView = null;
    private String movieName;
    List<Movie> resultsMovie;

    private static final String TAG = SearchResultActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewSearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieName=getIntent().getStringExtra("movieName");
        /*API.movies().search(Config.API_KEY,movieName).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                resultsMovie = response.body().getResults();
                recyclerView.setAdapter(new SearchResultsAdapter(resultsMovie, R.layout.list_item_movie, getApplicationContext()));
                Log.d(TAG, "Number of movies received: " + resultsMovie.size());
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,new MainActivity.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                        intent.putExtra("movieId", resultsMovie.get(position).getId());
                        startActivity(intent);
                    }
                }, 400);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
   */
        handleIntent(getIntent());
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MainActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MainActivity.ClickListener clickListener) {

            this.clickListener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
        super.onNewIntent(intent);

    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search
            API.movies().search(Config.API_KEY,query).enqueue(new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                    resultsMovie = response.body().getResults();
                    recyclerView.setAdapter(new SearchResultsAdapter(resultsMovie, R.layout.list_item_movie, getApplicationContext()));
                    //Log.d(TAG, "Number of movies received: " + resultsMovie.size());
                    //Toast.makeText(getApplicationContext(),resultsMovie.size(),Toast.LENGTH_SHORT).show();
                    //noOfSearchResults.setText(resultsMovie.size());
                }
                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });


            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView,new MainActivity.ClickListener() {
                @Override
                public void onClick(View view, final int position) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                            intent.putExtra("movieId", resultsMovie.get(position).getId());
                            startActivity(intent);
                        }
                    }, 400);
                }
                @Override
                public void onLongClick(View view, int position) {

                }
            }));

        }
    }

}
