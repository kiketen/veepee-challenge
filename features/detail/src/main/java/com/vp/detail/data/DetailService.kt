package com.vp.detail.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DetailService {
    @GET("/")
    fun getMovie(@Query("i") imdbID: String): Observable<MovieDetailApiModel>
}