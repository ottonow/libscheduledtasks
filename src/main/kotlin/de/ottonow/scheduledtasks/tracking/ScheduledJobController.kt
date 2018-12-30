package de.ottonow.scheduledtasks.tracking

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${tracking.scheduled-jobs.path:/scheduled-jobs}")
@CrossOrigin
open class ScheduledJobController @Autowired constructor(
    private val scheduledJobTracker: ScheduledJobTracker
) {

    @RequestMapping(method = [RequestMethod.GET])
    open fun getScheduledJobs(): Set<TrackedScheduledJob> {
        return scheduledJobTracker.trackedJobs
    }

    @RequestMapping(value = ["{className}"], method = [RequestMethod.GET])
    open fun getScheduledJobsPerClass(@PathVariable(name = "className") className: String): List<TrackedScheduledJob> {
        return scheduledJobTracker.findJobsByClass(className)
    }

    @RequestMapping(value = ["{className}/{methodName}"], method = [RequestMethod.GET])
    open fun getScheduledJob(
        @PathVariable(name = "className") className: String,
        @PathVariable(name = "methodName") methodName: String
    ): ResponseEntity<Any> {
        val scheduledJob = scheduledJobTracker.findJobByClassAndMethod(className, methodName)
        if (scheduledJob == null)
            return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity.ok(scheduledJob)
    }

    @RequestMapping(value = ["{className}/{methodName}/{uuid}"], method = [RequestMethod.GET])
    open fun getScheduledJobRun(
        @PathVariable(name = "className") className: String,
        @PathVariable(name = "methodName") methodName: String,
        @PathVariable(name = "uuid") uuid: String
    ): ResponseEntity<Any> {
        val scheduledJobRun = scheduledJobTracker.findRunByUuid(className, methodName, uuid)
        if (scheduledJobRun == null)
            return ResponseEntity(HttpStatus.NOT_FOUND)

        return ResponseEntity.ok(scheduledJobRun)
    }

    @RequestMapping(
        value = ["{className}/{methodName}"],
        method = [RequestMethod.POST]
    )
    open fun triggerJob(
        @PathVariable(name = "className") className: String,
        @PathVariable(name = "methodName") methodName: String
    ) {
        try {
            scheduledJobTracker.triggerJob(className, methodName)
        } catch (exc: Exception) {
            // Don't care if job fails, since fails are visible aswell
        }
    }

}