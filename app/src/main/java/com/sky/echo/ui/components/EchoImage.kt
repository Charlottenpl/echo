package com.sky.echo.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.sky.echo.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

@Composable
fun EchoImage(imageUrl: String, localImagePath: String, modifier: Modifier = Modifier) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(imageUrl) {
        isLoading = true
        val file = File(localImagePath)
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(localImagePath)
        } else {
            // 从网络下载图片
            withContext(Dispatchers.IO) {
                try {
                    val url = URL(imageUrl)
                    bitmap = BitmapFactory.decodeStream(url.openStream())
                    // 可选：将图片保存到本地
                } catch (e: Exception) {
                    // 处理异常
                }
            }
        }
        isLoading = false
    }

    Box(modifier = modifier) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap!!.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        } else if (isLoading) {
            // 显示占位图
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )
        }
    }
}