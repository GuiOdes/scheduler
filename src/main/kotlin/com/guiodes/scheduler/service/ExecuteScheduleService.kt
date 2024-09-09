package com.guiodes.scheduler.service

import com.guiodes.scheduler.controller.request.CreateScheduleRequest
import com.guiodes.scheduler.entity.ExecutedJobEntity
import com.guiodes.scheduler.entity.ScheduledJobEntity
import com.guiodes.scheduler.repository.ExecutedJobRepository
import com.guiodes.scheduler.repository.ScheduledJobRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.scheduling.support.CronExpression
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicInteger

@Service
class ExecuteScheduleService(
    private val scheduledJobService: ScheduledJobRepository,
    private val executedJobService: ExecutedJobRepository
) {
    private val atomicInteger = AtomicInteger(0)

    fun createScheduledJob(createScheduleRequest: CreateScheduleRequest) {
        if (CronExpression.isValidExpression(createScheduleRequest.cron).not()) {
            throw IllegalArgumentException("Invalid cron expression")
        }

        scheduledJobService.save(
            ScheduledJobEntity(
                name = createScheduleRequest.name,
                cron = createScheduleRequest.cron,
                nextExecutionAt = createScheduleRequest.cron.getNextExecution()
            )
        )
    }

    @Scheduled(cron = "* * * * * *")
    fun counter() {
        println("===> Counter ${atomicInteger.getAndIncrement()} <===")
    }

    @Scheduled(cron = "*/10 * * * * *")
    fun execute() {
        atomicInteger.set(0)

        println("===> Executing scheduled jobs ${LocalDateTime.now()} <===")
        val jobs = scheduledJobService.findAllByNextExecutionAtBefore(
            LocalDateTime.now()
        )

        jobs.forEach { job ->
            println("===> Executing job ${job.name} <===")
            runCatching {
                println(job.name)
            }.onSuccess {
                val nextExecution = job.cron.getNextExecution()

                scheduledJobService.save(job.copy(nextExecutionAt = nextExecution))

                executedJobService.save(
                    ExecutedJobEntity(
                        name = job.name,
                        status = "SUCCESS",
                        lastExecutionDate = LocalDateTime.now()
                    )
                )
            }.onFailure {
                println("Error: ${it.message}")
                executedJobService.save(
                    ExecutedJobEntity(
                        name = job.name,
                        status = "ERROR",
                        lastExecutionDate = LocalDateTime.now()
                    )
                )
            }
        }
    }

    fun String.getNextExecution(): LocalDateTime {
        return CronExpression.parse(this).next(LocalDateTime.now())!!
    }
}