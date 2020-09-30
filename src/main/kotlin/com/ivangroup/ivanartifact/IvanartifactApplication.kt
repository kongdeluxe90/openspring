package com.ivangroup.ivanartifact

import com.ivangroup.mongodb.Connection
import org.json.JSONObject
import org.springframework.boot.SpringApplication

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@SpringBootApplication
@RestController
class IvanartifactApplication {
	//CORS Gedöhns
	@Configuration
	class CorsConfig {
		@Bean
		fun corsConfigurer(): WebMvcConfigurer {
			return object : WebMvcConfigurerAdapter() {
				override fun addCorsMappings(registry: CorsRegistry) {
					registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("*")
							.allowedHeaders("*")
				}
			}
		}
	}

	@GetMapping("/hello")
	fun hello(@RequestParam(value = "name", defaultValue = "World") name: String?): String {
		return String.format("Hello %s!", name)
	}

	@GetMapping("/getDB")
	fun getDB(): String {
		val mongoConUrl = System.getenv("mongoUrl")
		var returnstring = ""
		var returnJson = JSONObject()
		var dbs = Connection.showDBs()
		/*if(dbs != {}) {
			//returnstring =  dbs.toString()
		}else{
			//returnstring = "Sorry no DB found under " + mongoConUrl
		}*/
		returnJson = dbs
		println(dbs.toString())
		//return returnstring
		return returnJson.toString()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(IvanartifactApplication::class.java, *args)
		}
	}
}