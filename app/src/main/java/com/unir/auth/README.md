# Configuración autentificación

El núcleo de la autentificación se gestiona a través de *com.auth.security* con las siguientes clases:

- TokenManager: se encarga de manejar el almacenamiento, acceso y borrado del Access Token y el Refresh Token.
- AuthINterceptor: Añade el Access Token a todas las peticiones salientes.
- AuthRepository: Repositorio para lanzar las request relacionadas con el token.


**Flujo completo de autentificación**

- **Screens (vistas)** -> pantallas de login / sign up / logout.
- **ViewModels** -> Disponen el flujo de datos para enviarlos desde la pantalla hasta el UseCase.
- **UseCase**-> Aplica toda la lógica de negocio (validaciones o tomar decisiones condicionado a la interacción del cliente).
- **Repository** -> Accede / refresca el token y lanza la petición al Service. 
- **Service** -> Únicamente contiene los endpoints dentro de la API.


## AuthRepositoryImpl

El AuthRepositoryImpl implementa la interfaz disponible en domain.repository.AuthRepository 
con los métodos básicos de autentificación (login, signup, refresh token y logout).

El AuthRepository debe declararse correctamente dentro de la Inyección de Dependencias correspondiente.


## TokenManager

Token Manager es un caso único en la inyección de dependencias, ya que está anotado como @Inject, lo cuál quiere decir que se puede inyectar automáticamente, 
pero sin que sea necesario pasarlo por un módulo de Inyección de Dependencias (es decir, se puede inyectar directamente, sin hacer nada más).

Hilt detecta que TokenManager tiene un constructor con @Inject y que su única dependencia es Context,
que también es proporcionada por Hilt (a través de @ApplicationContext). 
Por lo tanto, Hilt puede crear automáticamente una instancia de TokenManager cuando sea necesario,
sin que tú tengas que definir un @Provides o @Binds explícito en tu módulo.


## AuthInterceptor (¿mover al módulo de Network y sacar de Auth?)

Añade el Token a las cabeceras de todas las peticiones salientes. Esto sucede en la raíz del proyecto (com.di.NetworkModule).

Para que el AuthInterceptor pueda funcionar, debemos añadirlo a la Inyección de Dependencias (DI) **en este momento está dentro de auth.di.AuthModule**.
Tras añadirlo, ahora el AuthInterceptor se puede inyectar en el Retrofit e incluir la cabecera a todas las peticiones salientes.

Quedaría de la siguiente manera:
1. Añadimos AuthInterceptor al DI con su única dependencia (Token Manager), que como decíamos anteriormente, puede ser inyectada automáticamente, sin incluirla en el módulo de DI.
2. Añadimos el AuthInterceptor a todas las peticiones salientes de provideOkHttpClient (raíz del proyecto com.di.NetworkModule).
3. Añadimos Retrofit, que ahora utiliza a su vez el provideOKHttpClient que ya incluye las cabeceras con token.

*com.di.NetworkModule*
```

    @Singleton
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        return AuthInterceptor(tokenManager)
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) 
            .readTimeout(30, TimeUnit.SECONDS)    
            .writeTimeout(30, TimeUnit.SECONDS)   
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

```

TRas seguir todos estos pasos, la autentificación y la inclusión de tokens ya funciona correctamente.