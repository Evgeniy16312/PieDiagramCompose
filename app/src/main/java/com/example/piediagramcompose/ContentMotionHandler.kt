package com.example.piediagramcompose

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.piediagramcompose.content.SalesList
import com.example.piediagramcompose.content.SalesListItem
import com.example.piediagramcompose.mockData.ListPreviewParameterProvider
import com.example.piediagramcompose.ui.theme.PieDiagramComposeTheme


@OptIn(ExperimentalMotionApi::class)
@Composable
fun ContentMotionHandler(
    list: List<SalesList>,
    columns: Int,
    selectedItemIndex: Int,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    progress: Float
) {
    val context = LocalContext.current

    val chunkedList = remember(list, columns) {
        list.chunked(columns)
    }

    // To include raw file, the JSON5 script file
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene_pie)
            .readBytes()
            .decodeToString()   //readBytes -> cuz we want motionScene in a String format
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        /**
         * bg-image
         **/

        Image(
            painter = painterResource(id = R.drawable.ic_badge),
            contentDescription = "Toolbar Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .layoutId("collapsing_box")
                .fillMaxWidth()
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = size.height / 3,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            alignment = BiasAlignment(0f, 1f - ((1f - progress) * 0.50f)),
        )

        /**
         * Text - Collapsing
         */

        Text(
            text = "R.string.collapsing_text_minion",
            color = Color.Red,
            modifier = Modifier
                .layoutId("motion_text")
                .zIndex(1f),
            fontSize = 14.sp
        )

        /**
         * Main image
         **/
        Image(
            painter = painterResource(id = R.drawable.ic_savings),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .layoutId("content_img")
                .clip(RoundedCornerShape(5.dp)),
            contentDescription = "Animating Mario Image"
        )

        /**
         * Grid
         **/
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .layoutId("data_content")
                .background(Color.Red),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(contentPadding.calculateTopPadding())
            )

            chunkedList.forEach { chunk ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(contentPadding.calculateStartPadding(LocalLayoutDirection.current))
                    )

                    chunk.forEach { listItem ->
                        SalesListItem(
                            item = listItem,
                            modifier = Modifier
                                .padding(2.dp)
                                .weight(1f),
                            onClick = {}
                        )

                    }

                    val emptyCells = columns - chunk.size
                    if (emptyCells > 0) {
                        Spacer(modifier = Modifier.weight(emptyCells.toFloat()))
                    }

                    Spacer(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(contentPadding.calculateEndPadding(LocalLayoutDirection.current))
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
        }

        /**
         * piranha flower
         **/
        Image(
            painter = painterResource(id = R.drawable.ic_surprize),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .layoutId("piranha_flower"),
            contentDescription = "Piranha Flower"
        )

        /**
         * piranha tunnel
         **/
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .layoutId("piranha_tunnel"),
            contentDescription = "Piranha Tunnel"
        )
    }
}

@Composable
fun GridItemHandlerPreview(
    @PreviewParameter(ListPreviewParameterProvider::class) list: List<SalesList>
) {
    PieDiagramComposeTheme {
        ContentMotionHandler(
            list = list,
            columns = 2,
            selectedItemIndex = 3,
            modifier = Modifier.fillMaxSize(),
            progress = 0.5f
        )
    }
}