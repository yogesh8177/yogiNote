package yogispark.yoginote.Models

/**
 * Created by yogesh on 16/7/17.
 */
class Task {

    public var ID: Long
    public var Task: String
    public var TimeStamp: Long
    public var Expiry: Long
    public var Finished: Number
    public var Repeat: Number
    public var AlarmTone: String

    constructor(id: Long,task: String, timeStamp: Long, expiry: Long, finished: Number, repeat: Number, alarmTone: String){
        ID = id
        Task = task;
        TimeStamp = timeStamp
        Expiry = expiry
        Finished = finished
        Repeat = repeat
        AlarmTone = alarmTone
    }
}