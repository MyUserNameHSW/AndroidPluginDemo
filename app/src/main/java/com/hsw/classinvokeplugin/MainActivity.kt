package com.hsw.classinvokeplugin

import android.graphics.RenderEffect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.RenderScript
import android.widget.Button
import android.widget.Toast
import com.hsw.classinvokeplugin.databinding.ActivityMainBinding
import com.hsw.classinvokeplugin.fix.TestMethod
import java.io.File

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn).setOnClickListener {
            getMyName()
            test()
            toast("xxxxx")
        }


    }

    @FixMethod(desc = "getMyName")
    private fun getMyName(): String {
        return "My Name Is $name"
    }

    companion object {
        @JvmStatic
        val name = "hsw"

        @FixMethod(desc = "test")
        @JvmStatic
        private fun test() {
            Thread.sleep(10)
        }

        @FixMethod(desc = "toast")
        @JvmStatic
        fun toast(str: String) {

        }
    }
}