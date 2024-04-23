package ru.itmo.reporter.model

import java.time.OffsetDateTime

data class ReportTask(
    private val id: Long,
    private val event: ScheduleHistory,
    private val processedAt: OffsetDateTime? = null,
)
