package ni.edu.uam.administracion.modelo

class Pasajero(private var nombre: String,
               private var edad: Int,
               private var asiento: Int) {
    private var foto: String? = null

    fun setFoto(foto: String) {
        this.foto = foto
    }

    fun getFoto(): String? {
        return foto
    }


    fun getNombre(): String {
        return nombre
    }

    fun getEdad(): Int {
        return edad
    }

    fun getAsiento(): Int {
        return asiento
    }

    fun DarDatosPasajero(): String{
        return "Nombre: $nombre\nEdad: $edad\nAsiento: $asiento"
    }
}