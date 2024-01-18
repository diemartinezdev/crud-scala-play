package controllers

import play.api.libs.json.Json
import play.api.mvc._
import repositories.SiteRepository

import scala.concurrent.ExecutionContext
import javax.inject._

@Singleton
class SitesController @Inject()(cc: ControllerComponents, siteRepository: SiteRepository
                               )(implicit ec: ExecutionContext) extends AbstractController(cc) {
  def getAllSites: Action[AnyContent] = Action.async { implicit request =>
    siteRepository.getAllSites.map { site =>
      Ok(Json.toJson(site))
    }
  }
}
