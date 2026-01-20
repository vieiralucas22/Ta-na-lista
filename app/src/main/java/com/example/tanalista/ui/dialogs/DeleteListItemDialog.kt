package com.example.tanalista.ui.dialogs

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
import com.example.tanalista.R
import com.example.tanalista.ui.theme.Error
import com.example.tanalista.ui.theme.White
import com.example.tanalista.viewmodel.dialog.DeleteListItemDialogViewModel

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
                Text(text = "Remover item!", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    text = "Tem certeza que deseja remover esse item da lista?",
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
                            "Remover",
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