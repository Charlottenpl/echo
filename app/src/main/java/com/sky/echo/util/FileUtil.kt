package com.sky.echo.util

import java.io.File

object FileUtil {

    const val catch_path = "catch/"
    const val image_path = "image/"
    const val music_path = "music/"


    fun isExist(path: String): Boolean{
        return try {
            var file = File(path)
            file.exists()
        }catch (e: Exception){
            false
        }
    }

    fun imagePath(fileName: String): String{
        return catch_path + image_path + fileName
    }
}