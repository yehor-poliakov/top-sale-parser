package com.example.topsaleparser.parser

import com.example.topsaleparser.domain.Product
import com.example.topsaleparser.domain.ProductType
import io.github.bonigarcia.wdm.WebDriverManager
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Component

@Component
class TechParser {

    fun parseProduct(productType: ProductType): List<Product> {
        val productLink = productType.link
        val doc = imitateWebClient(productType, productLink)

        val productElements: Elements = doc.select(".goods-tile__inner")
        val result = mutableListOf<Product>()

        for ((index, headline) in productElements.withIndex()) {
            if (index < 3) {
                headline.select("small").remove()
                val title = headline.select(".goods-tile__title").text()
                val currentPrice = headline.select(".goods-tile__price-value").text().replace(" ", "")
                val oldPrice = headline.select(".goods-tile__price--old").text().replace(" ", "")
                val imgLink = headline.select(".ng-lazyloading").attr("src")
                result.add(
                        Product(
                                title = title,
                                currentPrice = currentPrice.toBigDecimal(),
                                oldPrice = oldPrice.toBigDecimal(),
                                imgLink = imgLink
                        )
                )
            }
        }
        return result
    }

    fun imitateWebClient(productType: ProductType, productLink: String): Document {
        val options = ChromeOptions()
        options.addArguments("--headless")
        options.addArguments("--disable-gpu")
        WebDriverManager.chromedriver().setup()
        val driver = ChromeDriver(options)
        driver.get("https://rozetka.com.ua/ua/$productLink/c80004/sort=action/")
        return Jsoup.parse(driver.pageSource)
    }
}
