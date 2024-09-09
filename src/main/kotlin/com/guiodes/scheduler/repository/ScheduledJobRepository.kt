package com.guiodes.scheduler.repository

import com.guiodes.scheduler.entity.ScheduledJobEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ScheduledJobRepository: JpaRepository<ScheduledJobEntity, Long> {
    fun findAllByNextExecutionAtBefore(date: LocalDateTime): List<ScheduledJobEntity>
}