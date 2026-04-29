package ni.edu.uam.administracion.modelo

class Pasajero(private var nombre: String,
               private var apellido: String,
               private var edad: Int){
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

    fun getApellidos(): String {
        return apellido
    }

    fun DarDatosPasajero(): String{
        return "Nombre: $nombre\nApellido: $apellido\nEdad: $edad"
    }
}