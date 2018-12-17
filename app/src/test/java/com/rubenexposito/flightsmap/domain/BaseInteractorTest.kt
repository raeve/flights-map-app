package com.rubenexposito.flightsmap.domain

import java.io.File

open class BaseInteractorTest {
    fun getResource(fileName: String): File {
        val loader = ClassLoader.getSystemClassLoader()
        val resource = loader.getResource(fileName)
        return File(resource.path)
    }
}