package com.getcatch.sample.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.getcatch.sample.Constants.VIRTUAL_CARD_INTEGRATION_ENABLED
import com.getcatch.sample.DemoSettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DemoScaffold(viewModel: DemoSettingsViewModel, content: @Composable ColumnScope.() -> Unit) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetContent = { DemoSettingsSheet(viewModel) },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetElevation = 8.dp,
        sheetPeekHeight = 0.dp,
        scaffoldState = scaffoldState,
    ) { contentPadding ->
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content()
                Spacer(
                    Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                        .background(
                            if (VIRTUAL_CARD_INTEGRATION_ENABLED) MaterialTheme.colors.secondary
                            else MaterialTheme.colors.surface
                        )
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .wrapContentSize()
                    .padding(16.dp)
            ) {
                GlobalSettingsFAB(
                    onClick = { coroutineScope.launch { scaffoldState.bottomSheetState.expand() } }
                )
            }
        }
    }
}
