package com.example.tanalista.ui.views.dialogs

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.application
import com.example.tanalista.R
import com.example.tanalista.ui.theme.Error
import com.example.tanalista.ui.theme.White
import com.example.tanalista.ui.viewmodel.dialog.DeleteListItemDialogViewModel

@Composable
fun DeleteListItemDialog(deleteDialogViewModel: DeleteListItemDialogViewModel) {
    if (deleteDialogViewModel.isDialogOpen) {
        Dialog(onDismissRequest = { deleteDialogViewModel.closeDialog() })
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text(text = deleteDialogViewModel.application.getText(R.string.remove_item).toString(), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    text = deleteDialogViewModel.application.getText(R.string.question_are_you_sure_remove_item).toString(),
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
                            containerColor = Error
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_trash),
                            contentDescription = "Delete button",
                            tint = White,
                            modifier = Modifier.size(18.dp),
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            deleteDialogViewModel.application.getText(R.string.remove).toString(),
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

            }
        }
    }
}