package org.lunchtime.nest.endpoints

import io.github.oshai.kotlinlogging.KotlinLogging
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.lunchtime.nest.AppConfig
import org.lunchtime.nest.llm.genAction

private val log = KotlinLogging.logger { "Tasks" }

class ActionDecisionEndpoint(
    private val appConfig: AppConfig,
) : HttpHandler {
    override fun invoke(req: Request): Response {
        val l = promptWithActionsRequestLens(req)
        val p = l.prompt
        val availableActions = l.actions
        val a = genAction(p, availableActions, appConfig.gem)
        if (a == null) return Response(Status.OK)
        log.info { "actions: $a" }
        return Response(Status.OK).body(a)
    }
}
