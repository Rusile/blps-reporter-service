package ru.itmo.reporter.dao

import ru.itmo.reporter.model.ReportTask
import ru.itmo.reporter.model.ScheduleHistory

interface ReportQueueDao {

    fun insert(event: ScheduleHistory)

    fun getAllUnprocessed(forUpdate: Boolean): List<ReportTask>

    fun markAsProcessed(ids: List<Long>)
}