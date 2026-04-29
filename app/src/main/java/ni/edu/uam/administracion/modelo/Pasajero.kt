package ni.edu.uam.administracion.modelo

class Pasajero(
    private var nombre: String,
    private var apellido: String,
    private var edad: Int
) {
    private val numeroPasajero: String = generarNumeroPasajero()
    private var foto: String? = null

    fun setFoto(foto: String) {
        this.foto = foto
    }

    fun getFoto(): String? {
        return foto
    }

    fun getNumeroPasajero(): String {
        return numeroPasajero
    }


    fun getNombre(): String {
        return nombre
    }

    fun getEdad(): Int {
        return edad
    }

    fun getApellidos(): String {
        return apellido
    }

    fun DarDatosPasajero(): String{
        return "No. Pasajero: $numeroPasajero\nNombre: $nombre\nApellido: $apellido\nEdad: $edad"
    }

    companion object {
        private var contador = 1893

        private fun generarNumeroPasajero(): String {
            contador += 1
            return "No-#$contador"
        }
    }
}