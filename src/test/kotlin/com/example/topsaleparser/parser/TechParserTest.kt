package com.example.topsaleparser.parser

import com.example.topsaleparser.domain.ProductType
import org.junit.jupiter.api.Test

class TechParserTest {

    companion object {
        private val NOTEBOOKS = ProductType.NOTEBOOK
        private val PHONES = ProductType.PHONE
        private val TV_SETS = ProductType.TV_SET
    }

    private val testInstance = TechParser()

    @Test
    fun `should return a list of notebooks`() {


        val result = testInstance.parseProduct(NOTEBOOKS)

    }
}