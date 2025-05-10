package ru.mareanexx.travelogue.presentation.screens.trip.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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


@Composable
fun MapBoxWithMapPoints(
    tripData: State<TripWithMapPoints?>,
    mapPointBitmaps: Map<Int, Bitmap>,
    focusedMapPointId: State<Int?>,
    onPointClicked: (Int) -> Unit
) {
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
            mapViewportState.easeTo(
                cameraOptions {
                    center(Point.fromLngLat(it.longitude, it.latitude))
                    zoom(12.0)
                },
                MapAnimationOptions.mapAnimationOptions {
                    duration(700)
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