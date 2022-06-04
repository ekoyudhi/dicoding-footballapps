package id.ekoyudhi.projectfinal.util

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class UtilsKtTest {

    @Test
    fun testReplaceSemiColonWithNewline() {
        val strFirst = "Satu; Dua"
        val strSecond = "Satu\n Dua"
        assertEquals(strSecond, replaceSemiColonWithNewline(strFirst))
    }


    @Test
    fun testToGMTFormat() {
        val strDate = "2018-09-10"
        val strTime = "19:00:00+0000"
        val strFull = "$strDate ${strTime.substring(0,8)}"
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        val mDate = formatter.parse(strFull)
        assertEquals(mDate, toGMTFormat(strDate, strTime))
    }

    @Test
    fun testGetAllLeagues() {
        val res = LinkedHashMap<String, String>()
        res["English Premier League"] = "4328"
        res["English League Championship"] = "4329"
        res["Scottish Premier League"] = "4330"
        res["German Bundesliga"] = "4331" //
        res["Italian Serie A"] = "4332" //
        res["French Ligue 1"] = "4334" //
        res["Spanish La Liga"] = "4335"
        assertEquals(res, getAllLeagues())
    }


}