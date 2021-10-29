package com.example.topsaleparser.parser;

import com.example.topsaleparser.domain.Product
import com.example.topsaleparser.domain.ProductType
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

@Component
class TechParser {

    fun parseProduct(productType: ProductType): List<Product> {
        val productLink = productType.link
        val doc: Document = Jsoup.connect("https://rozetka.com.ua/ua/$productLink/c80004/sort=action/").get()

        val productElements: Elements = doc.select(".goods-tile__inner")
        val result = mutableListOf<Product>()

        for ((index, headline) in productElements.withIndex()) {
            if (index < 3) {
                headline.select("small").remove()
                val title = headline.select(".goods-tile__title").text()
                val currentPrice = headline.select(".goods-tile__price-value").text().replace(" ", "")
                val oldPrice = headline.select(".goods-tile__price--old").text().replace(" ", "")
                result.add(
                    Product(
                        title = title,
                        currentPrice = currentPrice.toBigDecimal(),
                        oldPrice = oldPrice.toBigDecimal()
                    )
                )
            }
        }
        return result
    }
}
