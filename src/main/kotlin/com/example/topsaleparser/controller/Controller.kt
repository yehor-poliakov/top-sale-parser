package com.example.topsaleparser.controller

import com.example.topsaleparser.domain.ProductType
import com.example.topsaleparser.service.ProductService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(
    val service: ProductService
) {
    @GetMapping("/notebook")
    fun getNotebooks() = service.getProducts(ProductType.NOTEBOOK)

    @GetMapping("/phone")
    fun getPhones() = service.getProducts(ProductType.PHONE)

    @GetMapping("/tvset")
    fun getTvsets() = service.getProducts(ProductType.TV_SET)
}
