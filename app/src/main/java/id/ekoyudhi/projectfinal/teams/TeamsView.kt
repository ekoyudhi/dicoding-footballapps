package id.ekoyudhi.projectfinal.teams

import id.ekoyudhi.projectfinal.model.Team

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data : List<Team>?)
}