package ru.mareanexx.travelogue.presentation.screens.trip.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.mapbox.geojson.Point
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PolylineAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.extension.style.layers.properties.generated.LineJoin
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import ru.mareanexx.travelogue.data.trip.remote.dto.TripWithMapPoints
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val R = 6371 // радиус Земли в километрах
    val dLat = Math.toRadians(lat2 - lat1)
    val dLon = Math.toRadians(lon2 - lon1)
    val a = sin(dLat / 2).pow(2.0) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2.0)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    return R * c // расстояние в километрах
}

@Composable
fun MapBoxWithMapPoints(
    tripData: State<TripWithMapPoints?>,
    mapPointBitmaps: Map<Int, Bitmap>,
    focusedMapPointId: State<Int?>,
    onPointClicked: (Int) -> Unit
) {
    var previousPoint by remember { mutableStateOf<Point?>(null) }

    val mapViewportState = rememberMapViewportState {
        if (tripData.value!!.mapPoints.isNotEmpty()) {
            val first = tripData.value!!.mapPoints.first().mapPoint
            setCameraOptions {
                zoom(1.0)
                center(Point.fromLngLat(first.longitude, first.latitude))
            }
        }
    }

    LaunchedEffect(focusedMapPointId.value) {
        val target = tripData.value!!.mapPoints.find { it.mapPoint.id == focusedMapPointId.value }?.mapPoint
        target?.let {
            val newPoint = Point.fromLngLat(it.longitude, it.latitude)

            val distance = previousPoint?.let { prev ->
                haversineDistance(
                    prev.latitude(), prev.longitude(),
                    newPoint.latitude(), newPoint.longitude()
                )
            } ?: 0.0

            val duration = when {
                distance < 100.0 -> 1000L
                distance in (100.0..400.0) -> 2000L
                distance in (400.0..800.0) -> 3000L
                else -> 5000L
            }

            previousPoint = newPoint

            mapViewportState.flyTo(
                cameraOptions {
                    center(newPoint)
                    zoom(12.0)
                },
                MapAnimationOptions.mapAnimationOptions {
                    this.duration(duration)
                }
            )
        }
    }

    MapboxMap(
        modifier = Modifier.fillMaxSize(),
        scaleBar = {}, compass = {}, logo = {}, attribution = {},
        mapViewportState = mapViewportState
    ) {
        if (tripData.value!!.mapPoints.size >= 2) {
            val pointList = tripData.value!!.mapPoints.map {
                Point.fromLngLat(it.mapPoint.longitude, it.mapPoint.latitude)
            }
            PolylineAnnotation(points = pointList) {
                lineColor = Color.White
                lineWidth = 4.5
                lineJoin = LineJoin.ROUND
            }
        }

        tripData.value!!.mapPoints.forEach { pointWithPhotos ->
            val mapPoint = pointWithPhotos.mapPoint
            val bitmap = mapPointBitmaps[mapPoint.id]

            bitmap?.let {
                val icon = rememberIconImage("marker_${mapPoint.id}", BitmapPainter(it.asImageBitmap()))

                PointAnnotation(Point.fromLngLat(mapPoint.longitude, mapPoint.latitude)) {
                    iconImage = icon
                    iconSize = 1.2
                    interactionsState.onClicked { onPointClicked(mapPoint.id); true }
                }
            }
        }
    }
}