package com.fguyet.captioned.domain.entity

sealed class ImageRes {
    data class Placeholder(val placeholderImageRes: PlaceholderImageRes) : ImageRes()
    data class Remote(val uri: String) : ImageRes()
}

