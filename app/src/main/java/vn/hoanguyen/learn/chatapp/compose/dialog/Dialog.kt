package vn.hoanguyen.learn.chatapp.compose.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (() -> Unit)?,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector?,
) {
    AlertDialog(
        icon = icon?.let {
            { Icon(it, contentDescription = it.toString()) }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation?.invoke()
                }
            ) {
                Text("Ok")
            }
        },
        dismissButton = onConfirmation?.let {
            {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Cancel")
                }
            }
        }
    )
}