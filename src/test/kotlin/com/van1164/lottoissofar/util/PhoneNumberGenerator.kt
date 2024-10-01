package com.van1164.lottoissofar.util

import java.util.*
import kotlin.math.pow

class PhoneNumberGenerator {
    companion object {
        fun generatePhoneNumber(): String {
            return buildString {
                append("010")
                append(PhoneNumberGenerator().generateRandomNumber(4))
                append(PhoneNumberGenerator().generateRandomNumber(4))
            }
        }
    }

    // 지정된 자릿수의 랜덤 숫자를 생성하는 메소드
    internal fun generateRandomNumber(length: Int): String {
        val random = Random()
        val number = random.nextInt(10.0.pow(length).toInt())
        return String.format("%04d", number) // 4자리로 맞춤
    }

}