package com.pss_dev.inventrix_blg.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CustomBottomSheet(
    modifier: Modifier = Modifier,
    title: String? = null,
    onDismissRequest: () -> Unit = {},
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable () -> Unit = {}
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        sheetMaxWidth = BottomSheetDefaults.SheetMaxWidth,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        tonalElevation = BottomSheetDefaults.Elevation,
        shape = BottomSheetDefaults.ExpandedShape,
        scrimColor = BottomSheetDefaults.ScrimColor,
        windowInsets = BottomSheetDefaults.windowInsets,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = true,
            isFocusable = true,
            securePolicy = SecureFlagPolicy.SecureOn
        )
    ) {
        if (title != null) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }
        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        )
        content()
    }
}
