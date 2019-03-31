package com.kazimad.democorutines

import android.os.Handler
import android.util.Log
import java.util.*


/**
 * Created by kazimad on 3/31/2019.
 */

class Buffer : Object() {
    var list: LinkedList<Int> = LinkedList()
    var handler = Handler()
    lateinit var myCommunicator: MyCommunicator

    fun setCommunicator(communicator: MyCommunicator) {
        myCommunicator = communicator
    }

    @Throws(InterruptedException::class)
    fun produceTask(): String {
        synchronized(this) {
        val value = 0

            val result = "Producer produced-${list.size}"
            list.add(value)
            notify()
            Log.d("myLog", "produceTask ${list.size}")
            myCommunicator.onMessage("produceTask ${list.size}")
            return result

        }
    }

    @Throws(InterruptedException::class)
    fun consumeTask() {
        while (true) {
            synchronized(this) {
                while (list.size == 0) {
                    wait()
                }
                list.removeFirst()
                //for demonstration, in order to be able to have several jobs in list
                Thread.sleep(1000)

                Log.d("myLog", "Consumer consumed-${list.size}")
                myCommunicator.onMessage("Consumer consumed-${list.size}")

                if (list.size == 0) {

                    handler.postDelayed({
                        myCommunicator.onMessage("All tasks are done")
                        Log.d("myLog", "All tasks are done")
                    },333)

                }
            }
        }
    }
}