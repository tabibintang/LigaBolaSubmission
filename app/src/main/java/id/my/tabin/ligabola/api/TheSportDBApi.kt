package id.my.tabin.ligabola.api

import id.my.tabin.ligabola.BuildConfig.BASE_URL
import id.my.tabin.ligabola.BuildConfig.TSDB_API_KEY

object TheSportDBApi {
    fun getDetailLeague(league: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupleague.php?id=" + league
    }
//    fun getDetailLeague(league: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("lookupleague.php")
//            .appendQueryParameter("id", league)
//            .build()
//            .toString()
//    }

    fun getTeams(league: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/search_all_teams.php?l=" + league
    }

    //    fun getTeams(league: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("search_all_teams.php")
//            .appendQueryParameter("l", league)
//            .build()
//            .toString()
//    }
    fun getTeamDetail(teamName: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/searchteams.php?t=" + teamName
    }

    //    fun getTeamDetail(teamName: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("searchteams.php")
//            .appendQueryParameter("t", teamName)
//            .build()
//            .toString()
//    }
    fun getNextMatch(idLeague: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/eventsnextleague.php?id=" + idLeague

    }
//    fun getNextMatch(idLeague: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("eventsnextleague.php")
//            .appendQueryParameter("id", idLeague)
//            .build()
//            .toString()
//    }

    fun getPrevMatch(idLeague: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/eventspastleague.php?id=" + idLeague
    }

//    fun getPrevMatch(idLeague: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("eventspastleague.php")
//            .appendQueryParameter("id", idLeague)
//            .build()
//            .toString()
//    }


    fun getSearchMatch(search: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/searchevents.php?e=" + search
    }
//    fun getSearchMatch(search: String?): String {
//        val url = Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("searchevents.php")
//            .appendQueryParameter("e", search)
//            .build()
//            .toString()
//        return url
//    }

    fun getMatchDetail(idEvent: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupevent.php?id=" + idEvent
    }
//        fun getMatchDetail(idEvent: String?): String {
//        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
//            .appendPath("api")
//            .appendPath("v1")
//            .appendPath("json")
//            .appendPath(BuildConfig.TSDB_API_KEY)
//            .appendPath("lookupevent.php")
//            .appendQueryParameter("id", idEvent)
//            .build()
//            .toString()
//    }
}