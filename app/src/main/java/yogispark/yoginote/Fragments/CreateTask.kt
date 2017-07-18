package yogispark.yoginote.Fragments

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import org.jetbrains.anko.custom.async
import yogispark.yoginote.Helpers.DatabaseOpenHelper
import yogispark.yoginote.Helpers.database
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.uiThread
import yogispark.yoginote.Helpers.Utility
import yogispark.yoginote.Models.Task

import yogispark.yoginote.R
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CreateTask.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CreateTask.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateTask : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null
    private var Database: DatabaseOpenHelper? = null
    private var Task: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {

        }
        Database = DatabaseOpenHelper(context)
        Task = Task(0,"", 0, 0, 0, 0, "");
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_create_task, container, false)
        var taskDetails = view.findViewById(R.id.task_details) as EditText
        var expiryDate = view.findViewById(R.id.expiryDate) as DatePicker
        var taskRepeatSwitch = view.findViewById(R.id.repeat_switch) as Switch
        var fab = view.findViewById(R.id.addTask) as FloatingActionButton

        taskRepeatSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            Task?.Repeat = if (isChecked)  1 else 0
        }

        fab.setOnClickListener { view ->
            Task?.Task = taskDetails.text.toString()
            Task?.TimeStamp = Utility.getCurrentDateLong()
            val day = expiryDate.dayOfMonth
            var month = expiryDate.month
            var year = expiryDate.year
            val date = "${day}/${month}/${year}"
            Task?.Expiry = Utility.getLongDate(date)

            addTask(Task)
        }

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        /*if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }*/
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment CreateTask.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CreateTask {
            val fragment = CreateTask()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }

    }

    fun addTask(task: Task?){

        doAsync{
            context.database.use{
                insert(context.database.TABLE,
                        "TASK" to task?.Task,
                        "TIMESTAMP" to task?.TimeStamp,
                        "EXPIRY" to task?.Expiry,
                        "FINISHED" to task?.Finished,
                        "REPEAT" to task?.Repeat,
                        "ALARMTONE" to task?.AlarmTone
                )
            }
        }
        onUiThread {
            Toast.makeText(context, "Task added", 2000).show()
        }
    }
}// Required empty public constructor
