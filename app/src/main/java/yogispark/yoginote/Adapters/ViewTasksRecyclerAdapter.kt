package yogispark.yoginote.Adapters

import android.content.ContentValues
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.jetbrains.anko.db.update
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import yogispark.yoginote.Helpers.database
import yogispark.yoginote.Models.Task
import yogispark.yoginote.R
import yogispark.yoginote.ViewHolders.TaskListViewHolder

/**
 * Created by yogesh on 16/7/17.
 */
class ViewTasksRecyclerAdapter: RecyclerView.Adapter<TaskListViewHolder> {

    var taskList: List<Task>? = null

    constructor(_taskList: List<Task>?): super(){
        taskList = _taskList
    }

    override fun getItemCount(): Int {
        return if(taskList == null) 0 else taskList!!.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder?, position: Int) {
        val title = if (taskList!![position].Task.length > 20) "${taskList!![position].Task.subSequence(0, 17)}..." else taskList!![position].Task
        holder?.taskTitle?.text = title
        holder?.taskExpiry?.text = "Expiry: ${taskList!![position].Expiry.toString()}"
        holder?.taskFinish?.isChecked =  if(taskList!![position].Finished == 1L) true else false
        holder?.taskFinish?.setOnCheckedChangeListener { compoundButton, isChecked ->

            doAsync {
                holder?.taskFinish?.context?.database?.use{
                    update("tasks", "FINISHED" to if(isChecked) 1 else 0)
                            .whereArgs("(_id = {ID})", "ID" to taskList!![position].ID)
                            .exec()
                }
                uiThread {
                    Toast.makeText(holder?.taskFinish?.context, "Task status updated", 2000).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskListViewHolder{
        val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.task_list_view, parent, false)
        return TaskListViewHolder(view)
    }

}