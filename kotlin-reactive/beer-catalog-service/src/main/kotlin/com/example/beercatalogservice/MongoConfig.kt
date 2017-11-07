package com.example.beercatalogservice

import java.io.IOException

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.*

@Configuration
class MongoConfig {
    @Bean
    @Throws(IOException::class)
    fun mongoTemplate(): MongoTemplate {
        val mongo = EmbeddedMongoFactoryBean()
        mongo.setBindIp(MONGO_DB_URL)
        val mongoClient = mongo.getObject()
        return MongoTemplate(mongoClient, MONGO_DB_NAME)
    }

    companion object {
        private val MONGO_DB_URL = "localhost"
        private val MONGO_DB_NAME = "embeded_db"
    }
}