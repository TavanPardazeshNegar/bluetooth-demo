package com.voltacontrol.bluetooth.demo.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.voltacontrol.bluetooth.demo.R
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme


@Composable
fun StWideTextButton(
    text: String = "",
    textAlign: TextAlign = TextAlign.Center,
    onclick: () -> Unit = {}
) {
    TextButton(
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
        onClick = onclick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            textAlign = textAlign,
            text = text,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun StTextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textAlign: TextAlign = TextAlign.Center,
    color: Color = MaterialTheme.colors.onSurface,
    contentPadding: Dp = 8.dp,
    endIcon: Int? = null,
    endIconRotation: Float = 0f,
    startIcon: Int? = null,
    enabled: Boolean = true,
    backColor:Color = Color.Transparent,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    onclick: () -> Unit = {}
) {
    TextButton(
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
        onClick = onclick,
        modifier = modifier.background(backColor, shape),
        shape = shape,
        border = border
        ) {
        if (startIcon != null)
            Icon(painter = painterResource(id = startIcon), contentDescription = null)
        Text(
            textAlign = textAlign,
            text = text,
            modifier = Modifier
                .padding(contentPadding),
            color = if (enabled) color else color.copy(alpha = 0.5f)
        )
        if (endIcon != null)
            Icon(
                painter = painterResource(id = endIcon),
                contentDescription = null,
                modifier = Modifier.rotate(endIconRotation),
                tint = color
            )
    }
}

@Composable
fun PRoundedButton(
    modifier: Modifier = Modifier,
    contentModifier: Modifier,
    @DrawableRes
    lIcon: Int? = null,
    lIconDes: String? = null,
    enabled: Boolean = true,
    text: String = "",
    onclick: () -> Unit = {}
) {
    RoundedButton(
        modifier = modifier,
        colors = buttonColors(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.primaryVariant,
            MaterialTheme.colors.primarySurface
        ),
        enabled = enabled,
        lIcon = lIcon,
        lIconDes = lIconDes,
        contentColor = MaterialTheme.colors.onPrimary,
        text = text,
        onclick = onclick,
        contentModifier = contentModifier
    )

}

@Composable
fun PCircleCornerButton(
    modifier: Modifier = Modifier,
    @DrawableRes
    lIcon: Int? = null,
    lIconDes: String? = null,
    enabled: Boolean = true,
    text: String = "",
    onclick: () -> Unit = {}
) {
    RoundedButton(
        modifier = modifier,
        colors = buttonColors(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.primary,
            MaterialTheme.colors.onPrimary
        ),
        enabled = enabled,
        lIcon = lIcon,
        lIconDes = lIconDes,
        contentColor = MaterialTheme.colors.onPrimary,
        text = text,
        shape = RoundedCornerShape(percent = 50),
        onclick = onclick,
    )
}

@Composable
fun OutlinedCircleCornerButton(
    @DrawableRes
    sIcon: Int? = null,
    sIconDes: String? = null,
    enabled: Boolean = true,
    text: String = "",
    onclick: () -> Unit = {}
) {
    OutlinedRoundedButton(
        colors = ButtonDefaults.outlinedButtonColors(
            MaterialTheme.colors.surface,
            MaterialTheme.colors.onSurface,
            MaterialTheme.colors.onSurface
        ),
        enabled = enabled,
        lIcon = sIcon,
        lIconDes = sIconDes,
        contentColor = MaterialTheme.colors.onPrimary,
        text = text,
        shape = RoundedCornerShape(percent = 50),
        onclick = onclick,
    )
}

@Composable
fun SCircleCornerButton(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    @DrawableRes
    lIcon: Int? = null,
    lIconDes: String? = null,
    enabled: Boolean = true,
    text: String = "",
    onclick: () -> Unit = {}
) {
    RoundedButton(
        modifier = modifier,
        contentModifier = contentModifier,
        colors = buttonColors(
            MaterialTheme.colors.onSurface,
            MaterialTheme.colors.onSurface,
            MaterialTheme.colors.onSurface
        ),
        enabled = enabled,
        lIcon = lIcon,
        lIconDes = lIconDes,
        contentColor = MaterialTheme.colors.onPrimary,
        text = text,
        shape = RoundedCornerShape(percent = 50),
        onclick = onclick,
    )
}

@Composable
private fun buttonColors(
    enableColor: Color = MaterialTheme.colors.primary,
    disableColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary
): ButtonColors {
    return object : ButtonColors {
        @Composable
        override fun backgroundColor(enabled: Boolean): State<Color> {
            return rememberUpdatedState(
                if (enabled) enableColor else disableColor.copy(alpha = 0.5f)
            )
        }

        @Composable
        override fun contentColor(enabled: Boolean): State<Color> {
            return rememberUpdatedState(contentColor)
        }
    }
}

@Composable
private fun RoundedButton(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    colors: ButtonColors = buttonColors(),
    enabled: Boolean = true,
    @DrawableRes
    lIcon: Int? = null,
    lIconDes: String? = null,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    text: String = "Button",
    shape: Shape = RoundedCornerShape(percent = 25),
    onclick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        shape = shape,
        colors = colors, enabled = enabled,
        onClick = onclick
    ) {
        Row(modifier = contentModifier) {
            if (lIcon != null) {
                Icon(
                    painter = painterResource(id = lIcon),
                    contentDescription = lIconDes,
                    tint = contentColor,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            }
            Text(text = text, color = contentColor)
        }
    }
}

@Composable
private fun OutlinedRoundedButton(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    colors: ButtonColors = buttonColors(),
    enabled: Boolean = true,
    @DrawableRes
    lIcon: Int? = null,
    lIconDes: String? = null,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    text: String = "Button",
    shape: Shape = RoundedCornerShape(percent = 25),
    onclick: () -> Unit = {}
) {
    OutlinedButton(
        modifier = modifier,
        shape = shape,
        colors = colors, enabled = enabled,
        onClick = onclick
    ) {
        Row(modifier = contentModifier) {
            if (lIcon != null) {
                Icon(
                    painter = painterResource(id = lIcon),
                    contentDescription = lIconDes,
                    tint = contentColor,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
            }
            Text(text = text, color = contentColor)
        }
    }
}




