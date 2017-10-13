package play.json.refined

import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import org.scalacheck.{Arbitrary, Gen}

object ArbitraryRefined {

  implicit def refinedArbitrary[T, P](
    implicit arbitrary: Arbitrary[T], validate: Validate[T, P]
  ): Arbitrary[T Refined P] =
    Arbitrary(
      arbitrary.arbitrary.flatMap { t: T =>
        refineV[P](t) match {
          case Right(value) => Gen.const(value)
          case _ => Gen.fail
        }
      }
    )

}
