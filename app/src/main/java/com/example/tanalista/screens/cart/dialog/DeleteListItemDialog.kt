package com.example.tanalista.screens.cart.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.tanalista.R

@Composable
fun DeleteListItemDialog(deleteDialogViewModel: DeleteListItemDialogViewModel) {
    if (deleteDialogViewModel.isDialogOpen) {
        Dialog(onDismissRequest = { deleteDialogViewModel.closeDialog() })
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(R.color.white), shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.remove_item),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = stringResource(R.string.question_are_you_sure_remove_item),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(0.dp, 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { deleteDialogViewModel.deleteListItem() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.error)
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_trash),
                            contentDescription = "Delete button",
                            tint = colorResource(R.color.white),
                            modifier = Modifier.size(18.dp),
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            stringResource(R.string.remove),
                            color = colorResource(R.color.white),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }
        }
    }
}