package ni.edu.uam.administracion.modelo

class Cola {
    private val vuelos: MutableList<Vuelo> = mutableListOf()

    fun encolar(vuelo: Vuelo) {
        vuelos.add(vuelo)
    }

    fun desencolar(): Boolean {
        if (!estaVacia()) {
            var vuelo = vuelos[0]
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

    fun tamaño(): Int {
        return vuelos.size
    }

    fun AgregarAlInicio(vuelo: Vuelo) {
        vuelos.add(0, vuelo)
    }

    fun DarLista(): MutableList<Vuelo> {
        return vuelos

    }
}