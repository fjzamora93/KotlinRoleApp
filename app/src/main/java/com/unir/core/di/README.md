# Inyección de dependencias

La inyección de dependencias está centralizada para toda la aplicación y está estructurada por capas:

1. ApiServiceModulo: inyección para todos los servicios con los endpoints de la API.
2. AppModule: inyección de dependencias comunes para toda la aplicación.
3. DataBaseMOdule: inyección de los DAO.
4. NetworkMOdule: inyección de retrofit y la conexión a la API.
5. RepositoryModule: inyección de los repositorios.
6. UseCase: inyección de los casos de uso.