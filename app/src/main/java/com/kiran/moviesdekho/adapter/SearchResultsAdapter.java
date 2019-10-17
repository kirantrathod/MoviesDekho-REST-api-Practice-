package com.kiran.moviesdekho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kiran.moviesdekho.R;
import com.kiran.moviesdekho.model.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.searchViewHolder> {
    private List<Movie> searchMovies;
    private int rowLayout;
    private Context context;

    public SearchResultsAdapter(List<Movie> searchMovies, int rowLayout, Context context) {
        this.searchMovies = searchMovies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w342//";

    @NonNull
    @Override
    public SearchResultsAdapter.searchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new searchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsAdapter.searchViewHolder holder, int position) {
        String image_url = IMAGE_URL_BASE_PATH + searchMovies.get(position).getPosterPath();
        Picasso.get().
                load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(holder.movieImage);
        holder.movieTitle.setText(searchMovies.get(position).getTitle());
        holder.data.setText(searchMovies.get(position).getReleaseDate());
        holder.movieDescription.setText(searchMovies.get(position).getOverview());
        holder.rating.setText(searchMovies.get(position).getVoteAverage().toString());

    }

    @Override
    public int getItemCount() {
        return searchMovies.size();
    }

    public static class searchViewHolder extends RecyclerView.ViewHolder{
        LinearLayout moviesLayout;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;
        ImageView movieImage;
        public searchViewHolder(@NonNull View v) {
            super(v);
            moviesLayout = (LinearLayout) v.findViewById(R.id.movies_layout);
            movieImage = (ImageView) v.findViewById(R.id.movie_image);
            movieTitle = (TextView) v.findViewById(R.id.title);
            data = (TextView) v.findViewById(R.id.date);
            movieDescription = (TextView) v.findViewById(R.id.description);
            rating = (TextView) v.findViewById(R.id.rating);
        }
    }
}
