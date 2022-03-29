package com.hsw.plugin.timeout

import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes;

/**
 * @author: hsw
 * @date: 2022/3/29
 * @desc:
 */
class MethodTimerClassVisitor extends ClassVisitor {

    String methodOwner

    MethodTimerClassVisitor(ClassVisitor classVisitor) {
        super(Opcodes.ASM7, classVisitor)
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)
        methodOwner = name
    }

    @Override
    void visitEnd() {
        super.visitEnd()
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions)
        if ((access & Opcodes.ACC_INTERFACE) == 0 && "<init>" != name && "<clinit>" != name) {
            methodVisitor = new MethodTimerAdviceAdapter(api, methodVisitor, methodOwner, access, name, descriptor)
        }
        return methodVisitor
    }
}
