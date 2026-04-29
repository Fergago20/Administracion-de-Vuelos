package ni.edu.uam.administracion.modelo

@Suppress("unused")
class Cola {
    private val vuelos: MutableList<Vuelo> = mutableListOf()

    fun encolar(vuelo: Vuelo) {
        if (vuelo.getTipoViaje().equals("Emergencia", ignoreCase = true)) {
            AgregarAlInicio(vuelo)
        } else {
            vuelos.add(vuelo)
        }
    }

    fun desencolar(): Boolean {
        if (!estaVacia()) {
            val vuelo = vuelos[0]
            if(vuelo.TienePasajeros()){
                vuelos.removeAt(0)
                return true
            }
            return false
        }
        return false
    }

    fun estaVacia(): Boolean {
        return vuelos.isEmpty()
    }

    fun tamano(): Int {
        return vuelos.size
    }

    fun AgregarAlInicio(vuelo: Vuelo) {
        vuelos.add(0, vuelo)
    }

    fun DarLista(): MutableList<Vuelo> {
        return vuelos

    }
}