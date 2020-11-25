package com.yang.SQLExecutor.util.stringUtils.test.newStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-25-20:51
 */
public class DynamicObjectPoolBuilder {

    private static Map<Class, DynamicObjectPool> pools = new HashMap();

    public static <T> DynamicObjectPool<T> build(Class<T> objType, int minSize) {
        DynamicObjectPool<T> res;
        if ((res = pools.get(objType)) == null) pools.put(objType, (res = new DynamicObjectPool<T>(objType, minSize)));
        return res;
    }
    public static <T> DynamicObjectPool<T> build(Class<T> objType){
        return build(objType, 50);
    }
}
