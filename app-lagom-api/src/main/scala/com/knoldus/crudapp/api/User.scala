package com.knoldus.crudapp.api


import play.api.libs.json.{Format, Json}

import scala.collection.mutable.Map

case class User(id: Int, name: String)

object User {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[User] = Json.format[User]
}



