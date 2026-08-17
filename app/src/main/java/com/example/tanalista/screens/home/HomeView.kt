package com.example.tanalista.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tanalista.R
import com.example.tanalista.dsm.bottomsheet.composable.BottomSheetComponent
import com.example.tanalista.screens.Routes
import com.example.tanalista.screens.home.action.DeleteAction
import com.example.tanalista.screens.home.action.EditAction
import com.example.tanalista.screens.home.composable.ListComponentItem
import com.example.tanalista.screens.home.model.HomeUiState

@Composable
fun HomeView(
    viewModel: HomeViewModel,
    navController: NavController,
) {
    val uiState = viewModel.uiState.collectAsState().value

    Scaffold(
        topBar = {
            HeaderHome(
                modifier = Modifier
                    .background(colorResource(R.color.white))
                    .statusBarsPadding()
                    .padding(16.dp)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.listCreationScreen(0L))
                },
                containerColor = colorResource(R.color.floatingButton),
                contentColor = colorResource(R.color.white),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_plus),
                    contentDescription = stringResource(R.string.new_list)
                )
            }
        },
        content = { paddingValues ->
            ContentHome(
                state = uiState,
                onListClick = { listId ->
                    navController.navigate(Routes.cartScreen(listId))
                },
                onListLongClick = { listId ->
                    viewModel.showBottomSheet(listId)
                },
                modifier = Modifier
                    .background(colorResource(R.color.white))
                    .padding(paddingValues)
            )

            BottomSheetComponent(
                state = uiState.bottomSheetState,
                onDismissRequest = {
                    viewModel.dismissBottomSheet()
                },
                onBottomSheetButtonAction = { action ->
                    when (action) {
                        is EditAction -> {
                            navController.navigate(Routes.listCreationScreen(action.listId))
                            viewModel.dismissBottomSheet()
                        }
                        is DeleteAction -> {
                            viewModel.deleteList(action.listId)
                        }

                        else -> Unit
                    }
                }
            )
        }
    )
}

@Composable
fun HeaderHome(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = stringResource(R.string.home_greeting),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ContentHome(
    state: HomeUiState,
    onListClick: (Long) -> Unit,
    onListLongClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(R.string.my_lists),
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.listState, key = { it.id }) { list ->
                ListComponentItem(
                    listState = list,
                    onClick = { onListClick(list.id) },
                    onLongClick = { onListLongClick(list.id) }
                )
            }
        }
    }
}
