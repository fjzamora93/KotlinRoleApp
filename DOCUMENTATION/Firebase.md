# Utilización de Firebase


# 1: Instalación de dependencias

Instalamos todas las dependencias en el graddle, a nivel de aplicación y a nivel de proyecto. Añadimos los plugins y librerías  necesarias dentro de libs.version.


Adicionalmente, nos descargaremos el documento google-services.json y lo dejamos dentro de la carpeta de app del proyecto.


# 2. Añadir el context a MyApplication

Se hace para tener un contexto global accesible en toda la aplicación. Al asignar context = this en onCreate(), se permite acceder al contexto de la aplicación desde cualquier parte del código sin necesidad de pasarlo como parámetro. Esto es útil para Firebase y otras configuraciones que requieren un contexto persistente.

```
@HiltAndroidApp
class MyApplication: Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}

```



## Configuración del NavGraph (para FirebaseAuth)

En el caso de que vayamos a utilizar el sistema de AUtentificación de Firebase, será necesario pasar como parámetro el auth dentro el NavGraph a las vistas que necesiten dicha autentificación (generalmente login y signup).


```
@Composable
fun NavGraph(
    navHostController: NavHostController,
    auth: FirebaseAuth
) {

    NavHost(navController = navHostController, startDestination = "home") {
        composable("initial") {
            InitialScreen(navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignUp = { navHostController.navigate("signUp") })
        }
        composable("logIn") {
            LoginScreen(auth){ navHostController.navigate("home") }
        }
        composable("signUp") {
            SignUpScreen(auth)
        }
        composable("home"){
            HomeScreen()
        }
    }
}
```




## Crear repositorio y acceder a los datos

A la hora de trabajar con Firebase, tendremos acceso a dos tipo sde bases de datos:

**Firebase.database**: es la Realtime basada en documetos de json.
**Firebase.firestore**: Es la base de datos basada en documentos.

Por lo general, utilizaremos el FIrebase.firestore, pero existe como opción el otro sistema.


```

class SceneRepository(private val firebaseConfigManager: FirebaseConfigManager) {

    private val database = Firebase.database
    private var db: FirebaseFirestore = Firebase.firestore

```


## Llamar al repositoro desde el ViewMOdel o UseCase

A partir de aquí, todo es tan sencillo como simplemente llamar al repositorio y acceder a los datos cuando lo necesitemos.


```

@HiltViewModel
class SceneViewModel @Inject constructor(
    private val sceneRepository: SceneRepository // Inyectamos el repositorio
) : ViewModel() {

    private val _scene = MutableStateFlow<List<Scene>>(emptyList())
    val scene: StateFlow<List<Scene>> = _scene

    init {
        loadData()
    }

    // Método para agregar una nueva escena
    fun addScene(scene: Scene) {
        _scene.value = emptyList() // Opcional: se puede mostrar un estado vacío mientras se realiza la operación
        viewModelScope.launch {
            val result = sceneRepository.addScene(scene)
            println(result)
        }
    }

    // Método para cargar los datos
    private fun loadData() {
        viewModelScope.launch {
            val result = sceneRepository.getScenes()
            result.onSuccess { scenes ->
                _scene.value = scenes
                println(scenes)
                Log.d("SceneViewModel", "Datos cargados correctamente ${_scene.value}")
            }.onFailure { exception ->
                Log.e("SceneViewModel", "Error al cargar los datos $exception")
            }

        }
    }
}


```


## Pasos adicionales

Aunque aún no lo vamos a utilizar, es posible que en cualquier momento queramos hacer alguna configuración adicional de FireBase. Para ello, disponemos de la clase FireBaseConfigManager, 
dentro de com.data


```
class FirebaseConfigManager {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig.apply {
        setConfigSettingsAsync(remoteConfigSettings { minimumFetchIntervalInSeconds = 30 })
        fetchAndActivate()
    }

    fun getConfigValue(key: String): String {
        return remoteConfig.getString(key)
    }
}

```




```
// 

```





```
// 

```






```
// 

```






```
// 

```






```
// 

```






```
// 

```

