package com.example.topsaleparser.parser

import com.example.topsaleparser.domain.Product
import com.example.topsaleparser.domain.ProductType
import com.example.topsaleparser.domain.getCurrentPrice
import com.example.topsaleparser.domain.getOldPrice
import com.example.topsaleparser.domain.getProductPageLink
import com.example.topsaleparser.domain.getTitle
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

@Component
class TechParser {

    companion object {
        private const val BASE_DOMAIN = "https://rozetka.com.ua/ua/"
        private const val DISCOUNT_DOMAIN = "/sort=action/"
        private const val LISTED_PRODUCT = ".goods-tile__inner"
        private const val JUNK_ELEMENT = "small"
        private const val LISTED_PRODUCT_LINK = ".goods-tile__picture"
        private const val LISTED_PRODUCT_TITLE = ".goods-tile__title"
        private const val LISTED_PRODUCT_PRICE = ".goods-tile__price-value"
        private const val LISTED_PRODUCT_OLD_PRICE = ".goods-tile__price--old"
        private const val PRODUCT_IMAGE = ".picture-container__picture"
    }

    fun parseProduct(productType: ProductType): List<Product> {
        val sourceLink = buildSourceLink(productType)
        val sourceDocument = Jsoup.connect(sourceLink).get()
        val productElements: Elements = sourceDocument.select(LISTED_PRODUCT)
        val products = mutableListOf<Product>()
        productElements
            .forEach {
                it.select(JUNK_ELEMENT).remove()
                val productPageLink = it.getProductPageLink(LISTED_PRODUCT_LINK)
                val currentPrice = it.getCurrentPrice(LISTED_PRODUCT_PRICE).toInt()
                products.add(
                    Product(
                        it.getTitle(LISTED_PRODUCT_TITLE),
                        currentPrice,
                        it.getOldPrice(LISTED_PRODUCT_OLD_PRICE, currentPriceg),
                        getImgLink(fetchProductSource(productPageLink), PRODUCT_IMAGE),
                        productPageLink
                    )
                )
            }
        return products
    }

    fun fetchProductSource(singleProductLink: String): Document = Jsoup.connect(singleProductLink).get()

    fun buildSourceLink(productType: ProductType): String {
        val typeLink = productType.link
        return "$BASE_DOMAIN$typeLink/$DISCOUNT_DOMAIN"
    }

    fun  getImgLink(source: Document, productLink: String): String =
        source
            .select(productLink)
            .attr("src")
}
