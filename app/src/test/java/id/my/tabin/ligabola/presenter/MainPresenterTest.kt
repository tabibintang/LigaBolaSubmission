package id.my.tabin.ligabola.presenter

import com.google.gson.Gson
import id.my.tabin.ligabola.TestContextProvider
import id.my.tabin.ligabola.api.ApiRepository
import id.my.tabin.ligabola.model.League
import id.my.tabin.ligabola.model.Team
import id.my.tabin.ligabola.response.LeagueResponse
import id.my.tabin.ligabola.response.TeamResponse
import id.my.tabin.ligabola.view.MainView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    @Mock
    private lateinit var view: MainView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getDetailLeagueTest() {
        val leagueId = "4328"
        val leagues: MutableList<League> = mutableListOf()
        val teams: MutableList<Team> = mutableListOf()
        val responseLeague = LeagueResponse(leagues)
        val responseTeam = TeamResponse(teams)

        runBlocking {
            `when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            `when`(apiResponse.await()).thenReturn("")
            `when`(
                gson.fromJson("", LeagueResponse::class.java)
            ).thenReturn(responseLeague)
            `when`(
                gson.fromJson("", TeamResponse::class.java)
            ).thenReturn(responseTeam)

            presenter.getDetailLeague(leagueId)
            verify(view).showLoading()
            verify(view).showDetailLeague(leagues)
            verify(view).showTeamList(teams)
            verify(view).hideLoading()
        }
    }
}