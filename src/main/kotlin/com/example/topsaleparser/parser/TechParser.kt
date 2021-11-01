package com.example.topsaleparser.parser

import com.example.topsaleparser.domain.Product
import com.example.topsaleparser.domain.ProductType
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

@Component
class TechParser {

    fun parseProduct(productType: ProductType): List<Product> {
        val productLink = productType.link
        val doc = Jsoup.connect("https://rozetka.com.ua/ua/$productLink/c80004/sort=action/").get()
        val productElements: Elements = doc.select(".goods-tile__inner")
        val result = mutableListOf<Product>()
        for (headline in productElements) {
            headline.select("small").remove()
            val title = headline.select(".goods-tile__title").text()
            val currentPrice = headline.select(".goods-tile__price-value").text().replace(" ", "")
            val oldPrice = headline.select(".goods-tile__price--old").text().replace(" ", "")
            val goodLink = headline.select(".goods-tile__picture").attr("href")
            val imgLink = Jsoup.connect(goodLink).get().select(".picture-container__picture").attr("src")
            result.add(
                    Product(
                            title = title,
                            currentPrice = currentPrice.toInt(),
                            oldPrice = if (oldPrice.isBlank()) 0 else oldPrice.toInt(),
                            imgLink = imgLink,
                            productLink = goodLink
                    )
            )
        }
        return result
    }

//    fun imitateWebClient(productType: ProductType, productLink: String): Document {
//        val options = ChromeOptions()
//        options.addArguments("--headless")
//        options.addArguments("--disable-gpu")
//        WebDriverManager.chromedriver().setup()
//        val driver = ChromeDriver(options)
//        driver.get("https://rozetka.com.ua/ua/$productLink/c80004/sort=action/")
//        return Jsoup.parse(driver.pageSource)
//    }
}
