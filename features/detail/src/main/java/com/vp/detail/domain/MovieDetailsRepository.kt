package com.vp.detail.domain

import io.reactivex.Observable


interface MovieDetailsRepository {
    fun getMovieDetails(id: String): Observable<MovieDetail>
    fun saveFavorite(movieDetail: MovieDetail): Observable<Boolean>
    fun deleteFavorite(id: String): Observable<Boolean>
}