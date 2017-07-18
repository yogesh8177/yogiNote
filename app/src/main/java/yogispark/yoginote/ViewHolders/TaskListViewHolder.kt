package yogispark.yoginote.ViewHolders

import android.content.ClipData
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import yogispark.yoginote.R

/**
 * Created by yogesh on 18/7/17.
 */
class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var taskTitle: TextView? = itemView.findViewById(R.id.task_details_view) as TextView?
    //var taskStatus: ImageView? = null
    //var taskRepeat: TextView? = null
    var taskExpiry: TextView? = itemView.findViewById(R.id.task_expiry_view) as TextView?
    var taskFinish: Switch? = itemView.findViewById(R.id.finish_switch) as Switch?


}