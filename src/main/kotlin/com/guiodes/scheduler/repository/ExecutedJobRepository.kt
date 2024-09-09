package com.guiodes.scheduler.repository

import com.guiodes.scheduler.entity.ExecutedJobEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ExecutedJobRepository: JpaRepository<ExecutedJobEntity, Long>