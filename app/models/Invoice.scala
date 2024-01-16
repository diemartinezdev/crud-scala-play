package models

import play.api.libs.json.Json

case class Invoice(
  id: String,
  invoiceNumber: String,
  commodity: String,
  consumption: Int,
  totalPrice: BigDecimal,
                  )

object Invoice {
  implicit def invoicePlayJsonWrites = Json.writes[Invoice]
}
