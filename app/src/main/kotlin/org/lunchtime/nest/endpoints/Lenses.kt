package org.lunchtime.nest.endpoints

import org.http4k.core.Body
import org.http4k.format.Jackson.auto

data class Prompt(
    val prompt: String,
)

val promptRequestLens = Body.auto<Prompt>().toLens()
