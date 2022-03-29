package com.hsw.classinvokeplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn).setOnClickListener {
            getMyName()
            test()
        }
    }

    private fun getMyName(): String {
        return "My Name Is $name"
    }

    companion object {
        @JvmStatic
        val name = "hsw"

        @JvmStatic
        private fun test() {
            Thread.sleep(10)
        }
    }
}