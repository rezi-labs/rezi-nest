package org.lunchtime.nest.endpoints

import io.github.oshai.kotlinlogging.KotlinLogging
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.lunchtime.nest.AppConfig
import org.lunchtime.nest.llm.gen

private val log = KotlinLogging.logger { "Tasks" }

class TasksEndpoint(
    private val appConfig: AppConfig,
) : HttpHandler {
    override fun invoke(req: Request): Response {
        val p = promptRequestLens(req).prompt
        val a = gen(p, appConfig.gem)
        if (a == null) return Response(Status.OK)
        log.info { "tasks: $a" }
        return Response(Status.OK).body(a)
    }
}
