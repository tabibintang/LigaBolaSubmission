package id.my.tabin.ligabola.api

import id.my.tabin.ligabola.BuildConfig.BASE_URL
import id.my.tabin.ligabola.BuildConfig.TSDB_API_KEY

object TheSportDBApi {
    fun getDetailLeague(league: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupleague.php?id=" + league
    }

    fun getTeams(league: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/search_all_teams.php?l=" + league
    }

    fun getTeamDetail(teamName: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/searchteams.php?t=" + teamName
    }

    fun getNextMatch(idLeague: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/eventsnextleague.php?id=" + idLeague

    }

    fun getPrevMatch(idLeague: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/eventspastleague.php?id=" + idLeague
    }

    fun getSearchMatch(search: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/searchevents.php?e=" + search
    }

    fun getMatchDetail(idEvent: String?): String {
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/lookupevent.php?id=" + idEvent
    }

    fun getStandingList(idLeague: String?):String{
        return BASE_URL + "api/v1/json/$TSDB_API_KEY/lookuptable.php?l=" + idLeague
    }

}