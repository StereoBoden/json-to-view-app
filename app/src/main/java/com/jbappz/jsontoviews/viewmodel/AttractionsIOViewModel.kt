package com.jbappz.jsontoviews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jbappz.jsontoviews.model.AppDescription
import com.jbappz.jsontoviews.repository.AttractionsIORepository
import com.jbappz.jsontoviews.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AttractionsIOViewModel: ViewModel() {
    // Would use a DI library like Dagger 2 or Koin in production
    private val repository = AttractionsIORepository()

    private val _appDescription = MutableStateFlow<Resource<AppDescription>>(Resource.loading(null))
    val appDescription: StateFlow<Resource<AppDescription>>
        get() = _appDescription

    fun getAppDescriptionData() {
        viewModelScope.launch {
            with(_appDescription) {
                emit(Resource.loading(null))
                emit(repository.getAppDescriptionData())
            }
        }
    }
}