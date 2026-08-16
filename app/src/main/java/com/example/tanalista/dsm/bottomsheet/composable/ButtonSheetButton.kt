package com.example.tanalista.dsm.bottomsheet.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tanalista.R
import com.example.tanalista.dsm.bottomsheet.BottomSheetButtonAction
import com.example.tanalista.dsm.bottomsheet.model.BottomSheetButtonState
import com.example.tanalista.screens.home.action.EditAction

@Composable
fun ButtonSheetButton(
    state: BottomSheetButtonState,
    modifier: Modifier = Modifier,
    onClick: (BottomSheetButtonAction) -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(R.color.grayBackground))
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
            ) { onClick(state.action) }
            .padding(vertical = 14.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(colorResource(R.color.floatingButton)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(state.iconId),
                contentDescription = null,
                tint = colorResource(R.color.white),
                modifier = Modifier.size(18.dp)
            )
        }

        Text(
            text = stringResource(state.titleId),
            modifier = Modifier.weight(1f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_right),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Preview
@Composable
private fun ButtonSheetButtonPreview() {
    ButtonSheetButton(
        state = BottomSheetButtonState(
            titleId = R.string.new_list,
            iconId = R.drawable.ic_plus,
            action = EditAction(0)
        )
    )
}