package com.van1164.lottoissofar.common.dto.item

import com.van1164.lottoissofar.common.domain.Item
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.Length


data class CreateItemDto(
    @field:NotBlank
    @field:Length(min = 1, max = 50, message = "제목은 1이상 50이하입니다.")
    val name : String,

    val category : Int,

    @field:NotBlank(message = "내용이 비어있을 수 없습니다.")
    @field:Length(min = 1)
    val description : String
) : BaseDto<Item>{
    override fun toDomain() : Item {
        return Item(
            this.name,
            this.category,
            this.description
        )
    }

    fun toDomain(imageUrl: String?): Item {
        return Item(
            name = this.name,
            category = this.category,
            imageUrl = imageUrl,
            description = this.description
        )
    }
}
