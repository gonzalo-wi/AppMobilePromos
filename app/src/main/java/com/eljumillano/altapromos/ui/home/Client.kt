package com.eljumillano.altapromos.ui.home

data class Client(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    val locality: String = "",
    val reparto: String = "",
    val phone: String = "",
    val email: String = "",
    val time: String = "",
    val status: ClientStatus = ClientStatus.NEW,
    val timestamp: Long = System.currentTimeMillis()
)

enum class ClientStatus {
    NEW,
    PENDING,
    COMPLETED
}

