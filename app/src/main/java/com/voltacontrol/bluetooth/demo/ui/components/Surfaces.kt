package com.voltacontrol.bluetooth.demo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val SmallCorner = 8.dp
val LargeCorner = 32.dp

@Composable
fun SRoundedCard(
    modifier: Modifier = Modifier,
    contentPadding: Dp = 16.dp,
    border: BorderStroke? = null,
    onclick: (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    color: Color = MaterialTheme.colors.surface,
    content: @Composable () -> Unit
) {
    RoundedCard(
        modifier = modifier,
        border = border,
        content = content,
        contentPadding = contentPadding,
        elevation = elevation,
        onclick = onclick,
        color = color
    )
}

@Composable
fun LRoundedCard(
    modifier: Modifier = Modifier,
    onclick: () -> Unit = {},
    border: BorderStroke? = null,
    content: @Composable () -> Unit,
) {
    RoundedCard(modifier = modifier, cornerSize = LargeCorner, onclick = onclick, content = content, border = border)
}

@Composable
fun CircularCard(
    modifier: Modifier = Modifier,
    onclick: (() -> Unit)? = null,
    gradient: Brush? = null,
    color: Color = MaterialTheme.colors.surface,
    elevation: Dp = 2.dp,
    contentPadding: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    val shape: Shape = RoundedCornerShape(percent = 50)
    STCard(
        modifier = modifier,
        elevation = elevation,
        contentPadding = contentPadding,
        shape = shape,
        gradient = gradient,
        color = color,
        onclick = onclick,
        content = content
    )
}

@Composable
private fun RoundedCard(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.surface,
    gradient: Brush? = null,
    cornerSize: Dp = SmallCorner,
    onclick: (() -> Unit)? = null,
    contentPadding: Dp = 16.dp,
    border: BorderStroke? = null,
    elevation: Dp = 2.dp,
    content: @Composable () -> Unit
) {
    val shape: Shape = RoundedCornerShape(CornerSize(cornerSize))
    STCard(
        border = border,
        modifier = modifier,
        color = color,
        gradient = gradient,
        shape = shape,
        elevation = elevation,
        onclick = onclick,
        content = content,
        contentPadding = contentPadding,
    )
}

@Composable
private fun STCard(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.surface,
    gradient: Brush? = null,
    shape: Shape = RoundedCornerShape(percent = 50),
    onclick: (() -> Unit)? = null,
    elevation: Dp = 2.dp,
    contentPadding: Dp = 16.dp,
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .shadow(elevation, shape, clip = true)
            .then(
                if (gradient == null)
                    Modifier.background(color, shape)
                else Modifier.background(gradient, shape)
            )
            .then(
                if (onclick != null) Modifier.clickable(
                    enabled = true,
                    onClick = onclick,
                    role = Role.Button
                ) else Modifier
            )
            .then(
                if (border != null)
                    Modifier.border(border, shape)
                else Modifier
            )
            .padding(contentPadding),
        color = Color.Transparent,
        content = content
    )
}


