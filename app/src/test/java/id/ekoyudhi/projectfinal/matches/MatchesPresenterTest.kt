package id.ekoyudhi.projectfinal.matches

import com.google.gson.Gson
import id.ekoyudhi.projectfinal.TestContextProvider
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.model.EventLeague
import id.ekoyudhi.projectfinal.model.EventLeagueResponse
import id.ekoyudhi.projectfinal.api.TheSportDBApi
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MatchesPresenterTest {

    @Mock
    private lateinit var view : MatchesView

    @Mock
    private lateinit var gson : Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MatchesPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchesPresenter(view, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun testGetNextEventLeagueList() {
        val events : MutableList<EventLeague> = mutableListOf()
        val response = EventLeagueResponse(events)
        val id = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextLeagueEvents(id)),
                EventLeagueResponse::class.java
        )).thenReturn(response)

        presenter.getNextEventLeagueList(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(events)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun testGetPastEventLeagueList() {
        val events : MutableList<EventLeague> = mutableListOf()
        val response = EventLeagueResponse(events)
        val id = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPastLeagueEvents(id)),
                EventLeagueResponse::class.java
        )).thenReturn(response)

        presenter.getPastEventLeagueList(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(events)
        Mockito.verify(view).hideLoading()
    }
}