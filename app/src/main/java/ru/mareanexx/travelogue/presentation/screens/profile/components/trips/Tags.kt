package ru.mareanexx.travelogue.presentation.screens.profile.components.trips

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mareanexx.travelogue.R
import ru.mareanexx.travelogue.presentation.theme.MontserratFamily
import ru.mareanexx.travelogue.presentation.theme.Shapes
import ru.mareanexx.travelogue.presentation.theme.mainBorderColor
import ru.mareanexx.travelogue.presentation.theme.primaryText

@Composable
fun AddTagButton(onAddTag: () -> Unit) {
    Button(
        modifier = Modifier.height(30.dp),
        contentPadding = PaddingValues(start = 7.dp, end = 12.dp),
        border = BorderStroke(width = 2.dp, color = mainBorderColor),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = mainBorderColor),
        onClick = onAddTag
    ) {
        Icon(modifier = Modifier.size(20.dp), painter = painterResource(R.drawable.plus_icon), contentDescription = null)
        Text(text = stringResource(R.string.add_tag), style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
fun ConcreteTag(
    value: String,
    onValueChanged: (String) -> Unit,
    onTagDelete: () -> Unit
) {
    Row(
        modifier = Modifier.height(30.dp).widthIn(min = 60.dp, max = 200.dp)
            .background(primaryText, Shapes.medium).padding(start = 7.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(modifier = Modifier.size(20.dp), painter = painterResource(R.drawable.tag_icon), contentDescription = null, tint = Color.White)
        BasicTextField(
            modifier = Modifier.width(IntrinsicSize.Min),
            value = value,
            textStyle = TextStyle(fontFamily = MontserratFamily, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp, lineHeight = 13.sp),
            onValueChange = onValueChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
        )
        Box(
            modifier = Modifier.padding(start = 8.dp)
            .background(Color.LightGray, CircleShape)
            .clickable {
                onTagDelete()
            }
        ) {
            Icon(
                modifier = Modifier.size(15.dp),
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Black
            )
        }
    }
}