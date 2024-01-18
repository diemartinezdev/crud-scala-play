package models

import play.api.libs.json.{Json, OFormat}

case class Site(
               id: String,
               short_name: String,
               vat_number: Option[String]
               )

object Site {
  implicit val format: OFormat[Site] = Json.format[Site]
}