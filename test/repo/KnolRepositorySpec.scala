package repo

import org.specs2.mutable.Specification
import play.api.Application
import play.api.test.WithApplication
import scala.concurrent.duration.Duration
import scala.concurrent.Await

class KnolRepositorySpec extends Specification {

  import models._

  "Knol repository" should {

    def knolRepo(implicit app: Application) = {
      val app2KnolRepo = Application.instanceCache[KnolRepository]
      app2KnolRepo(app)
    }

    "insert a row" in new WithApplication {
      val knolId = Await.result(knolRepo.insert(Knol("", "", "")), Duration.Inf)
      knolId === 1
    }

  }
}
