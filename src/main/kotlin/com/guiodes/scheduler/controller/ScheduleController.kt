package com.guiodes.scheduler.controller

import com.guiodes.scheduler.controller.request.CreateScheduleRequest
import com.guiodes.scheduler.service.ExecuteScheduleService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schedule")
class ScheduleController(
    private val scheduleService: ExecuteScheduleService
) {

    @PostMapping
    fun createScheduledJob(@RequestBody createScheduleRequest: CreateScheduleRequest) {
        scheduleService.createScheduledJob(createScheduleRequest)
    }
}