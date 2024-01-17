package com.example.scrachcard

class ImageModel(image: String?) {
    private var image: String

    init {
        this.image = image!!
    }
    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image!!
    }

}