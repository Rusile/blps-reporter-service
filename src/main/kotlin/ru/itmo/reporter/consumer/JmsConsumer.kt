package ru.itmo.reporter.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.jms.Session
import org.slf4j.LoggerFactory
import org.springframework.jms.annotation.JmsListener
import org.springframework.stereotype.Component
import ru.itmo.reporter.dao.ReportQueueDao
import ru.itmo.reporter.model.ScheduleHistory

@Component
class JmsConsumer(
    private val reportObjectMapper: ObjectMapper,
    private val reportQueueDao: ReportQueueDao,
) {

    @JmsListener(destination = "\${topic_name}", containerFactory = "jmsFactory")
    fun receiveMessage(
        message: String, session: Session
    ) {
        try {
            logger.info("Received message: {}", message)
            val historyLog = reportObjectMapper.readValue(message, ScheduleHistory::class.java)
            reportQueueDao.insert(historyLog)
            session.commit()
        } catch (e: Exception) {
            logger.error("Error while reading jms", e)
            session.rollback()
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(JmsConsumer::class.java)
    }
}
