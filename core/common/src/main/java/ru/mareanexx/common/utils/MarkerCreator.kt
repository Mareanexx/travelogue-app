package ru.mareanexx.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

object MarkerCreator {
    fun createMarkerBitmap(context: Context, @DrawableRes imageRes: Int): Bitmap {
        val sizePx = 120
        val borderPx = 6f
        val shadowRadius = 12f
        val shadowColor = Color(0xFF178FC5).toArgb()

        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.setShadowLayer(shadowRadius, 0f, 0f, shadowColor)
        canvas.drawCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f - borderPx, paint)

        paint.clearShadowLayer()
        paint.color = Color.White.toArgb()
        canvas.drawCircle(sizePx / 2f, sizePx / 2f, sizePx / 2f - borderPx / 2, paint)

        val avatar = BitmapFactory.decodeResource(context.resources, imageRes)
        val clipped = Bitmap.createScaledBitmap(avatar, sizePx - 2 * borderPx.toInt(), sizePx - 2 * borderPx.toInt(), true)
        val rect = RectF(borderPx, borderPx, sizePx - borderPx, sizePx - borderPx)

        val path = Path().apply {
            addOval(rect, Path.Direction.CW)
        }

        canvas.save()
        canvas.clipPath(path)
        canvas.drawBitmap(clipped, null, rect, null)
        canvas.restore()

        return bitmap
    }

    fun createMarkerBitmapFromBitmap(avatar: Bitmap): Bitmap {
        val sizePx = 130
        val shadowRadius = 8f
        val borderWidth = 8f
        val whiteBorder = 6f

        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val center = sizePx / 2f

        // Радиусы кругов
        val outerRadius = center // Самый внешний (тень)
        val middleRadius = center - shadowRadius // Синяя обводка
        val innerRadius = middleRadius - borderWidth // Белая обводка
        val imageRadius = innerRadius - whiteBorder // Радиус картинки

        // Кисть
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // 1. Полупрозрачная синяя тень
        paint.color = Color(0x80178FC5).toArgb()
        canvas.drawCircle(center, center, outerRadius, paint)

        // 2. Более плотная синяя обводка
        paint.color = Color(0xD9178FC5).toArgb()
        canvas.drawCircle(center, center, middleRadius, paint)

        // 3. Белая обводка
        paint.color = Color.White.toArgb()
        canvas.drawCircle(center, center, innerRadius, paint)

        // 4. Картинка внутри круга
        val imageSize = (imageRadius * 2).toInt()
        val scaledAvatar = Bitmap.createScaledBitmap(avatar, imageSize, imageSize, true)
        val imageRect = RectF(
            center - imageRadius,
            center - imageRadius,
            center + imageRadius,
            center + imageRadius
        )

        val path = Path().apply { addOval(imageRect, Path.Direction.CW) }
        canvas.save()
        canvas.clipPath(path)
        canvas.drawBitmap(scaledAvatar, null, imageRect, null)
        canvas.restore()

        return bitmap
    }

}