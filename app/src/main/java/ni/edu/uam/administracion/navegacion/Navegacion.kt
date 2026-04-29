package ni.edu.uam.administracion.navegacion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ni.edu.uam.administracion.vistas.* // Asegúrate de importar CargaScreen
import androidx.activity.compose.BackHandler

sealed class Screen {
	object Carga : Screen() // <-- 1. Añadimos la pantalla de carga
	object Home : Screen()
	object CrearVuelo : Screen()
	object CrearPasajero : Screen()
	object AdministrarVuelos : Screen()
	data class AdministrarPasajeros(val index: Int) : Screen()
}

@Composable
fun Navegacion() {
	// 2. Iniciamos en Screen.Carga para que sea lo primero que se vea
	val current = remember { mutableStateOf<Screen>(Screen.Carga) }

	BackHandler(enabled = current.value != Screen.Home && current.value != Screen.Carga) {
		current.value = Screen.Home
	}

	when (val screen = current.value) {
		// 3. Agregamos el caso para la pantalla de carga
		is Screen.Carga -> CargaScreen(onLoadingFinished = {
			current.value = Screen.Home
		})

		is Screen.Home -> HomeScreen(
			onCrearVuelo = { current.value = Screen.CrearVuelo },
			onCrearPasajero = { current.value = Screen.CrearPasajero },
			onAdministrarVuelos = { current.value = Screen.AdministrarVuelos }
		)

		is Screen.CrearVuelo -> CrearVueloScreen(onDone = { current.value = Screen.Home })

		is Screen.CrearPasajero -> CrearPasajeroScreen(
			onDone = { current.value = Screen.Home },
			onAdministrarPasajeros = { index -> current.value = Screen.AdministrarPasajeros(index) }
		)

		is Screen.AdministrarVuelos -> AdministrarVuelosScreen(
			onVerPasajeros = { index -> current.value = Screen.AdministrarPasajeros(index) },
			onDone = { current.value = Screen.Home }
		)

		is Screen.AdministrarPasajeros -> AdministrarPasajerosScreen(
			vueloIndex = screen.index,
			onDone = { current.value = Screen.AdministrarVuelos }
		)
	}
}