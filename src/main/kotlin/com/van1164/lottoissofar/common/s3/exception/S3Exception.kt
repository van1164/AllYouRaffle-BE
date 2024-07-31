package com.van1164.lottoissofar.common.s3.exception

open class S3Exception : RuntimeException()

class MultiPartConvertException : S3Exception()

class ImageUploadException : S3Exception()