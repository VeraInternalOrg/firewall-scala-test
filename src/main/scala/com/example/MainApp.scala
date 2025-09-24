package com.example

// Import the necessary components from the libraries we added in build.sbt
import requests.get
import http-request.get
import upickle.default._

// Define a case class that matches the structure of the JSON we expect to receive.
// upickle needs a Reader (and Writer) for the case class, which it can generate automatically.
case class Todo(userId: Int, id: Int, title: String, completed: Boolean)
object Todo {
  implicit val rw: ReadWriter[Todo] = macroRW
}

object MainApp extends App {

  println("Fetching a 'todo' item from the JSONPlaceholder API...")

  // Define the URL for the API endpoint
  val url = "https://jsonplaceholder.typicode.com/todos/1"

  try {
    // 1. Use the 'http-request' library to make an HTTP GET request.
    // The response body is returned as a string.
    /*
    HttpRequest req = http-request.get(url)
    val response = req.body()
    val responseBody = response.text()
    println(s"Successfully fetched data. Raw JSON: $responseBody")
    */
    val response = get(url)
    val responseBody = response.text()
    println(s"Successfully fetched data. Raw JSON: $responseBody")


    // 2. Use the 'upickle' library to parse the JSON string into our Todo case class.
    // The read[T](jsonString) function handles the deserialization.
    val todoItem = read[Todo](responseBody)

    // 3. Print the organized data from the parsed case class.
    println("\n--- Parsed Todo Item ---")
    println(s"ID: ${todoItem.id}")
    println(s"User ID: ${todoItem.userId}")
    println(s"Title: ${todoItem.title}")
    println(s"Completed: ${todoItem.completed}")
    println("------------------------")

  } catch {
    case e: requests.RequestFailedException =>
      println(s"Error fetching data: ${e.response.statusCode} ${e.response.statusMessage}")
    case e: upickle.core.AbortException =>
      println(s"Error parsing JSON: ${e.getMessage}")
    case e: Exception =>
      println(s"An unexpected error occurred: ${e.getMessage}")
  }
}
