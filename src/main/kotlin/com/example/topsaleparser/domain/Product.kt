package com.example.topsaleparser.domain

import java.math.BigDecimal

data class Product(
    val id: String? = null,
    val title: String,
    val currentPrice: BigDecimal,
    val oldPrice: BigDecimal,
    val imgLink: String? = null,
    val productLink: String? = null
)
