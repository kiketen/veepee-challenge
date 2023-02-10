package com.vp.detail.domain

import io.reactivex.Observable


interface SetFavoriteMovie {
    operator fun invoke(movieDetail: MovieDetail, isFavorite: Boolean): Observable<Boolean>
}

class SetFavoriteMovieImpl(
        private val movieDetailsRepository: MovieDetailsRepository
) : SetFavoriteMovie {
    override fun invoke(movieDetail: MovieDetail, isFavorite: Boolean): Observable<Boolean> {
        return if (isFavorite) {
            movieDetailsRepository.saveFavorite(movieDetail)
        } else {
            movieDetailsRepository.deleteFavorite(movieDetail.id)
        }
    }
}

