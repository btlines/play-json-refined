package play.json

import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import play.api.libs.json.{JsError, JsSuccess, Reads, Writes}
import play.api.libs.functional.syntax._

package object refined {

  implicit def refinedReads[T, P](
    implicit reads: Reads[T], validate: Validate[T, P]
  ): Reads[T Refined P] =
    Reads[T Refined P] { json =>
      reads
        .reads(json)
        .flatMap { t: T =>
          refineV[P](t) match {
            case Left(error) => JsError(error)
            case  Right(value) => JsSuccess(value)
          }
        }
    }

  implicit def refinedWrites[T, P](
    implicit writes: Writes[T]
  ): Writes[T Refined P] =
    writes.contramap(_.value)

}
