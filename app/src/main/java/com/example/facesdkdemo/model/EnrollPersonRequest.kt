package com.example.facesdkdemo.model

import com.google.gson.annotations.SerializedName

data class EnrollPersonRequest(
    @SerializedName("Person") val person: PersonRequest
)

data class PersonRequest(
    @SerializedName("CustomID") val customID: String,
    @SerializedName("Face") val face: List<FacePersonRequest>?
)

data class FacePersonRequest(
    @SerializedName("Face-1") val face: String
)