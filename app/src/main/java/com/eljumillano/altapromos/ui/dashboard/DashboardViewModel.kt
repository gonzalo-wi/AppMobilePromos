package com.eljumillano.altapromos.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _localidades = MutableLiveData<List<String>>(emptyList())
    val localidades: LiveData<List<String>> = _localidades

    private val _loadingLocalidades = MutableLiveData(false)
    val loadingLocalidades: LiveData<Boolean> = _loadingLocalidades

    fun cargarLocalidadesPorReparto(nroReparto: String) {
        if (nroReparto.isBlank()) {
            _localidades.value = emptyList()
            return
        }
        _loadingLocalidades.value = true
        viewModelScope.launch(Dispatchers.IO) {
            // Reemplazar por llamada real a servicio
            val data = LocalidadesRepository.obtenerLocalidades(nroReparto)
            _localidades.postValue(data)
            _loadingLocalidades.postValue(false)
        }
    }
}

object LocalidadesRepository {
    // Simulación: devolver localidades diferentes según nro de reparto
    fun obtenerLocalidades(nroReparto: String): List<String> = when (nroReparto.trim()) {
        "1" -> listOf("CABA", "Avellaneda", "Lanús")
        "2" -> listOf("Morón", "Ituzaingó", "Merlo")
        "3" -> listOf("San Isidro", "Vicente López", "San Martín")
        else -> listOf("General", "Zona 1", "Zona 2")
    }
}
