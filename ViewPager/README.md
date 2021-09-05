# AndroidKotlin
Desarrollo de Android con Kotlin

Ejemplo simple del uso de los siguientes elementos:
* ViewPager
* TabLayout
* Fragments
* Listeners
* ViewBinding


Importante: para que el viewBinding funcione no se debe olvidar agregar el siguiente código en el build.gradle a nivel app (:app)

buildFeatures{
    viewBinding true //activa el enlace de vistas
}

Compilado para:
minSdk 21
targetSdk 31