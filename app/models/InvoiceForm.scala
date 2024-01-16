package models

import play.api.libs.json.Json

case class InvoiceForm(
                        invoiceNumber: String,
                        commodity: String,
                        consumption: Int,
                        totalPrice: BigDecimal,
                      )

object InvoiceForm {
  implicit def invoiceFormPlayJsonReads = Json.reads[InvoiceForm]
}
