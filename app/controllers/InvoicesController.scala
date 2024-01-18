package controllers

import play.api.libs.json.Json
import play.api.mvc._
import repositories.InvoiceRepository

import scala.concurrent.ExecutionContext
import javax.inject._

@Singleton
class InvoicesController @Inject()(cc: ControllerComponents, invoiceRepository: InvoiceRepository
                                  )(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def getAllInvoices: Action[AnyContent] = Action.async { implicit request =>
    invoiceRepository.getAllInvoices.map { invoice =>
      //      Ok(views.html.invoice(invoice))
      Ok(Json.toJson(invoice))
    }
  }

  def getInvoicesWithSites: Action[AnyContent] = Action.async { implicit request =>
    invoiceRepository.getInvoicesWithSites.map { invoice =>
      Ok(Json.toJson(invoice))
    }
  }
}