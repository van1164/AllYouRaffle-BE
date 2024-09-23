package com.van1164.lottoissofar.common.exception

import org.springframework.http.HttpStatus


enum class ErrorCode (val status:Int, val code:String, val message:String, private var expression: ((Any) -> String)? = null) {
    // Common
    BAD_REQUEST(400, "C001", "요청 파라미터 혹은 요청 바디의 값을 다시 확인하세요."),
    INTERNAL_SERVER_ERROR(500, "C002", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "C003", "유효하지 않은 입력입니다."),
    NOT_FOUND(404, "C004", "Not Found", {id -> "not found id : $id"}),
    DATETIME_INVALID(400, "C005", "유효하지 않은 날짜입니다"),
    MESSAGE_SEND_FAIL(400, "C006", "메시지 전송 실패"),

    // Raffle
    RAFFLE_NOT_FOUND(404, "R001", "Raffle을 찾을 수 없습니다."),
    RAFFLE_ALREADY_INACTIVE(409, "R002", "이미 완료된 Raffle입니다. 새로운 Raffle에 참가해주세요."),
    RAFFLE_MAX_CAPACITY_REACHED(409, "R003", "필요한 인원이 모두 채워져 Raffle 참여가 마감되었습니다. 새로운 Raffle에 참가해주세요."),
    RAFFLE_PURCHASE_LOCK_TIMEOUT(408, "R004", "Raffle 결제 과정에서 시간 초과가 발생했습니다."),
    RAFFLE_INSUFFICIENT_TICKETS(410, "R005", "보유 티켓이 부족합니다."),
    RAFFLE_PURCHASE_LIMIT_EXCEEDED(409, "R006", "이 래플의 구매가능 횟수를 초과했습니다."),

    // Item
    ITEM_NOT_FOUND(404, "I001", "아이템의 ID를 찾을 수 없습니다."),

    // Auth
    USER_NOT_FOUND(404, "A001", "사용자를 찾을 수 없습니다."),
    DUPLICATED_PHONE_NUMBER(409, "A002", "이미 등록된 전화번호입니다."),

    // Oauth
    SOCIAL_EMAIL_LOAD_FAIL(400, "O001", "소셜 로그인에서 이메일을 불러올 수 없습니다."),
    SOCIAL_NAME_LOAD_FAIL(400, "O002", "소셜 로그인에서 이름을 불러올 수 없습니다."),

    // User
    USER_TICKET_LOCK_TIMEOUT(408, "U001", "사용자 응모권 추가에 실패했습니다.", {userId -> "사용자 ID : $userId | 응모권 추가에 실패하였습니다."});

    private var messageArgument: Any? = null

    fun setMessageWith(arg: Any?): ErrorCode {
        this.messageArgument = arg
        return this
    }

    fun getFormattedMessage(): String {
        return if (expression != null && messageArgument != null) {
            expression!!.invoke(messageArgument!!)
        } else {
            message
        }
    }
}
