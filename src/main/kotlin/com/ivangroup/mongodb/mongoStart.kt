package com.ivangroup.mongodb

import com.mongodb.client.MongoClients
import org.bson.Document
import java.util.*
import java.util.function.Consumer
import java.util.logging.Level
import java.util.logging.Logger

object Connection {
    @JvmStatic
    fun showDBs(): List<Document> {
        Logger.getLogger("org.mongodb.driver").level = Level.WARNING
        val mongoConUrl = System.getenv("mongoUrl")
        println(mongoConUrl)
        var connectionString = "mongodb://127.0.0.1:27017"

        if(mongoConUrl != null) {
            connectionString = mongoConUrl
        }

        var returnDocument: Document = Document()
        var databases: List<Document> = emptyList()
        try {
            MongoClients.create(connectionString).use { mongoClient ->
                databases = mongoClient.listDatabases().into(ArrayList())
                databases.forEach(Consumer { db: Document -> returnDocument = db })
                databases.forEach(Consumer { db: Document -> println(db.toJson()) })
            }
        }catch(e: Exception){
            println(e)
        }
        return databases
    }
}
