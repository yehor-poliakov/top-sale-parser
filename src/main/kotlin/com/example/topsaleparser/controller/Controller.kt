package com.example.topsaleparser.controller

import com.example.topsaleparser.domain.Product
import com.example.topsaleparser.domain.ProductType
import com.example.topsaleparser.service.ProductService
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class Controller(
        val service: ProductService
) {
    @Value("\${client.address}")
    lateinit var frontEndIp: String

    @CrossOrigin("http://localhost:8000")
    @GetMapping("/notebook")
    fun getNotebooks(request: HttpServletRequest): List<Product> {
        validateIpAddress(request)
        return service.getProducts(ProductType.NOTEBOOK)
    }

    @CrossOrigin("http://localhost:8000")
    @GetMapping("/phone")
    fun getPhones(request: HttpServletRequest): List<Product> {
        validateIpAddress(request)
        return service.getProducts(ProductType.PHONE)
    }

    @CrossOrigin("http://localhost:8000")
    @GetMapping("/tvset")
    fun getTvsets(request: HttpServletRequest): List<Product> {
        validateIpAddress(request)
        return service.getProducts(ProductType.TV_SET)
    }

    fun validateIpAddress(httpRequest: HttpServletRequest) {
        if (httpRequest.remoteAddr != frontEndIp) throw Exception()
    }
}
