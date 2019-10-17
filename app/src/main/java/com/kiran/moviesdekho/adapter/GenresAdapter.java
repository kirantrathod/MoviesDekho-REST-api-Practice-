package com.kiran.moviesdekho.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kiran.moviesdekho.R;
import com.kiran.moviesdekho.model.movies.GenreItem;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {
    private static final String TAG = GenresAdapter.class.getSimpleName();

    private List<GenreItem> genres;
    private int rowLayout;
    private Context context;

    public GenresAdapter(List<GenreItem> genres, int rowLayout, Context context) {
        this.genres = genres;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView genreName;
        public GenreViewHolder(View v) {
            super(v);
            genreName = (TextView) v.findViewById(R.id.genreName);
        }
    }

    @Override
    public GenresAdapter.GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, final int position) {
        holder.genreName.setText(this.genres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }
}
