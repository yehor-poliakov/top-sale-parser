package com.example.topsaleparser.controller

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ErrorController {

        @ExceptionHandler(value = [(Exception::class)])
        fun handleUserAlreadyExists(ex: Exception, request: HttpServletRequest, response: HttpServletResponse){
            response.status = 404
        }
}