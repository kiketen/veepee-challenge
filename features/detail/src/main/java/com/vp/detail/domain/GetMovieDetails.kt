package com.vp.detail.domain

import io.reactivex.Observable


interface GetMovieDetails {
    operator fun invoke(id: String): Observable<MovieDetail>
}

class GetMovieDetailsImpl(
        private val movieDetailsRepository: MovieDetailsRepository
) : GetMovieDetails {
    override fun invoke(id: String): Observable<MovieDetail> {
        return movieDetailsRepository.getMovieDetails(id)
    }
}

