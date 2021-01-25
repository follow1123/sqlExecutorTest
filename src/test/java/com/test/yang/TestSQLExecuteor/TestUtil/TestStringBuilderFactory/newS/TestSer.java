package com.test.yang.TestSQLExecuteor.TestUtil.TestStringBuilderFactory.newS;

import com.yang.SQLExecutor.util.stringUtils.test.newStructure.DynamicObjectPool;
import com.yang.SQLExecutor.util.stringUtils.test.newStructure.StringBuilderAdapter;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.List;

import static com.yang.SQLExecutor.util.stringUtils.test.newStructure.DynamicObjectPool.DeepCloneUtil.*;
/**
 * @auther YF
 * @create 2020-12-20-11:07
 * 测试克隆StringBuilder
 */
public class TestSer {
    @Test
    public void test01() throws Exception {
//        StringBuilder sb = new StringBuilder();
        StringBuilderAdapter sb = new StringBuilderAdapter();
        sb.append("1231231232131");
        long l = System.currentTimeMillis();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(sb);
        oos.flush();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        StringBuilderAdapter o = (StringBuilderAdapter) ois.readObject();
        long l1 = System.currentTimeMillis();
        System.out.println((l1 - l));
        System.out.println(o == sb);
        System.out.println(o);
        System.out.println(sb);

        long l2 = System.currentTimeMillis();
        Constructor<? extends StringBuilderAdapter> dc = sb.getClass().getDeclaredConstructor();
        StringBuilderAdapter ssa = dc.newInstance();

        System.out.println(sb == ssa);
        System.out.println(ssa.toString());
        System.out.println(sb);
        long l3 = System.currentTimeMillis();
        System.out.println((l3 - l2));

    }

    @Test
    public void test02() throws Exception {
        StringBuilderAdapter o = new StringBuilderAdapter().append("123213");
        int i = 100;
        String s = "hello world";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.writeObject(i);
        oos.writeObject(s);
        oos.flush();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object value;
        while (true){
            try{
                value = ois.readObject();
                System.out.println(value);
            }catch (EOFException e){
                break;
            }
        }

    }

    @Test
    public void test03(){
        StringBuilderAdapter o = new StringBuilderAdapter().append("123213");
        int i = 100;
        String s = "hello world";
//        writeObj(o);
//        StringBuilderAdapter stringBuilderAdapter = readObj(StringBuilderAdapter.class);
//        System.out.println(stringBuilderAdapter + " =====");
//        closeStream();
//        writeObj(i);
//        writeObj(s);
//        List<Object> objects = readObjs();
//        objects.forEach(System.out::println);
    }

    @Test
    public void test04(){
        StringBuilderAdapter o = new StringBuilderAdapter().append("123213");
        int i = 100;
        String s = "hello world";
        long l = System.currentTimeMillis();
        StringBuilderAdapter clone = DynamicObjectPool.DeepCloneUtil.clone(o);
        long l1 = System.currentTimeMillis();
        System.out.println(l1 - l);
        System.out.println(o == clone);
        System.out.println(clone);

        List<Object> clone1 = DynamicObjectPool.DeepCloneUtil.clone(o, i, s);

        clone1.forEach(System.out::println);
    }
}
