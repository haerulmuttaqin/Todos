pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
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
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "HaerulMuttaqin"
include(":app")
include(":core")
include(":features:todo_list:todo_list_data")
include(":features:todo_list:todo_list_domain")
include(":features:todo_list:todo_list_presentation")
include(":features:todo_detail:todo_detail_data")
include(":features:todo_detail:todo_detail_domain")
include(":features:todo_detail:todo_detail_presentation")
