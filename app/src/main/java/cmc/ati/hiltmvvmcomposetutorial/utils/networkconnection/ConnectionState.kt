package cmc.ati.hiltmvvmcomposetutorial.utils.networkconnection

sealed class ConnectionState {
    data object Available : ConnectionState()
    data object Unavailable : ConnectionState()
}