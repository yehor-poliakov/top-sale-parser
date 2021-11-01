package com.example.topsaleparser.controller

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
    fun getNotebooks(request: HttpServletRequest) =
            if (request.remoteAddr == frontEndIp)
                service.getProducts(ProductType.NOTEBOOK)
            else throw Exception()


    @GetMapping("/phone")
    fun getPhones() = service.getProducts(ProductType.PHONE)

    @GetMapping("/tvset")
    fun getTvsets() = service.getProducts(ProductType.TV_SET)
}
