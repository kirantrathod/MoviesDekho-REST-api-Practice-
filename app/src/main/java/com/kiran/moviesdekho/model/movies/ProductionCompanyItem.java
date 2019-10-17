package com.kiran.moviesdekho.model.movies;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductionCompanyItem {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("origin_country")
    @Expose
    private String origin_country;

    @SerializedName("logo_path")
    @Expose
    private String logo_path;

    @SerializedName("id")
    @Expose
    private Integer id;

    public ProductionCompanyItem(String name, String origin_country, String logo_path, Integer id) {
        this.name = name;
        this.origin_country = origin_country;
        this.logo_path = logo_path;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_country() {
        return origin_country;
    }

    public void setOrigin_country(String origin_country) {
        this.origin_country = origin_country;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductionCompanyItem() {
    }

    @NonNull
    @Override
    public String toString() {
        return "ProductionCompanyItem{" +
                "name='" + name + '\'' +
                ", origin_country='" + origin_country + '\'' +
                ", logo_path='" + logo_path + '\'' +
                ", id=" + id +
                '}';
    }
}
