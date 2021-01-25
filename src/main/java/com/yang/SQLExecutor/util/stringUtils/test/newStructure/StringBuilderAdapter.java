package com.yang.SQLExecutor.util.stringUtils.test.newStructure;

/**
 * @auther YF
 * @create 2020-11-21-20:55
 */
public class StringBuilderAdapter implements Reusable {

    static final long serialVersionUID = 1_0000_0000_0000l;

    private boolean idle;

    private StringBuilder sb;

    private int a, b, c;

    public StringBuilderAdapter(int a, int b, int c) {
        this();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public void printDefaultParams(){
        System.out.println(a + "==" + b + "==" + c);
    }

    public StringBuilderAdapter() {
        this.sb  = new StringBuilder();
        this.idle = true;
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
