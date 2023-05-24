package com.voltacontrol.bluetooth.demo.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.voltacontrol.bluetooth.demo.ui.components.CircularCard
import com.voltacontrol.bluetooth.demo.ui.components.PRoundedButton
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme


@Composable
fun MainUI() {

    var parentSize by remember {
        mutableStateOf(IntSize.Zero)
    }
    with(LocalDensity.current) {
        Column(
            Modifier
                .fillMaxSize()
                .onGloballyPositioned { lc -> parentSize = lc.size },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CircularCard(
                modifier = Modifier
                    .padding(vertical = 64.dp)
                    .size(
                        (parentSize.width * 0.25)
                            .toInt()
                            .toDp()
                    ),
                contentPadding = 0.dp
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize(),
                    strokeCap = StrokeCap.Butt,
                    strokeWidth = (parentSize.width * 0.125).toInt().toDp()
                )
            }

            Row(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(0.1f))
                PRoundedButton(text = "Stop/Start", modifier = Modifier.weight(0.35f), contentModifier = Modifier.padding(vertical = 16.dp))
                Spacer(modifier = Modifier.weight(0.1f))
                PRoundedButton(text = "Balance", modifier = Modifier.weight(0.35f), contentModifier = Modifier.padding(vertical = 16.dp))
                Spacer(modifier = Modifier.weight(0.1f))
            }

            Text(
                modifier = Modifier
                    .padding(top = 64.dp)
                    .background(
                        MaterialTheme.colors.primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(16.dp)
                ,
                text = "#01"
            )

        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    BluetoothDemoTheme {
        MainUI()
    }
}



