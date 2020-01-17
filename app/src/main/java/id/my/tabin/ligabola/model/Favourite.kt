package id.my.tabin.ligabola.model

data class Favourite(val id: Long?, val idEvent: String?) {

    companion object {
        const val TABLE_FAVOURITE: String = "TABLE_FAVOURITE"
        const val ID: String = "ID_"
        const val ID_EVENT: String = "ID_EVENT"
    }
}