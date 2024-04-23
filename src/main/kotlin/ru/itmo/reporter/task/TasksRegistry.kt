package ru.itmo.reporter.task

import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.TimeUnit

@Configuration
@EnableScheduling
class TasksRegistry(
    private val task: SimpleJsonReportCreationTask
) {

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    fun reportCreationTask() {
        task.createReport()
    }
}