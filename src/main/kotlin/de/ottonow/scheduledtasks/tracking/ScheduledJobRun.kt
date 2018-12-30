package de.ottonow.scheduledtasks.tracking

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Duration
import java.time.Instant
import java.util.*

@JsonInclude(value = JsonInclude.Include.NON_NULL)
data class ScheduledJobRun(
    val uuid: UUID,
    val startedAt: Instant,
    val endedAt: Instant? = null,
    val exception: ExceptionInfo? = null
) {

    @JsonProperty
    fun duration(): Long? {
        if (endedAt == null)
            return null

        return Duration.between(startedAt, endedAt).toMillis()
    }

}

data class ExceptionInfo(
    val message: String,
    val stacktrace: String
)