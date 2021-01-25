package com.yang.SQLExecutor.util.stringUtils.test.newStructure;

import java.io.Serializable;

/**
 * @auther YF
 * @create 2020-11-21-20:41
 */
public interface Reusable extends Serializable {

    //设置当前对象在什么情况下是闲置状态
    boolean isIdle();
    //回收当前对象
    void recycle();
    //设置当前对象的闲置状态
    Reusable setIdle(boolean isIdle);


}
