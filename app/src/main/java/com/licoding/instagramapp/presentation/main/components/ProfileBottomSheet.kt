package com.licoding.instagramapp.presentation.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.licoding.instagramapp.data.models.BottomSheetMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheet(
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    val items = listOf(
        BottomSheetMenuItem(
            name = "Report...",
        ),
        BottomSheetMenuItem(
            name = "Block",
        ),
        BottomSheetMenuItem(
            name = "Block this account",
        ),
        BottomSheetMenuItem(
            name = "Restrict",
        ),
        BottomSheetMenuItem(
            name = "Hide your story",
        ),
        BottomSheetMenuItem(
            name = "Copy profile URL",
        ),
        BottomSheetMenuItem(
            name = "Show QR code",
        ),
        BottomSheetMenuItem(
            name = "Send message",
        ),
        BottomSheetMenuItem(
            name = "Share profile",
        )
    )

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 13.dp)
        ) {
            items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}