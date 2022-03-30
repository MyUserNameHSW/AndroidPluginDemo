package com.hsw.classinvokeplugin

import org.junit.Test

import org.junit.Assert.*
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val newClass = File("build/generated/source/buildConfig/debug/com/hsw/classinvokeplugin/MyClass.class")
        if (newClass.exists()) {
            newClass.delete()
        }
        if (newClass.parentFile?.exists() == false) {
            newClass.parentFile?.mkdirs()
        }
        newClass.createNewFile()
    }
}