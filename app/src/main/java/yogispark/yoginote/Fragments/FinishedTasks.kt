package yogispark.yoginote.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.coroutines.experimental.selects.select
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.onUiThread
import yogispark.yoginote.Adapters.ViewTasksRecyclerAdapter
import yogispark.yoginote.Helpers.TaskRowParser
import yogispark.yoginote.Helpers.database
import yogispark.yoginote.Models.Task

import yogispark.yoginote.R


/**
 * A simple [Fragment] subclass.
 * Use the [FinishedTasks.newInstance] factory method to
 * create an instance of this fragment.
 */
class FinishedTasks : Fragment() {

    var adapter: ViewTasksRecyclerAdapter? = null
    var recyclerView: RecyclerView? = null
    var taskList: List<Task>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        retainInstance = true
        val view = inflater!!.inflate(R.layout.fragment_finished_tasks, container, false)
        recyclerView = view.findViewById(R.id.finished_tasks_recyclerview) as RecyclerView?
        recyclerView?.layoutManager = LinearLayoutManager(context)

        loadData()
        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment FinishedTasks.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): FinishedTasks {
            val fragment = FinishedTasks()
            val args = Bundle()
            /* args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
            fragment.arguments = args
            return fragment
        }
    }

    fun loadData(){

        if(taskList != null){
            recyclerView?.adapter = adapter
            Toast.makeText(context, "Data already loaded", 2000).show()
        }else{
            doAsync {
                var parser = TaskRowParser()
                context.database.use {
                    taskList = select("tasks", "_id", "TASK", "TIMESTAMP", "EXPIRY", "FINISHED", "REPEAT", "ALARMTONE")
                            .whereArgs("( FINISHED = {finished})", "finished" to 1)
                            .orderBy("TIMESTAMP", SqlOrderDirection.DESC).parseList(parser)
                }

                onUiThread {
                    adapter = ViewTasksRecyclerAdapter(taskList)
                    recyclerView?.adapter = adapter
                    Toast.makeText(context, "Data loaded", 2000).show()
                }
            }
        }

    }

}// Required empty public constructor
