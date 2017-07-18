package yogispark.yoginote.Helpers

import org.jetbrains.anko.db.RowParser
import yogispark.yoginote.Models.Task

/**
 * Created by yogesh on 16/7/17.
 */
class TaskRowParser : RowParser<Task> {
    override fun parseRow(columns: Array<Any?>): Task {
        return Task(columns[0] as Long,
                columns[1] as String,
                columns[2] as Long,
                columns[3] as Long,
                columns[4] as Number,
                columns[5] as Number,
                columns[6] as String)
    }

}