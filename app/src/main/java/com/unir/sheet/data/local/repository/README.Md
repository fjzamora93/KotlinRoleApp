### PACKAGE DATA.LOCAL.REPOSITORY

EL paquete Repository dentro de la carepta DATA tiene como objetivo implementar las interfaces que existen dentro de DOMAIN.REPOSITORY.

El objetivo es desacoplar la lógica de negocio, que estará dentro de los INTERFACES del REPOSITORY, de la conexión a datos específica,
que esa quedará definida dentro de la clase REPOSITORY.

DE esta forma,dependiendo de si es LOCAL o REMOTE, ambas clases implementarán la misma interfaz, pero cada una lo hará de manera diferente,
dependiendo de si se harán conexiones a una API remota o a una base de datos SQLITE local.

EJEMPLO:

domain
    |_repository
        |_CharacterRepository -> interfaces con los métodos que se deben implementar

data
    |_local
    |     |_repository
    |          |_LocalCharacterRepository -> implementación de la interfaz para la base local
    |
    |_remote
    |     |_repository
    |          |_RemoteCharacterRepository -> implementación para la base remota