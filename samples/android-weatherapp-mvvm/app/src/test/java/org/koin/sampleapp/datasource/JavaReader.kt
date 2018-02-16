package org.koin.sampleapp.datasource

import org.koin.sampleapp.repository.local.BaseReader
import java.io.File

/**
 * Json Java File reader
 */
class JavaReader : BaseReader() {

    fun basePath(): String? {
        val classLoader: ClassLoader = JavaReader::class.java.classLoader
        val path: String? = classLoader.getResource("json/")?.path
        return path
    }

    override fun getAllFiles(): List<String> {
        return basePath()?.let {
            val list = File(it).list()
            list.toList()
        }!!
    }

    override fun readJsonFile(jsonFile: String): String = File("${basePath()}/$jsonFile").readLines().joinToString(separator = "\n")
}