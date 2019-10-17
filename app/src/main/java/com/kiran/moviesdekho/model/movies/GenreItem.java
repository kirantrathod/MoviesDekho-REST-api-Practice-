package com.kiran.moviesdekho.model.movies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreItem {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenreItem(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreItem() {
    }

    @NonNull
    @Override
    public String toString() {
        return "GenreItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
