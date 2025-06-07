package ru.mareanexx.feature_auth.presentation.screens.register.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mareanexx.common.ui.theme.disabledButtonContainer
import ru.mareanexx.common.ui.theme.disabledButtonContent
import ru.mareanexx.common.ui.theme.greenCircle
import ru.mareanexx.feature_auth.R
import ru.mareanexx.feature_auth.presentation.screens.register.viewmodel.form.RegisterFormState

@Composable
fun CheckersGrid(formState: State<RegisterFormState>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(start = 15.dp, end = 15.dp, bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        item { OneCheckerIndicator(R.string.uppercase_requirement, formState.value.uppercaseLetterState, Arrangement.Start) }
        item { OneCheckerIndicator(R.string.eight_chars_requirement, formState.value.eightCharsState, Arrangement.End) }
        item { OneCheckerIndicator(R.string.lowercase_requirement, formState.value.lowercaseLetterState, Arrangement.Start) }
        item { OneCheckerIndicator(R.string.one_digit_requirement, formState.value.oneDigitState, Arrangement.End) }
    }
}

@Composable
fun OneCheckerIndicator(
    @StringRes requirementRes: Int,
    requirementState: Boolean,
    horizontalArrangement: Arrangement.Horizontal
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement
    ) {
        Box(
            modifier = Modifier.size(16.dp)
                .background(if (!requirementState) disabledButtonContainer else greenCircle,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.check_icon),
                contentDescription = stringResource(R.string.check_icon_cd)
            )
        }
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = stringResource(requirementRes),
            style = MaterialTheme.typography.labelSmall,
            color = disabledButtonContent
        )
    }
}