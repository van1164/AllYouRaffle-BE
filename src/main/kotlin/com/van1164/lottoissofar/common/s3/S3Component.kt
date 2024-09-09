package com.van1164.lottoissofar.common.s3

import com.amazonaws.AmazonClientException
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.S3Object
import com.van1164.lottoissofar.common.s3.exception.ImageUploadException
import com.van1164.lottoissofar.common.s3.exception.MultiPartConvertException
import com.van1164.lottoissofar.common.util.multiCatch
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*


@Component
class S3Component(
    private val amazonS3Client: AmazonS3Client,
    @Value("\${cloud.aws.s3.bucket}") private val bucket : String,
    @Value("\${cloud.aws.s3.region}") private val region: String
) {
    private val urlPrefix = "https://$bucket.s3.$region.amazonaws.com/"

    fun imageUpload(image: MultipartFile) : String{
        return multiCatch(
            mTry = {
                val key = UUID.randomUUID().toString() + image.originalFilename
                val metadata = ObjectMetadata().apply {
                    contentLength = image.inputStream.available().toLong()
                    contentType = image.contentType
                }
                amazonS3Client.putObject(bucket,key,image.inputStream, metadata)

                return@multiCatch urlPrefix+key
            },
            mCatch= arrayOf(
                listOf(MultiPartConvertException::class.java,AmazonClientException::class.java) to {_ ->
                    throw ImageUploadException()
                }
            )
        )
    }

    fun getImage(key : String): S3Object {
        return amazonS3Client.getObject(bucket,key)
    }

//    private fun MultipartFile.toFile(): File {
//        val convertFile = File(this.originalFilename?:"")
//        if (convertFile.createNewFile()) {
//            FileOutputStream(convertFile).use { fos ->
//                fos.write(this.bytes)
//            }
//            return convertFile
//        }
//        throw MultiPartConvertException()
//    }

}