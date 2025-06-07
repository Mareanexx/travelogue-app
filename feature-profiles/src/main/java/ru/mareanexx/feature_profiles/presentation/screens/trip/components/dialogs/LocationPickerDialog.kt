package ru.mareanexx.feature_profiles.presentation.screens.trip.components.dialogs

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.google.android.gms.location.LocationServices
import com.mapbox.geojson.Point
import com.mapbox.maps.ViewAnnotationAnchor
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.ViewAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.viewannotation.annotationAnchor
import com.mapbox.maps.viewannotation.geometry
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import ru.mareanexx.common.ui.theme.Shapes
import ru.mareanexx.common.ui.theme.primaryText
import ru.mareanexx.common.ui.theme.profilePrimaryText
import ru.mareanexx.feature_profiles.R

@Composable
fun LocationPickerDialog(onConfirm: (latitude: Double, longitude: Double) -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    var currentLocation by remember { mutableStateOf<Point?>(null) }
    val selectedPoint = remember { mutableStateOf<Point?>(null) }

    LaunchedEffect(Unit) {
        try {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                location -> currentLocation = Point.fromLngLat(location.longitude, location.latitude)
            }
        } catch (e: SecurityException) { Log.d("LOCATION", "Cant get current location as '${e.message}'") }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false, usePlatformDefaultWidth = false)
    ) {
        Column(modifier = Modifier.systemBarsPadding().background(Color.White).fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTextButton(ru.mareanexx.core.common.R.string.cancel_btn, onClick = onDismiss)
                Text(
                    text = stringResource(R.string.choose_location),
                    style = MaterialTheme.typography.displaySmall,
                    color = profilePrimaryText, textAlign = TextAlign.Center
                )
                CustomTextButton(
                    text = R.string.confirm_location, enabled = selectedPoint.value != null,
                    onClick = { onConfirm(selectedPoint.value!!.latitude(), selectedPoint.value!!.longitude()) }
                )
            }
            MapboxMap(
                modifier = Modifier.fillMaxSize().zIndex(100f),
                compass = {}, scaleBar = {},
                mapViewportState = rememberMapViewportState {
                    setCameraOptions {
                        center(if (currentLocation != null) currentLocation else Point.fromLngLat(37.617188, 55.755892))
                        zoom(12.0)
                        pitch(0.0)
                        bearing(0.0)
                    }
                },
                onMapClickListener = { point ->
                    selectedPoint.value = point
                    true
                },
            ) {
                val icon = rememberIconImage(R.drawable.location)
                selectedPoint.value?.let { point ->
                    PointAnnotation(point) {
                        iconImage = icon
                        iconColor = Color.Red
                        iconSize = 3.0
                    }
                }
                currentLocation?.let {
                    PointAnnotation(point = it)
                    ViewAnnotation(
                        options = viewAnnotationOptions {
                            geometry(it)
                            annotationAnchor { anchor(ViewAnnotationAnchor.TOP) }
                        }
                    ) { CurrentLocationViewAnnotation(R.string.current_location_va) }
                }
            }
        }
    }
}

@Composable
fun CurrentLocationViewAnnotation(chosenLocation: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            modifier = Modifier.background(Color.White, Shapes.small)
                .padding(horizontal = 5.dp, vertical = 3.dp),
            text = stringResource(chosenLocation), color = primaryText,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight =  FontWeight.Bold)
        )
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(R.drawable.location),
            contentDescription = null, tint = Color.Red
        )
    }
}

@Composable
fun CustomTextButton(@StringRes text: Int, enabled: Boolean = true, onClick: () -> Unit) {
    TextButton(enabled = enabled, onClick = onClick) {
        Text(
            text = stringResource(text), color = primaryText,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLocation() {
    LocationPickerDialog(onDismiss = {}, onConfirm = { _, _ ->  })
}