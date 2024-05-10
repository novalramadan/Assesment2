package org.d3if3103.assesmant2.navigation


import org.d3if3103.assesmant2.ui.screen.KEY_ID_motor


sealed class Screen (val route: String) {
    data object  Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")

    data object  About: Screen ("aboutScreen")
    data object  Person: Screen ("personScreen")


    data object FormUbah: Screen("detailScreen/{$KEY_ID_motor}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}