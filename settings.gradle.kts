pluginManagement {
    repositories {
        google {
            google()
            mavenCentral()
            gradlePluginPortal()
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KotlinRoleApp"
include(":app")
 