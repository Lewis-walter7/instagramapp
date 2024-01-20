package com.licoding.instagramapp.presentation.main.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.licoding.instagramapp.data.models.BottomSheetMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    val items = listOf(
        BottomSheetMenuItem(
            name = "Settings and privacy",
            icon = Icons.Default.Settings
        ),
        BottomSheetMenuItem(
            name = "Threads",
            icon = Icons.Default.Settings
        ),
        BottomSheetMenuItem(
            name = "Your activity",
            icon = Icons.Outlined.MonitorHeart
        ),
        BottomSheetMenuItem(
            name = "Archive",
            icon = Icons.Default.Archive
        ),
        BottomSheetMenuItem(
            name = "QR code",
            icon = Icons.Default.QrCodeScanner
        ),
        BottomSheetMenuItem(
            name = "Saved",
            icon = Icons.Default.BookmarkBorder
        ),
        BottomSheetMenuItem(
            name = "Supervision",
            icon = Icons.Default.People
        ),
        BottomSheetMenuItem(
            name = "Orders and payments",
            icon = Icons.Default.CreditCard
        ),
        BottomSheetMenuItem(
            name = "Meta Verified",
            icon = Icons.Outlined.Verified
        ),
        BottomSheetMenuItem(
            name = "Close friends",
            icon = Icons.Outlined.FormatListBulleted
        ),
        BottomSheetMenuItem(
            name = "Favorites",
            icon = Icons.Default.StarBorder
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
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.name,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}