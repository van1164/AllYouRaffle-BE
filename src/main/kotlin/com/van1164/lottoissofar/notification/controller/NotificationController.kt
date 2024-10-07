package com.van1164.lottoissofar.notification.controller

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.notification.NotificationResponseDto
import com.van1164.lottoissofar.notification.service.NotificationService
import com.van1164.lottoissofar.purchase_history.service.PurchaseHistoryService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/notification")
class NotificationController(
    private val notificationService: NotificationService,
) {

    @GetMapping("/test")
    fun test(
        @Parameter(hidden = true)
        user: User,
    ): Int {
        return user.tickets
    }

    @GetMapping("")
    fun getNotifications(
        @Parameter(hidden = true)
        user: User,
        @RequestParam(required = false, defaultValue = Long.MAX_VALUE.toString()) cursor: Long,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): Page<NotificationResponseDto> {
        return notificationService.getNotificationsByUserId(user.userId,cursor,size)
    }


}