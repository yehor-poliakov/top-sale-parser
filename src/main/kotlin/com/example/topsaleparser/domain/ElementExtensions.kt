package com.example.topsaleparser.domain

import org.jsoup.nodes.Element

fun Element.getTitle(title: String): String =
    select(title)
        .text()

fun Element.getCurrentPrice(currentPrice: String): String =
    select(currentPrice)
        .text()
        .replace(" ", "")

fun Element.getOldPrice(oldPrice: String): Int {
    val result = select(oldPrice)
        .text()
        .replace(" ", "")
    return if (result.isBlank()) 0 else result.toInt()
}

fun Element.getProductPageLink(link: String): String =
    select(link)
        .attr("href")
