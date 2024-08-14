package com.pss_dev.inventrix_blg.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    title: String = "ShivShakti Enterprises",
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = { },
    elevation: Dp = 4.dp,
    onTextButtonClicked: () -> Unit = {},
    imageVector: ImageVector? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                BorderStroke(0.5.dp, MaterialTheme.colorScheme.onPrimaryContainer),
                shape = RoundedCornerShape(20.dp)
            ),
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = elevation,
        tonalElevation = elevation
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(40.dp),
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(40.dp)
                        .padding(end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = onTextButtonClicked) {
                        Text(
                            modifier = Modifier.width(120.dp),
                            text = title,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                        if (imageVector != null) {
                            Icon(
                                imageVector = imageVector,
                                contentDescription = "More Companies",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            },
            navigationIcon = navigationIcon,
            actions = actions
        )
    }

}