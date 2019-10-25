package net.oxyce.handlertest

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val twoTV = findViewById<TextView>(R.id.two_value)
        val fiveTV = findViewById<TextView>(R.id.five_value)
        val sixTV = findViewById<TextView>(R.id.six_value)
        val nineTV = findViewById<TextView>(R.id.nine_value)


        // WITH MESSAGES
        val mainMessagesHandler = object: Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    2 -> twoTV.text = (twoTV.text.toString().toInt() + 1).toString()
                    5 -> fiveTV.text = (fiveTV.text.toString().toInt() + 1).toString()
                    6 -> sixTV.text = (sixTV.text.toString().toInt() + 1).toString()
                    9 -> nineTV.text = (nineTV.text.toString().toInt() + 1).toString()
                }
            }
        }
        TimeHandlerThread("FIVE_SECONDS", 5000, mainMessagesHandler).start()
        TimeHandlerThread("SIX_SECONDS", 6000, mainMessagesHandler).start()
        TimeHandlerThread("NINE_SECONDS", 9000, mainMessagesHandler).start()


        // WITH RUNNABLES
        val mainRunnablesHandler = Handler()

        object: HandlerThread("TWO_SECONDS") {
            override fun run() {
                while (true) {
                    Thread.sleep(2000)
                    mainRunnablesHandler.post {
                        twoTV.text = (twoTV.text.toString().toInt() + 1).toString()
                    }
                }
            }
        }.start()
    }

    class TimeHandlerThread(name: String,
                            val millis: Long,
                            val handler: Handler) : HandlerThread(name) {

        override fun run() {
            while (true) {
                Thread.sleep(millis)
                val msg = Message()
                msg.what = (millis / 1000).toInt()
                handler.sendMessage(msg)
            }
        }
    }
}
