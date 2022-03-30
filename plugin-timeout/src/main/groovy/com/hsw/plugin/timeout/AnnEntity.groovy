package com.hsw.plugin.timeout;

/**
 * @author: hsw
 * @date: 2022/3/30
 * @desc:
 */
class AnnEntity implements Cloneable {
    /**
     * 插入的方法的路径
     */
    String methodOwner
    /**
     * 插入的方法名
     */
    String methodName
    /**
     * 插入的方法描述
     */
    String methodDesc

    /**
     * String:注解参数名
     * Object:参数值
     */
    Map<String, Object> annotationData = new HashMap<>()

    @Override
    protected AnnEntity clone() throws CloneNotSupportedException {
        try {
            return (AnnEntity) super.clone()
        } catch (CloneNotSupportedException e) {
            e.printStackTrace()
        }
        return null
    }
}
