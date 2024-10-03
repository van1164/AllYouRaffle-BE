package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "review")
data class Review(
    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val user : User,

    @OneToOne(mappedBy = "review")
    val winnerHistory: WinnerHistory? = null,

    ) : BaseEntity() {

// 리뷰에 얽힌 좋아요. 가 있으면 좋을 거 같다.
    // 쿠팡을 보면 리뷰에 좋아요를 할 수 있고 도움되었다는 push 메시지가 업데이트 되는데
    // 이게 사용자 간의 커뮤니티 기능 역할을 하기 때문에 좋다고 생각됨.
}
