package reprator.wipro.factlist.util

import java.io.IOException
import java.io.InputStreamReader

object FileReader {
    fun readTestResourceFile(fileName: String): String {
        try {
            return InputStreamReader(
                this.javaClass.classLoader!!.getResourceAsStream("api-response/$fileName"), "UTF-8"
            ).use {
                it.readText()
            }
        } catch (e: IOException) {
            throw e
        }
    }
}
