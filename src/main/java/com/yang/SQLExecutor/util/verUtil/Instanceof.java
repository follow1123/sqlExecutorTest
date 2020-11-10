package com.yang.SQLExecutor.util.verUtil;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @auther YF
 * @create 2020-11-10-21:38
 */
public class Instanceof {

    //原对象
    private Object e;
    //是否已匹配的标记
    private boolean isMatched;
    //单例
    private static Instanceof anInstanceof;

    public static Instanceof ele(Object e) {
        if (anInstanceof == null) {
            anInstanceof = new Instanceof(e);
        }
        return anInstanceof;
    }

    private Instanceof(Object e) {
        this.e = e;
        this.isMatched = false;
    }

    /**
     * 设置这个对象已经匹配
     */
    private void matched() {
        this.isMatched = true;
    }

    /**
     * 判断当前对象是否属于某个Type
     * 因为instanceof泛型会报错所以使用class对象进行比较
     * 实现类似instanceof的功能
     * @param tClass
     * @param verification
     * @param <T>
     * @return
     */
    public <T> Instanceof is(Class<T> tClass, Verification<T> verification) {
        if (tClass.equals(e.getClass())) {
            verification.ope((T) e);
            matched();
        }
        return this;
    }

    /**
     * 判断当前对象是否时List类型
     * @param verification
     * @return
     */
    public Instanceof isList(Verification<List> verification) {
        if (e instanceof List) {
            verification.ope((List) e);
            matched();
        }
        return this;
    }

    /**
     * 判断当前对象是否为Object数组
     * @param verification
     * @return
     */
    public Instanceof isArray(Verification<Object[]> verification) {
        if (e instanceof Object[]) {
            verification.ope((Object[]) e);
            matched();
        }
        return this;
    }

    /**
     * 判断当前对象是否为空
     * @param verification
     * @return
     */
    public Instanceof isNull(Verification verification) {
        if (e == null) {
            verification.ope(e);
            matched();
        }
        return this;
    }

    /**
     * 其他情况
     * @param verification
     */
    public void other(Verification<Object> verification) {
        if (!isMatched) {
            verification.ope(e);
        }
    }

    public interface Verification<T> {
        void ope(T t);
    }
}
