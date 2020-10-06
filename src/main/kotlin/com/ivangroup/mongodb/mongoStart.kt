package com.ivangroup.mongodb

import com.mongodb.client.MongoClients
import org.bson.Document
import java.util.*
import java.util.function.Consumer
import java.util.logging.Level
import java.util.logging.Logger
import org.json.JSONObject
import org.json.JSONArray

object Connection {
    @JvmStatic
    fun showDBs(): JSONObject {
        Logger.getLogger("org.mongodb.driver").level = Level.WARNING
        val mongoConUrl = System.getenv("mongoUrl")
        println(mongoConUrl)
        var connectionString = "mongodb://127.0.0.1:27017"

        if(mongoConUrl != null) {
            connectionString = mongoConUrl
        }

        //var returnDocument: Document = Document()
        var returnJson: JSONObject = JSONObject()
        var databases: List<Document> = emptyList()
        try {
            MongoClients.create(connectionString).use { mongoClient ->
                databases = mongoClient.listDatabases().into(ArrayList())
                //databases.forEach(Consumer { db: Document -> returnDocument = db })
                //databases.forEach(Consumer { db: Document -> println(db.toJson()) })
                databases.forEach(Consumer { db: Document ->
                    var aDB: JSONObject = JSONObject()
                    aDB.put("name", db.get("name"))
                    aDB.put("sizeOnDisk", db.get("sizeOnDisk"))
                    returnJson.put(db.get("name").toString(), aDB) })
                println(returnJson)
            }
        }catch(e: Exception){
            println(e)
        }
        //return databases
        return returnJson
    }

    fun postInDBs(insertData: String) {
        Logger.getLogger("org.mongodb.driver").level = Level.WARNING
        val mongoConUrl = System.getenv("mongoUrl")
        println(mongoConUrl)
        var connectionString = "mongodb://127.0.0.1:27017"

        if(mongoConUrl != null) {
            connectionString = mongoConUrl
        }

        try {
            MongoClients.create(connectionString).use { mongoClient ->
                mongoClient.getDatabase("ivandb").getCollection("starwars").insertOne(Document.parse(insertData))
                println(insertData)
                println(Document.parse(insertData))
            }
        }catch(e: Exception){
                println(e)
            }
        }
}
