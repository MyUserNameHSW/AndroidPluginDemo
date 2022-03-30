package com.hsw.plugin.timeout

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

/**
 * @author: hsw
 * @date: 2022/3/29
 * @desc:
 */
class MethodTimerPlugin implements Plugin<Project> {

    public static List<MethodTimerEntity> METHOD_TIMER_LIST

    @Override
    void apply(Project project) {
        def android = project.extensions.findByType(AppExtension)
        android.registerTransform(new MethodTimerTransform())
        def statisticExtension = project.extensions.create('timer', MethodExtension)
        project.afterEvaluate {
            METHOD_TIMER_LIST = new ArrayList<>()
            def methodTimer = statisticExtension.getMethodTimer()
            if (methodTimer != null) {
                methodTimer.each { Map<String, Object> map ->
                    MethodTimerEntity entity = new MethodTimerEntity()
                    if (map.containsKey("time")) {
                        entity.time = map.get("time")
                    }
                    if (map.containsKey("owner")) {
                        entity.owner = map.get("owner")
                    }
                    METHOD_TIMER_LIST.add(entity)
                }
            }
        }

    }

}
