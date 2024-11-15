package com.example.retrofit.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.retrofit.RetrofitApplication
import com.example.retrofit.data.Repository
import com.example.retrofit.model.Animal
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AppUiState{
    data class Success(val data:List<Animal>):AppUiState
    object Error:AppUiState
    object Loading:AppUiState
}

class AppViewModel(
    private val repository: Repository
): ViewModel(){
    var appUiState: AppUiState by mutableStateOf(AppUiState.Loading)
        private set

    init{
        getInfo()
    }

    fun getInfo(){
        viewModelScope.launch {
            appUiState = AppUiState.Loading
            appUiState = try{
                val data =repository.getInfo()
                AppUiState.Success(data)
            }catch (e: IOException){
                AppUiState.Error
            }catch (e: HttpException){
                AppUiState.Error
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RetrofitApplication)
                val repository = application.container.repository
                AppViewModel(repository = repository)
            }
        }
    }
}