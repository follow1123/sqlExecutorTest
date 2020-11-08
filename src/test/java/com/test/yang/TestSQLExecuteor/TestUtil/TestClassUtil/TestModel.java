package com.test.yang.TestSQLExecuteor.TestUtil.TestClassUtil;

import com.yang.SQLExecutor.util.classUtils.ClassModel;
import com.yang.SQLExecutor.util.classUtils.ConstructorModel;
import com.yang.SQLExecutor.util.classUtils.FieldModel;
import com.yang.SQLExecutor.util.classUtils.MethodModel;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

import static com.yang.SQLExecutor.util.classUtils.Symbol.*;
/**
 * @auther YF
 * @create 2020-11-07-18:41
 */
public class TestModel {
    @Test
    public void test01(){
        FieldModel fieldModel = new FieldModel("name");
        fieldModel.setModifier(_public);
//        fieldModel.setStatus(_static);
        System.out.println(fieldModel.getModel());
    }
    @Test
    public void test02(){
        MethodModel set = new ConstructorModel("Con");
        set.setParams(Arrays.asList("String name", "String age"));
        set.setContents(Arrays.asList("int i = 0;", "String g = 100;"));
        System.out.println(set.getModel());
    }

    @Test
    public void test03(){

        ClassModel person = new ClassModel("Person");
        person.setaPackage("com.yang.text");
        person.addImport(Arrays.asList("java.util.Math", "java.util.function.*"));

        person.addFieldModel(new FieldModel("name"));
        FieldModel age = new FieldModel("age");
        age.setType("int");
        person.addFieldModel(age);
        FieldModel height = new FieldModel("height");
        height.setType("long");
        person.addFieldModel(height);

        ConstructorModel person1 = new ConstructorModel("Person");
        person1.setParams(Arrays.asList("String name", "int age", "long height"));
        person1.setContents(Arrays.asList("this.name = name;", "this.age = age;", "this.height = height"));

        person.addConstructorModel(person1);

        MethodModel setName = new MethodModel("setName");
        setName.setParams(Arrays.asList("String name"));
        setName.setContents(Arrays.asList("this.name = name;"));
        MethodModel getName = new MethodModel("getName");
        getName.setContents(Arrays.asList("return this.name;"));
        getName.setReturnType("String");

        person.addMethodModel(setName);
        person.addMethodModel(getName);

        System.out.println(person.getModel());


    }
}
