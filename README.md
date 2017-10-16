[![Build status](https://api.travis-ci.org/btlines/play-json-refined.svg?branch=master)](https://travis-ci.org/btlines/play-json-refined)
[![codecov](https://codecov.io/gh/btlines/play-json-refined/branch/master/graph/badge.svg)](https://codecov.io/gh/btlines/play-json-refined)
[![License](https://img.shields.io/:license-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![Download](https://api.bintray.com/packages/beyondthelines/maven/play-json-refined/images/download.svg) ](https://bintray.com/beyondthelines/maven/play-json-refined/_latestVersion)

# Play JSON Refined

A tiny library providing Json formats for [refined](https://github.com/fthomas/refined) types.

## Context

Refined types come in handy to limit the valid values accepted in a function. However as values are often not available at compile time you need to validate them as soon as they enter your application.

In case of JSON inputs we need a format to convert from JSON to a refined type. (e.g. We want to read a non empty list or a positive integer directly from JSON).

## Setup

In order to use play-json-refined you need to add the following lines to your `build.sbt`:

```scala
resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

libraryDependencies += "beyondthelines" %% "play-json-refined" % "0.0.2"
```

## Dependencies

Play JSON Refined has only 2 dependencies: [Play-json](https://github.com/playframework/play-json) and [Refined](https://github.com/fthomas/refined).

## Usage

In order to use Play JSON with refined types you need to import the following:

```scala
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import play.api.libs.json._ 
import play.json.refined._
```

Importing play.json.refined._ adds implicit definitions to derive Json formats for refined types.

## Example

Let's take a basic example to illustrate the usage:

```scala
// import the refined that you need
// here we use numeric and collection
import eu.timepit.refined.collection._
import eu.timepit.refined.numeric._

type PosInt = Int Refined Positive
type NonEmptyString = String Refined NonEmpty

final case class Data(
  i: PosInt,
  s: NonEmptyString
)

implicit val dataFormat: OFormat[Data] =
  Json.format[Data]

val data = Data(1, "a")
// convert to JSON
val json = Json.toJson(data)
// convert from JSON
val parsed = json.as[Data]
```

