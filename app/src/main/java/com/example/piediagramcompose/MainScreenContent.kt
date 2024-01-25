package com.example.piediagramcompose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.piediagramcompose.animationScrollflags.ExitUntilCollapsedState
import com.example.piediagramcompose.mockData.populateList
import com.example.piediagramcompose.ui.theme.PieDiagramComposeTheme

val MinToolbarHeight = 96.dp
val MaxToolbarHeight = 276.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenContent() {
    val marioToolbarHeightRange = with(LocalDensity.current) {
        MinToolbarHeight.roundToPx()..MaxToolbarHeight.roundToPx()
    }
    val toolbarState = rememberSaveable(saver = ExitUntilCollapsedState.Saver) {
        ExitUntilCollapsedState(marioToolbarHeightRange)
    }
    val scrollState = rememberScrollState()
    toolbarState.scrollValue = scrollState.value

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = {
            ContentMotionHandler(
                list = populateList(),
                columns = 1,
                selectedItemIndex = 1,
                modifier = Modifier.fillMaxSize(),
                scrollState = scrollState,
                progress = toolbarState.progress
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreenContent() {
    PieDiagramComposeTheme {
        MainScreenContent()
    }
}