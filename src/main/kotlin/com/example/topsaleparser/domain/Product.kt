package com.example.topsaleparser.domain

data class Product(
        val title: String,
        val currentPrice: Int,
        val oldPrice: Int,
        val imgLink: String? = null,
        val productLink: String? = null
)
