package ru.mareanexx.travelogue.presentation.screens.trip.components.modalsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.rememberMapState
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.screens.trip.components.dialogs.CurrentLocationViewAnnotation
import ru.mareanexx.travelogue.presentation.screens.trip.viewmodel.form.MapPointForm
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.blueBackground
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun LocationBlock(onOpenChooseLocationDialog: () -> Unit, mapPointForm: MapPointForm) {
    if (mapPointForm.latitude == Double.MIN_VALUE && mapPointForm.longitude == Double.MIN_VALUE) {
        Box(
            modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth().height(205.dp)
                .clip(Shapes.small).border(2.dp, primaryText, Shapes.small),
            contentAlignment = Alignment.Center
        ) {
            Button(
                shape = Shapes.small,
                colors = ButtonDefaults.buttonColors(containerColor = primaryText, contentColor = Color.White),
                onClick = onOpenChooseLocationDialog
            ) {
                Text(text = stringResource(R.string.choose_location), style = MaterialTheme.typography.labelMedium)
            }
        }
    } else { StaticLocationMap(mapPointForm, onOpenChooseLocationDialog) }
}

@Composable
fun StaticLocationMap(mapPointForm: MapPointForm, onOpenChooseLocationSheet: () -> Unit) {
    Box(modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth().height(205.dp).clip(Shapes.small)) {

        val point = Point.fromLngLat(mapPointForm.longitude, mapPointForm.latitude)

        MapboxMap(
            modifier = Modifier.fillMaxWidth().height(205.dp),
            compass = {}, scaleBar = {},
            mapViewportState = rememberMapViewportState {
                setCameraOptions {
                    center(point)
                    zoom(13.0)
                    pitch(0.0)
                    bearing(0.0)
                }
            },
            mapState = rememberMapState {
                gesturesSettings = GesturesSettings {
                    scrollEnabled = false
                    rotateEnabled = false
                    pinchToZoomEnabled = false
                    pitchEnabled = false
                    doubleTapToZoomInEnabled = false
                    quickZoomEnabled = false
                }
            }
        ) {
            PointAnnotation(point) {}
            ViewAnnotation(
                options = viewAnnotationOptions {
                    geometry(point)
                    annotationAnchor { anchor(ViewAnnotationAnchor.BOTTOM) }
                }
            ) { CurrentLocationViewAnnotation(R.string.chosen_location) }
        }
        Box(
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 15.dp).size(40.dp)
                .background(blueBackground, shape = CircleShape)
                .clickable(
                    remember { MutableInteractionSource() },
                    null
                ) { onOpenChooseLocationSheet() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.edit_square), modifier = Modifier.size(30.dp),
                tint = Color.White, contentDescription = stringResource(R.string.choose_location)
            )
        }
    }
}