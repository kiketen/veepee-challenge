package com.vp.list.viewmodel


sealed class ListNavigation {
    class Detail(val id: String) : ListNavigation()
}
