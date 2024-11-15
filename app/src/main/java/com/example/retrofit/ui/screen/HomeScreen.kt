package com.example.retrofit.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.compose.RetrofitTheme
import com.example.retrofit.R
import com.example.retrofit.model.Animal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) {

        val viewModel: AppViewModel = viewModel(
            factory = AppViewModel.Factory
        )

        val uiState = viewModel.appUiState
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            when (uiState) {
                is AppUiState.Loading -> LoadingScreen()
                is AppUiState.Success -> SuccessScreen(uiState.data)
                is AppUiState.Error -> ErrorScreen(retryAction = viewModel::getInfo)
            }
        }
    }
}

@Composable
fun LoadingScreen(){
    Image(
        painter = painterResource(id = R.drawable.baseline_downloading_24), contentDescription = "Loading",
        modifier = Modifier.fillMaxWidth(0.25f)
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.baseline_error_outline_24), contentDescription = "Error",
        modifier = Modifier.fillMaxWidth(0.25f)
    )
    Button(onClick = retryAction) {
        Text(text = "Retry")
    }
}

@Composable
fun SuccessScreen(data: List<Animal>) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(24.dp, 0.dp)
            .fillMaxSize()
    ){
        items(
            items = data
        ){
            info -> Card(info = info)
        }
    }
}

@Composable
fun Card(
    modifier: Modifier = Modifier,
    info: Animal
) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clip(RoundedCornerShape(12.dp)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = info.name + " ( " + info.type + " )",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(12.dp)
        )
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(info.imgSrc)
                .crossfade(true)
                .build(),
            contentDescription = info.name,
            contentScale = ContentScale.FillWidth,
            error = painterResource(id = R.drawable.baseline_error_outline_24),
            placeholder = painterResource(id = R.drawable.baseline_downloading_24),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = info.description,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodySmall
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    RetrofitTheme {
        val mockData = List(10) {
            Animal(
                name = "Dog",
                type = "Domestic",
                description = "Lorem200",
                imgSrc = ""
            )
        }

        SuccessScreen(mockData)
        }
}

@Composable
fun HomeScreenPreview(){
    RetrofitTheme {
        HomeScreen()
    }
}