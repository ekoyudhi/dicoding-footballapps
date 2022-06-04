package id.ekoyudhi.projectfinal.matches

import id.ekoyudhi.projectfinal.model.EventLeague

interface MatchesView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data : List<EventLeague>?)
}