package play.json.refined

import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import eu.timepit.refined.collection.NonEmpty
import eu.timepit.refined.numeric.Positive
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.prop.GeneratorDrivenPropertyChecks._
import play.api.libs.json.{JsError, JsSuccess, Json, OFormat}
import org.scalacheck.{Arbitrary, Gen}

import ArbitraryRefined._

object PlayJsonRefinedSpec {
  type NonEmptyList[A] = List[A] Refined NonEmpty
  type NonEmptyString = String Refined NonEmpty
  type PosInt = Int Refined Positive

  final case class Data(
    s: NonEmptyString,
    n: PosInt,
    xs: NonEmptyList[Int]
  )

  implicit val dataArbitrary: Arbitrary[Data] =
    Arbitrary(Gen.resultOf(Data))
  
  implicit val dataFormat: OFormat[Data] = Json.format[Data]
}

class PlayJsonRefinedSpec extends WordSpec with Matchers {

  import PlayJsonRefinedSpec._

  "play.json.refined" should {
    "read non empty list" in forAll { list: List[String] =>
      val json = Json.toJson(list)
      json.validate[NonEmptyList[String]] match {
        case JsSuccess(nel, _) => (nel: List[String]) shouldBe list
        case JsError(_) => list shouldBe empty
      }
    }
    "read non empty string" in forAll { s: String =>
      val json = Json.toJson(s)
      json.validate[NonEmptyString] match {
        case JsSuccess(t, _) => (t: String) shouldBe s
        case JsError(_) => s shouldBe empty
      }
    }
    "read positive ints" in forAll { n: Int =>
      val json = Json.toJson(n)
      json.validate[PosInt] match {
        case JsSuccess(m, _) => (m: Int) shouldBe n
        case JsError(_) => n shouldBe <= (0)
      }
    }

    "write non empty list" in forAll { list: NonEmptyList[String] =>
      val json = Json.toJson(list)
      json.as[List[String]] shouldBe (list: List[String])
    }
    "write non empty string" in forAll { s: NonEmptyString =>
      val json = Json.toJson(s)
      json.as[String] shouldBe (s: String)
    }
    "write positive ints" in forAll { n: PosInt =>
      val json = Json.toJson(n)
      json.as[Int] shouldBe (n: Int)
    }

    "format case classes with refined members" in forAll { data: Data =>
      val json = Json.toJson(data)
      json.as[Data] shouldBe data
    }
  }

}
