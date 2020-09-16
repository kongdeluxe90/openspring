package com.ivangroup.ivanartifact

import com.ivangroup.mongodb.Connection

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication
@RestController
class IvanartifactApplication {
	@GetMapping("/hello")
	fun hello(@RequestParam(value = "name", defaultValue = "World") name: String?): String {
		return String.format("Hello %s!", name)
	}

	@GetMapping("/getDB")
	fun getDB(): String {
		val mongoConUrl = System.getenv("mongoUrl")
		var returnstring = ""
		var dbs = Connection.showDBs()
		if(dbs.size > 0) {
			returnstring =  dbs.toString()
		}else{
			returnstring = "Sorry no DB found under " + mongoConUrl
		}
		println(dbs.toString())
		return returnstring
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(IvanartifactApplication::class.java, *args)
		}
	}
}