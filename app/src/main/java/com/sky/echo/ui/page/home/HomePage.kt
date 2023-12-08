package com.sky.echo.ui.page.home

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky.echo.R
import com.sky.echo.ui.components.EchoImage
import com.sky.echo.util.FileUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

@Preview(showSystemUi = true)
@Composable
fun HomePage(user: String = "1234"){
    

    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .padding(10.dp)
            .background(color = MaterialTheme.colorScheme.background),
        ){
        Column(
            modifier = Modifier.fillMaxWidth()
                ) {

            /** 顶部操作栏*/
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Hello $user")
            }

            /** 音乐背景图*/
            Box(modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()) {
                EchoImage(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f)
                        .align(Alignment.Center),
                    imageUrl = "https://images.cnblogs.com/cnblogs_com/charlottepl/1676587/o_210408083032QQ%E5%9B%BE%E7%89%8720210408162958.jpg",
                    localImagePath = FileUtil.imagePath("def.jpg")
                )
            }



            /**
             * 歌词
             * 显示上一行，当前行，下一行
             */
            Box(modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth(0.9f)
                .align(Alignment.CenterHorizontally)
                .padding(0.dp)
                .padding(10.dp)
                .weight(1f)
            ) {
                Text(
                    text = "如果这不是结局 如果我还爱你",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.CenterStart))
            }


            /**
             * 歌名
             * 信息栏
             */
            Box (
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp)
                    .weight(1f)
            ){
                Column {
                    Text(
                        text = "Charlotte Pl",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    Row {
                        Text(
                            text = "blog:charlotte.pl with google and android.",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.widthIn(max = with(LocalView.current) {
                                var w = (LocalView.current.width/ LocalDensity.current.density + 0.5f) * 0.8f;
                                Log.e("HomePage", "$w")
                                Dp(w)
                            }),
                            overflow = TextOverflow.Ellipsis, // 当文本超出限制时显示省略号
                            maxLines = 1, // 限制为单行文本
                            softWrap = false // 禁止自动换行
                        )

                        Box (
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(5.dp, 0.dp)
                        ){
                            Text(text = "VIP",fontSize = 12.sp)
                        }
                    }
                }
            }

            /**
             * 底部操作栏
             * 播放暂停进度条收藏操作
             */
            Box (
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp)
                    .weight(2f)
            ){
            }
        }
    }

}