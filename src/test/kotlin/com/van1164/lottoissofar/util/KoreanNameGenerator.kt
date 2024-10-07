package com.van1164.lottoissofar.util

import java.util.*

class KoreanNameGenerator {
    private val lastNames: Array<String> = arrayOf(
        "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "오", "한", "신", "서", "권", "황", "안", "송",
        "류", "홍"
    )

    private val firstNameSyllable: Array<String> = arrayOf(
        "가", "강", "건", "경", "고", "관", "광", "구", "권", "규", "기",
        "나", "남", "노", "다", "동", "두", "라", "려", "명", "민", "미",
        "바", "백", "범", "병", "보", "성", "소", "수", "승", "시", "아",
        "연", "영", "예", "오", "용", "우", "원", "윤", "은", "이", "재",
        "정", "제", "준", "지", "진", "찬", "채", "현", "혜", "호", "훈"
    )

    companion object {
        fun generateRandomKoreanName(): String {
            val random = Random()

            // 성 (한 글자) 랜덤 선택
            val lastName = KoreanNameGenerator().lastNames[random.nextInt(KoreanNameGenerator().lastNames.size)]

            // 이름 (두 글자) 랜덤 선택
            val firstName = (
                    KoreanNameGenerator().firstNameSyllable[random.nextInt(KoreanNameGenerator().firstNameSyllable.size)]
                            + KoreanNameGenerator().firstNameSyllable[random.nextInt(KoreanNameGenerator().firstNameSyllable.size)])

            // 완성된 이름 반환
            return lastName + firstName
        }
    }

}