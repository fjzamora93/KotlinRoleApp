package com.unir.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NavigationViewModel : ViewModel() {

    // Mantener un stack de rutas
    private val _routeStack = mutableListOf<String>()
    val routeStack: List<String> get() = _routeStack

    private val _currentRoute = MutableStateFlow<String?>(null)
    val currentRoute: StateFlow<String?> = _currentRoute

    private val _navTitle = MutableStateFlow<String?>(null)
    val navTitle: StateFlow<String?> = _navTitle

    private val _navigationEvent = MutableLiveData<NavigationEvent?>()
    val navigationEvent: LiveData<NavigationEvent?> = _navigationEvent

    fun navigate(route: String) {
        _routeStack.add(route)
        _navigationEvent.value = NavigationEvent.Navigate(route)
        _currentRoute.value = route
        setNavTitle()

    }

    // TODO : DEFINIR EL NOMBRE DE CADA SECCIÓN DE PANTALLA DEPENDIENDO DE LA RUTA
    private fun setNavTitle() {
        when( _currentRoute.value) {
            "UserProfileScreen" -> _navTitle.value = "Perfil de Usuario"
            null -> _navTitle.value = "sección sin título, por qué??"
            else -> _navTitle.value = _currentRoute.value
        }
    }


    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }



    /**
     * Método para navegar hacia atrás. Accede al stack de rutas que hemos visitado eliminando la ruta actual,
     * guardando justo la anterior y navegando hacia esta última.
     *
     * Si no hay más rutas que visitar porque no hay una anterior, simplemente navegamos a la pantalla principal.
     * */
    fun goBack() {
        if (_routeStack.size > 1) {
            var currentRoute = _routeStack.removeAt(_routeStack.size - 1)
            var previousRoute = _routeStack.last()

            _routeStack.remove(currentRoute)
            _navigationEvent.value = NavigationEvent.NavigateAndPopUp(
                currentRoute,
                previousRoute,
                inclusive = false
            )

            println("La ruta ANTERIOR es... $previousRoute El tamaño es : ${_routeStack.size}")
            _navigationEvent.value = NavigationEvent.Navigate(previousRoute)
        } else {
            this.navigate(ScreensRoutes.AdventureMainScreen.route)
            println("No hay más pantallas para retroceder.")
        }
    }

}

