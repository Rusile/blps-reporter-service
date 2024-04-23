package ru.itmo.reporter.dao.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.jooq.DSLContext
import org.jooq.JSONB
import org.springframework.stereotype.Repository
import ru.itmo.reporter.dao.ReportQueueDao
import ru.itmo.reporter.generated.jooq.Tables.REPORT_QUEUE
import ru.itmo.reporter.model.ReportTask
import ru.itmo.reporter.model.ScheduleHistory
import ru.itmo.reporter.util.Mappers.toModel
import java.time.OffsetDateTime

@Repository
class ReportQueueDaoImpl(
    private val dslContext: DSLContext,
    private val reportObjectMapper: ObjectMapper,
) : ReportQueueDao {

    override fun insert(event: ScheduleHistory) {
        dslContext.insertInto(REPORT_QUEUE)
            .set(REPORT_QUEUE.EVENT, JSONB.valueOf(reportObjectMapper.writeValueAsString(event)))
            .execute()
    }

    override fun getAllUnprocessed(forUpdate: Boolean): List<ReportTask> {
        return dslContext.selectFrom(REPORT_QUEUE)
            .where(REPORT_QUEUE.PROCESSED_AT.isNull)
            .also {
                if (forUpdate) {
                    it.forUpdate()
                }
            }
            .toList()
            .map {
                it.toModel { event ->
                    reportObjectMapper.readValue(event, ScheduleHistory::class.java)
                }
            }
    }

    override fun markAsProcessed(ids: List<Long>) {
        dslContext.update(REPORT_QUEUE)
            .set(REPORT_QUEUE.PROCESSED_AT, OffsetDateTime.now())
            .where(REPORT_QUEUE.ID.`in`(ids))
            .execute()
    }
}