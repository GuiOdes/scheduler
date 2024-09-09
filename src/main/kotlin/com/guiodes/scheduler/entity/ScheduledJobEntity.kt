package com.guiodes.scheduler.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "scheduled_job")
data class ScheduledJobEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val name: String,
    val cron: String,
    val nextExecutionAt: LocalDateTime
)
