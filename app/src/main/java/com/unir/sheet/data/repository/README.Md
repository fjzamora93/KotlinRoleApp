### PACKAGE DATA.LOCAL.REPOSITORY

Las clases del directorio Repository DEBEN implementar las interfaces que existen dentro de DOMAIN.REPOSITORY.
Esta es la forma de garantizar que todos los métodos tanto para conectar con la API como con la base de datos interna están CORRECTAMENTE IMPLEMENTADOS.

DE esta forma,dependiendo de si es LOCAL o REMOTE, ambas clases implementarán la misma interfaz, pero cada una lo hará de manera diferente,
dependiendo de si se harán conexiones a una API remota o a una base de datos SQLITE local.

POr su parte, la lógica para decidir si se conecta a la base de datos local o a la remota se llevará a cabo dentro de la capa de Repository.

EJEMPLO:

```
📁 data
    ├── 📁 local
    │   ├── CharacterDao.kt
    │   ├── CharacterLocalDataSource.kt
    │   ├── ...
    ├── 📁 remote
    │   ├── CharacterApiService.kt
    │   ├── CharacterRemoteDataSource.kt
    │   ├── ...
    ├── 📁 repository  ⬅️ **Aquí va el repositorio**
    │   ├── CharacterRepositoryImpl.kt
    ├── 📁 model
    │   ├── RolCharacter.kt

```