package com.yang.SQLExecutor.util.stringUtils.test.newStructure;

/**
 * @auther YF
 * @create 2020-11-21-20:55
 */
public class StringBuilderAdapter implements Reusable {

    private boolean idle;

    private StringBuilder sb;

    public StringBuilderAdapter() {
        this.sb  = new StringBuilder();
        this.idle = true;
    }

    public Reusable append(Object o) {
        sb.append(o);
        return this;
    }

    public void replaceLast(int lastSize, String newString) {
        sb.replace(sb.length() - lastSize, sb.length(), newString);
    }

    public void replaceLastEmpty(int lastSize) {
        replaceLast(lastSize, "");
    }

    @Override
    public boolean isIdle() {
        return idle;
    }

    @Override
    public void recycle() {
        sb.delete(0, sb.length());
        this.idle = true;
    }

    @Override
    public Reusable setIdle(boolean isIdle) {
        this.idle = isIdle;
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
