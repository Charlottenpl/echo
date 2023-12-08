package com.sky.echo.util

import android.graphics.Bitmap
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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


    fun saveImage(bitmap: Bitmap, path: String): Boolean{
        return try {
            val file = File(path)
            file.parentFile?.mkdirs() // 确保父目录存在
            FileOutputStream(file).use { output ->
                bitmap.compress(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) Bitmap.CompressFormat.WEBP_LOSSLESS else Bitmap.CompressFormat.PNG,
                    100,
                    output
                )
            }
            Log.e("dev", "save to $path")
            true
        } catch (e: IOException) {
            false
        }
    }
}