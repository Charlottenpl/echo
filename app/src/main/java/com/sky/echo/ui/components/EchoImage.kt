package com.sky.echo.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sky.echo.R
import com.sky.echo.util.FileUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

@Composable
fun EchoImage(imageUrl: String, localImagePath: String, modifier: Modifier = Modifier) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var state by remember { mutableIntStateOf(0) } // 0: default 1：loading 2：success 3：cancel 4: fail

    LaunchedEffect(imageUrl) {
        state = 1
        show("加载图片$imageUrl")
        val file = File(localImagePath)
        if (file.exists()) {
            show("图片存在")
            bitmap = BitmapFactory.decodeFile(localImagePath)
        } else {
            show("图片不存在，从网络下载")
            // 从网络下载图片
            withContext(Dispatchers.IO) {
                try {
                    val url = URL(imageUrl)
                    bitmap = BitmapFactory.decodeStream(url.openStream())
                    if (bitmap == null) throw NullPointerException()
                    // 将图片保存到本地
                    show("保存图片到本地")
                    FileUtil.saveImage(bitmap!!, localImagePath)
                    state = 2
                } catch (e: Exception) {
                    // 处理异常
                    show("异常${e.message}")
                    state = 4
                }
            }
        }
    }

    Box(modifier = modifier) {
        when(state){
            0, 1 ->
                // 显示占位图
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(30.dp)), // 设置圆角
                    painter = painterResource(id = R.drawable.def),
                    contentDescription = null
                )

            2 ->
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(30.dp)), // 设置圆角
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

            3 ,4 ->
                // 显示占位图
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(30.dp)), // 设置圆角
                    painter = painterResource(id = R.drawable.def),
                    contentDescription = null
                )

        }
    }
}

fun show(text: String){
    Log.e("dev", text )
}