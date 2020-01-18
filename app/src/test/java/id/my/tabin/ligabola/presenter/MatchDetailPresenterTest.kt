package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.TestContextProvider
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.model.Event
import id.my.tabin.ligabola.model.League
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.response.EventResponse
import id.my.tabin.ligabola.response.LeagueResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.view.MainView
import id.my.tabin.ligabola.view.MatchDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {
    @Mock
    private lateinit var view: MatchDetailView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun getMatchDetail() {
        val idEvent = "602349"
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

            presenter.getMatchDetail(idEvent)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).showMatchDetail(events)
            Mockito.verify(view).hideLoading()
        }
    }
}