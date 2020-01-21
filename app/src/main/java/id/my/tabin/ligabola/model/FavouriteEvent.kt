package id.my.tabin.ligabola.model

data class FavouriteEvent(val id: Long?, val idEvent: String?) {

    companion object {
        const val TABLE_FAVOURITE_EVENT: String = "TABLE_FAVOURITE_EVENT"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
    }
}