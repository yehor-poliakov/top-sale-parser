package com.example.topsaleparser.parser

import com.example.topsaleparser.domain.Product
import com.example.topsaleparser.domain.ProductType
import com.example.topsaleparser.domain.getCurrentPrice
import com.example.topsaleparser.domain.getLink
import com.example.topsaleparser.domain.getOldPrice
import com.example.topsaleparser.domain.getTitle
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

@Component
class TechParser {

    private val baseDomain = "https://rozetka.com.ua/ua/"
    private val discountDomain = "/sort=action/"
    private val listedProduct = ".goods-tile__inner"
    private val junkElementsClass = "small"
    private val listedProductLink = ".goods-tile__picture"
    private val listedProductTitle = ".goods-tile__title"
    private val listedProductPrice = ".goods-tile__price-value"
    private val listedProductOldPrice = ".goods-tile__price--old"
    private val productImage = ".picture-container__picture"

    fun parseProduct(productType: ProductType): List<Product> {
        val sourceLink = getSourceLink(productType)
        val sourceDocument = Jsoup.connect(sourceLink).get()
        val productElements: Elements = sourceDocument.select(listedProduct)
        val result = mutableListOf<Product>()
        productElements
            .forEach {
                it.select(junkElementsClass).remove()
                val productPageLink = it.getLink(listedProductLink)
                result.add(
                    Product(
                        it.getTitle(listedProductTitle),
                        it.getCurrentPrice(listedProductPrice).toInt(),
                        it.getOldPrice(listedProductOldPrice),
                        getImgLink(fetchProductSource(productPageLink), productImage),
                        productPageLink
                    )
                )
            }
        return result
    }

    fun fetchProductSource(singleProductLink: String): Document = Jsoup.connect(singleProductLink).get()

    fun getSourceLink(productType: ProductType): String {
        val typeLink = productType.link
        return "$baseDomain$typeLink/$discountDomain"
    }

    fun  getImgLink(source: Document, productLink: String): String =
        source
            .select(productLink)
            .attr("src")
}
