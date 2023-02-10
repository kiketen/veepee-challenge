package com.vp.core.presentation.list;

import com.google.gson.annotations.SerializedName;

public class ListItem {

    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    private String imdbID;
    @SerializedName("Poster")
    private String poster;

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String value) {
        imdbID = value;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String value) {
        poster = value;
    }
}
