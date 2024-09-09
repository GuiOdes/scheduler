package com.guiodes.scheduler.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "executed_job")
class ExecutedJobEntity(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val scheduleReference: String,
    val status: String,
    val executionDate: LocalDateTime
)
