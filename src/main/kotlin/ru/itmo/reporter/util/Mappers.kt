package ru.itmo.reporter.util

import ru.itmo.reporter.generated.jooq.tables.records.ReportQueueRecord
import ru.itmo.reporter.model.ReportTask
import ru.itmo.reporter.model.ScheduleHistory

object Mappers {
    fun ReportQueueRecord.toModel(
        deserializer: (String) -> ScheduleHistory
    ): ReportTask {
        return ReportTask(
            id = this.id,
            event = deserializer.invoke(this.event.data()),
            processedAt = this.processedAt
        )
    }
}