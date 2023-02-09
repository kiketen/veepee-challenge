package com.vp.detail.domain


interface SetFavoriteMovie {
    operator fun invoke(movieDetail: MovieDetail)
}

class SetFavoriteMovieImpl(
        private val movieDetailsRepository: MovieDetailsRepository
) : SetFavoriteMovie {
    override fun invoke(movieDetail: MovieDetail) {
        return movieDetailsRepository.saveFavorite(movieDetail)
    }
}

