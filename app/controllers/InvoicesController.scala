package controllers

import models.Invoice
import play.api.libs.json.Json
import play.api.mvc._


import java.util.UUID
import javax.inject.{Inject, Singleton}

@Singleton
class InvoicesController @Inject()
  (val controllerComponents: ControllerComponents
  ) extends BaseController {

  val store = collection.mutable.Map.empty[String, models.Invoice]


  store += "w2" -> models.Invoice("w2", "abc123", "ELEC", 1230, 2340)
  store += "w3" -> models.Invoice("w3", "abc234", "GAS", 2344, 3453)
  store += "w4" -> models.Invoice("w4", "abc654", "ELEC", 2244, 32353)

  implicit val invoiceListJson = Json.format[Invoice]

  def get = Action {
    val models = store.values
    val json = Json.toJson(models)

    Ok(json)
  }

  def getById(id: String) = Action {
    store.get(id)
      .map(Json.toJson[models.Invoice])
      .fold(NotFound(s"Invoice not found: ${id}"))(Ok(_))
  }

  val missingContentType = UnprocessableEntity("Expected 'Content-Type' set to 'application/json'")
  val missingInvoiceForm = UnprocessableEntity("Expected content to contain a invoice form")

  def post = Action { req =>
    req.body.asJson
      .toRight(missingContentType)
      .flatMap(_.asOpt[models.InvoiceForm].toRight(missingInvoiceForm))
      .map { form =>
        val id = UUID.randomUUID().toString
        val model = models.Invoice(id, form.invoiceNumber, form.commodity, form.consumption, form.totalPrice)

        store.update(id, model)
        val json = Json.toJson(model)
        Created(json)
      }
      .merge
  }

  def putById(id: String) = Action { req =>
    req.body.asJson
      .toRight(missingContentType)
      .flatMap(_.asOpt[models.InvoiceForm].toRight(missingInvoiceForm))
      .flatMap { form =>
        store.get(id)
          .toRight(NotFound(s"Invoice not found: ${id}"))
          .map((_, form))
      }
      .map { case (found, form) =>
        val model = models.Invoice(found.id, form.invoiceNumber, form.commodity, form.consumption, form.totalPrice)
        store.update(found.id, model)

        NoContent
      }
      .merge
  }

  def deleteById(id: String) = Action {
    store.get(id)
      .fold(NotFound(s"Invoice not found: ${id}")) { _ =>
        store.remove(id)

        NoContent
      }
  }
}
