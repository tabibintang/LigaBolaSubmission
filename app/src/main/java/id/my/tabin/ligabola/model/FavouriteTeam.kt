package id.my.tabin.ligabola.model

data class FavouriteTeam(val id: Long?, val idTeam: String?) {

    companion object {
        const val TABLE_FAVOURITE_TEAM: String = "TABLE_FAVOURITE_TEAM"
        const val ID: String = "ID_"
        const val ID_TEAM: String = "ID_TEAM"
    }
}