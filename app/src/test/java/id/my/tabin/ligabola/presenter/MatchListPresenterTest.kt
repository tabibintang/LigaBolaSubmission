package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.TestContextProvider
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.model.Favourite
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.response.EventResponse
import id.my.tabin.ligabola.response.EventSearchResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.view.MatchDetailView
import id.my.tabin.ligabola.view.MatchListView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchListPresenterTest {
    @Mock
    private lateinit var view: MatchListView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: MatchListPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchListPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getMatchNextList() {
        val idLeague = "4328"
        val events: MutableList<Event> = mutableListOf()
        val teams: MutableList<Team> = mutableListOf()
        val responseEvent = EventResponse(events)
        val responseTeam = TeamResponse(teams)

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", EventResponse::class.java)
            ).thenReturn(responseEvent)
            Mockito.`when`(
                gson.fromJson("", TeamResponse::class.java)
            ).thenReturn(responseTeam)

            presenter.getMatchNextList(idLeague)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()
            Mockito.verify(view).showMatchList(events)
        }
    }

    @Test
    fun getMatchPrevList() {
        val idLeague = "4328"
        val events: MutableList<Event> = mutableListOf()
        val teams: MutableList<Team> = mutableListOf()
        val responseEvent = EventResponse(events)
        val responseTeam = TeamResponse(teams)

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", EventResponse::class.java)
            ).thenReturn(responseEvent)
            Mockito.`when`(
                gson.fromJson("", TeamResponse::class.java)
            ).thenReturn(responseTeam)

            presenter.getMatchPrevList(idLeague)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()
            Mockito.verify(view).showMatchList(events)
        }
    }

    @Test
    fun getMatchSearchList() {
        val searchString = "Chelsea"
        val events: MutableList<Event> = mutableListOf()
        val teams: MutableList<Team> = mutableListOf()
        val responseEvent = EventSearchResponse(events)
        val responseTeam = TeamResponse(teams)

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", EventSearchResponse::class.java)
            ).thenReturn(responseEvent)
            Mockito.`when`(
                gson.fromJson("", TeamResponse::class.java)
            ).thenReturn(responseTeam)

            presenter.getMatchSearchList(searchString)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()
            Mockito.verify(view).showMatchList(events)
        }
    }

    @Test
    fun getMatchFavouriteList() {
        val favourites: MutableList<Favourite> = mutableListOf()
        val events: MutableList<Event> = mutableListOf()
        val teams: MutableList<Team> = mutableListOf()
        val responseEvent = EventResponse(events)
        val responseTeam = TeamResponse(teams)

        favourites.add(Favourite(1,"602349"))
        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(
                gson.fromJson("", EventResponse::class.java)
            ).thenReturn(responseEvent)
            Mockito.`when`(
                gson.fromJson("", TeamResponse::class.java)
            ).thenReturn(responseTeam)

            presenter.getMatchFavouriteList(favourites)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).hideLoading()
            Mockito.verify(view).showMatchList(events)
        }
    }
}