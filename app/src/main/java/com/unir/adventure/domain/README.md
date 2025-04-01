# Domain
En teoría es posible trabajar sin esta capa, o incluso crearla maś adelante una vez esté clara la estructura de este módulo de la aplicación.

Dentro del directorio de Domain se suele encontrar:
- **TOdos los interfaces** (aunque yo solo uso interfaces para el Repository, para poder ir más rápido).
- **UseCases:** lo estoy utilizando para todas las validaciones de los viewmodels -si es que requieren de lógica adicional.


## Flujo habitual

Service(remoto) o Dao(local) > Repository > UseCase > ViewModel > Screen.

EL UseCase crea una capa de abstracción adicional para encapsular cualquier procesamiento, validación o lógica que requieran los datos que vengan desde el REpository o que se estén actualizando.

## Flujo simplificado

Repository > ViewModel.

El viewModel invoca directamente al repositorio. Esto es posible -aunque no muy recomendado- si los datos parseados desde el REpository están listos para ser usados en el ViewModel.