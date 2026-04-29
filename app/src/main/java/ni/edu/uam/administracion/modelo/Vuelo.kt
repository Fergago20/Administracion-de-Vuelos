package ni.edu.uam.administracion.modelo

@Suppress("unused")
class Vuelo(private val numeroVuelo: String,
    private var aerolinea: String,
    private var destino: String,
    private var tipViaje: String) {
    private var listaPasajeros: MutableList<Pasajero> = mutableListOf()


    companion object{
        val aerolineasDisponibles = listOf("Avianca", "Copa Airlines", "American Airlines", "Delta Air Lines")
        val destinosDisponibles = listOf("Nueva York", "Miami", "Los Ángeles", "Chicago")
        val tiposDeViaje = listOf("Viaje", "Carga", "Emergencia")
    }

    fun AgregarPasajero(pasajero: Pasajero) {
        listaPasajeros.add(pasajero)
    }

    fun EliminarPasajero(pasajero: Pasajero) {
        listaPasajeros.remove(pasajero)
    }

    fun getNumeroVuelo(): String {
        return numeroVuelo
    }

    fun getAerolinea(): String {
        return aerolinea
    }

    fun getDestino(): String {
        return destino
    }

    fun getTipoViaje(): String {
        return tipViaje
    }

    fun DarDatosVuelo(): String{
        return "Numero de vuelo: $numeroVuelo\nAerolinea: ${getAerolinea()}\nDestino: ${getDestino()}\nTipo de viaje: ${getTipoViaje()}"
    }

    fun DarListaPasajeros(): MutableList<Pasajero> {
        return listaPasajeros
    }

    fun TienePasajeros(): Boolean {
        return listaPasajeros.isNotEmpty()

    }


}