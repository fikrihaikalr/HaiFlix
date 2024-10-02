package com.fikrihaikal.haiflix.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie (
    val img: String?,
    val name: String?,
    val id: Int?,
    val overview: String?,
    val voteAverage: Double?,
    val voteCount: Double?,
    val runtime: Int?,
    val releaseDate: String?,
    val isFavorite: Boolean?,
): Parcelable