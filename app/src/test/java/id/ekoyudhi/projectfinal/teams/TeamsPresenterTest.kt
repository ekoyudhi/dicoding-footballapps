package id.ekoyudhi.projectfinal.teams

import com.google.gson.Gson
import id.ekoyudhi.projectfinal.TestContextProvider
import id.ekoyudhi.projectfinal.api.ApiRepository
import id.ekoyudhi.projectfinal.api.TheSportDBApi
import id.ekoyudhi.projectfinal.model.Team
import id.ekoyudhi.projectfinal.model.TeamResponse
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {

    @Mock
    private lateinit var view: TeamsView

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var gson: Gson

    private lateinit var presenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun testGetAllTeams() {
        val teams : MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val id = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getAllTeamsLeague(id)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getAllTeams(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }

}