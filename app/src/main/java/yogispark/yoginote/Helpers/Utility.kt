package yogispark.yoginote.Helpers

import java.text.DateFormat
import java.util.*

/**
 * Created by yogesh on 16/7/17.
 */
class Utility{

    companion object {
        fun getLongDate(date: String): Long{
            return System.currentTimeMillis()
        }

        fun getCurrentDateLong(): Long {
            return System.currentTimeMillis()
        }
    }

}