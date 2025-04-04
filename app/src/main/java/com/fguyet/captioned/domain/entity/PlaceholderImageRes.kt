package com.fguyet.captioned.domain.entity

sealed class PlaceholderImageRes {
    data class OrganizedChaos(val id: Int) : PlaceholderImageRes()
}