package com.example.topsaleparser.service

import com.example.topsaleparser.domain.Product
import com.example.topsaleparser.domain.ProductType
import com.example.topsaleparser.parser.TechParser
import org.springframework.stereotype.Service

@Service
class ProductService(
    val techParser: TechParser
) {
    fun getProducts(productType: ProductType): List<Product> = techParser.parseProduct(productType)
}
