package ru.itmo.reporter.model

import java.time.OffsetDateTime

data class ReportTask(
    val id: Long,
    val event: ScheduleHistory,
    val processedAt: OffsetDateTime? = null,
)
