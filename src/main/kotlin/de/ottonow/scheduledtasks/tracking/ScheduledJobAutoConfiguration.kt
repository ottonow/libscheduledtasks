package de.ottonow.scheduledtasks.tracking

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.config.ScheduledTaskHolder

@Configuration
open class ScheduledJobAutoConfiguration @Autowired constructor(
    private val scheduledTaskHolder: ScheduledTaskHolder
) {

    @Bean
    open fun scheduledJobController(scheduledJobTracker: ScheduledJobTracker): ScheduledJobController {
        return ScheduledJobController(scheduledJobTracker)
    }

    @Bean
    open fun scheduledJobTracker(): ScheduledJobTracker {
        return ScheduledJobTracker(scheduledTaskHolder)
    }

    @Bean
    open fun scheduledJobTrackingAspect(scheduledJobTracker: ScheduledJobTracker) : ScheduledJobTrackingAspect {
        return ScheduledJobTrackingAspect(scheduledJobTracker)
    }

}