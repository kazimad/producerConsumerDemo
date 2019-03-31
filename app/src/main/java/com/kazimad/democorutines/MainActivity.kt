package com.kazimad.democorutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyCommunicator {

    override fun onMessage(message: String) {
        updateUi(message)
    }

    private var buffer = Buffer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buffer.setCommunicator(this)
        createConsumerProducer()
        button.setOnClickListener {
            buffer.produceTask()
        }

    }

    private fun createConsumerProducer() {
        // Create producer thread
        val t1 = Thread(Runnable {
//            try {
//                buffer.produceTask()
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
        })

        val t2 = Thread(Runnable {
            try {
                buffer.consumeTask()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        })
        t1.start()
        t2.start()

//        t1.join()
//        t2.join()
    }


    private fun updateUi(message: String) {
        val snackbar = Snackbar
            .make(root, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
}
