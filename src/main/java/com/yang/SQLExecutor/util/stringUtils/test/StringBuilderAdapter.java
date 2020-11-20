package com.yang.SQLExecutor.util.stringUtils.test;

/**
 * @auther YF
 * @create 2020-11-20-20:22
 */
public class StringBuilderAdapter {

    private StringBuilder sb;

    private boolean isUsing;

    public StringBuilderAdapter(StringBuilder sb) {
        this.sb = sb;
        this.isUsing = true;
    }

    public StringBuilderAdapter append(Object o) {
        sb.append(o);
        return this;
    }

    public void replaceLast(int lastSize, String newString) {
        sb.replace(sb.length() - lastSize, sb.length(), newString);
    }

    public void replaceLastEmpty(int lastSize) {
        replaceLast(lastSize, "");
    }

    /**
     * 回收
     */
    public void recycle() {
        sb.delete(0, sb.length());
        this.isUsing = false;
    }


    public boolean isUsing() {
        return isUsing;
    }

    public StringBuilderAdapter setUsing(boolean using) {
        isUsing = using;
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
