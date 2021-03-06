package fr.xebicon

import fr.xebicon.extension.save
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import java.time.LocalDate


data class Speaker(val name: String)

data class Event(val title: String, val description: String, val speakers: List<Speaker>, val date: LocalDate)

val amaze = Speaker("Amaze")

val events = listOf(
  Event("Keynote", "Amazing Keynote from an amazing speaker", listOf(amaze), LocalDate.of(2018, 11, 20))
)

fun Application.main() {
  install(DefaultHeaders)
  install(CallLogging)
  install(ContentNegotiation) {
    gson {}
  }
  routing {
    get("/") {
      call.respond("OK")
    }
    get("/events") {
      call.respond(events)
    }
    post("/events") {
      val event = call.receive<Event>()

//      val keyFactory = service.newKeyFactory()
//      val key = keyFactory.setKind("events").newKey()
//      val dsEvent = FullEntity.newBuilder(key)
//        .set("title", event.title)
//        .build()

      event.save()

      call.respond("OK")
    }
  }
}
