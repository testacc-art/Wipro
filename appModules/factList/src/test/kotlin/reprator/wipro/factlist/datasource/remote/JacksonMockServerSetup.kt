package reprator.wipro.factlist.datasource.remote

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import retrofit2.converter.jackson.JacksonConverterFactory

fun createJacksonMapper() = jacksonObjectMapper().apply {
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
    disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    registerModule(KotlinModule())
}

fun createJacksonConverterFactory(): JacksonConverterFactory {
    return JacksonConverterFactory.create(createJacksonMapper())
}