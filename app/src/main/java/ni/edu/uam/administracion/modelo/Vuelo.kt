package ni.edu.uam.administracion.modelo

class Vuelo(private val numeroVuelo: String,
    private var aerolinea: String,
    private var destino: String) {
    private var listaPasajeros: MutableList<Pasajero> = mutableListOf()

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

    fun DarDatosVuelo(): String{
        return "Numero de vuelo: $numeroVuelo\nAerolinea: $aerolinea\nDestino: $destino"
    }

    fun DarListaPasajeros(): MutableList<Pasajero> {
        return listaPasajeros
    }

    fun TienePasajeros(): Boolean {
        return listaPasajeros.isNotEmpty()

    }
}