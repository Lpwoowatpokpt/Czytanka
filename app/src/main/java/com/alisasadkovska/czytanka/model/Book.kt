package com.alisasadkovska.czytanka.model

class Book {
    var age: String ?=null
    var author: String ?=null
    var cover: String ?=null
    var description: String ?=null
    var downloadUrl: String ?=null
    var painter: String ?= null
    var title: String ?=null

    constructor(){}

    constructor(
        age: String?,
        author: String?,
        cover: String?,
        description: String?,
        downloadUrl: String?,
        painter: String?,
        title: String?
    ) {
        this.age = age
        this.author = author
        this.cover = cover
        this.description = description
        this.downloadUrl = downloadUrl
        this.painter = painter
        this.title = title
    }


}