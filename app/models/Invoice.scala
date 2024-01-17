package models

import play.api.libs.json._


case class Invoice(
                    id: Option[Long],
                    invoice_number: Option[String],
                    site_id: String,
                    commodity: String,
                    consumption: BigDecimal,
                    total_price: BigDecimal
                  )

object Invoice {
  implicit val format: OFormat[Invoice] = Json.format[Invoice]
}
