package ru.itmo.reporter.task

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.itmo.reporter.dao.ReportQueueDao
import java.io.File
import java.nio.file.Paths
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class SimpleJsonReportCreationTask(
    private val reportQueueDao: ReportQueueDao,
    private val reportObjectMapper: ObjectMapper,
) {

    @Transactional
    fun createReport() {
        val tasks = reportQueueDao.getAllUnprocessed(true)

        for (task in tasks) {
            val reportJson = reportObjectMapper.writeValueAsString(task.event)

            val reportDate = LocalDateTime.now().format(formatter)
            val fileName = "report-$reportDate-${task.id}.json"
            val directory = Paths.get("src/main/resources/reports").toFile()

            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            file.writeText(reportJson)

        }
        reportQueueDao.markAsProcessed(tasks.map { it.id })
    }

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }
}