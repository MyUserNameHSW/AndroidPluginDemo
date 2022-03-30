package com.hsw.plugin.timeout

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter


/**
 * @author: hsw
 * @date: 2022/3/29
 * @desc:
 */
class MethodTimerAdviceAdapter extends AdviceAdapter {

    String methodDesc
    String methodOwner
    String methodName
    int slotIndex
    int api

    Map<String, AnnEntity> annEntityMap = new HashMap<>()

    MethodTimerAdviceAdapter(int api, MethodVisitor methodVisitor, String owner, int access, String name, String descriptor) {
        super(api, methodVisitor, access, name, descriptor)
        this.api = api
        this.methodOwner = owner
        this.methodName = name
        this.methodDesc = descriptor
    }

    @Override
    AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(descriptor, visible)

        if (descriptor.contains("FixMethod")) {
            AnnEntity annEntity = new AnnEntity()
            return new AnnotationVisitor(api, annotationVisitor) {
                @Override
                void visit(String name, Object value) {
                    super.visit(name, value)
                    annEntity.annotationData.put(name, value)
                }

                @Override
                void visitEnd() {
                    super.visitEnd()
                    annEntity.methodOwner = methodOwner
                    annEntity.methodName = methodName
                    annEntity.methodDesc = methodDesc
                    annEntityMap.put(methodOwner + methodName + methodDesc, annEntity)
                }
            }
        }
        return annotationVisitor
    }


    @Override
    protected void onMethodEnter() {
        super.onMethodEnter()
        for (MethodTimerEntity entity : MethodTimerPlugin.METHOD_TIMER_LIST) {
            if (methodOwner.contains(entity.getOwner())) {
                slotIndex = newLocal(Type.LONG_TYPE)
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                mv.visitVarInsn(LSTORE, slotIndex)
                AnnEntity annEntity = annEntityMap.get(methodOwner + methodName + methodDesc)
                if (annEntity != null) {
                    mv.visitMethodInsn(INVOKESTATIC, "com/hsw/classinvokeplugin/App", "getInstance", "()Lcom/hsw/classinvokeplugin/App;", false);
                    mv.visitLdcInsn(annEntity.annotationData.get("desc"));
                    mv.visitInsn(ICONST_0);
                    mv.visitMethodInsn(INVOKESTATIC, "android/widget/Toast", "makeText", "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;", false);
                    mv.visitMethodInsn(INVOKEVIRTUAL, "android/widget/Toast", "show", "()V", false);
                }
            }
        }
    }

    @Override
    void onMethodExit(int opcode) {
        for (MethodTimerEntity entity : MethodTimerPlugin.METHOD_TIMER_LIST) {
            if (methodOwner.contains(entity.getOwner())) {
                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false)
                mv.visitVarInsn(LLOAD, slotIndex)
                mv.visitInsn(LSUB)
                mv.visitVarInsn(LSTORE, slotIndex)
                mv.visitVarInsn(LLOAD, slotIndex)
                mv.visitLdcInsn(new Long(entity.getTime()))
                mv.visitInsn(LCMP)
                Label label0 = new Label()
                mv.visitJumpInsn(IFLE, label0)
                mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
                mv.visitTypeInsn(NEW, "java/lang/StringBuilder")
                mv.visitInsn(DUP)
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false)
                mv.visitLdcInsn(methodOwner + "/" + methodName + " --> execution time : (")
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                mv.visitVarInsn(LLOAD, slotIndex)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(J)Ljava/lang/StringBuilder;", false)
                mv.visitLdcInsn("ms)")
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false)
                mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false)
                mv.visitLabel(label0)
            }
        }
        super.onMethodExit(opcode)
    }
}
