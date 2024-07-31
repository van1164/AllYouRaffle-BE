package com.van1164.lottoissofar.common.util

import com.van1164.lottoissofar.common.domain.BaseEntity
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

inline fun <R> multiCatch(
    mTry: () -> R,
    vararg mCatch: Pair<List<Class<out Throwable>>, (Throwable) -> R>,
    noinline finallyBlock: (() -> Unit)? = null
): R {
    return try {
        mTry()
    } catch (e: Throwable) {
        for (handler in mCatch) {
            for(exception in handler.first){
                if (exception.isInstance(e)) {
                    return handler.second(e)
                }
            }
        }
        throw e
    } finally {
        finallyBlock?.invoke()
    }
}

fun <T : BaseEntity> softDelete(id : Long, repository : JpaRepository<T, Long>){
    repository.findById(id).run {
        val item = this.orElseThrow{ GlobalExceptions.NotFoundException()}
        item.deletedDate= LocalDateTime.now()
    }
}
