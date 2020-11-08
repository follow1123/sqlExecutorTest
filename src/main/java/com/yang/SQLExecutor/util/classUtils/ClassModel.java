package com.yang.SQLExecutor.util.classUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther YF
 * @create 2020-11-07-18:07
 */
public class ClassModel implements Model {
    private String modifier;

    private String aPackage;

    private String aExtends;

    private String status;

    private String type;

    private String name;

    private List<String> impls;

    private List<String> imports;

    private List<FieldModel> fieldModels;

    private List<MethodModel> methodModels;

    private List<ConstructorModel> constructorModels;

    public ClassModel() {
        fieldModels = new ArrayList<>();
        methodModels = new ArrayList<>();
        constructorModels = new ArrayList<>();
        impls = new ArrayList<>();
        imports = new ArrayList<>();
    }


    public ClassModel(String name) {
        this();
        this.name = name;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getaPackage() {
        return aPackage;
    }

    public void setaPackage(String aPackage) {
        this.aPackage = aPackage;
    }

    public String getaExtends() {
        return aExtends;
    }

    public void setaExtends(String aExtends) {
        this.aExtends = aExtends;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImpls(List<String> impls) {
        this.impls.addAll(impls);
    }

    public void addFieldModel(FieldModel fieldModel) {
        fieldModels.add(fieldModel);
    }

    public void addMethodModel(MethodModel methodModel) {
        methodModels.add(methodModel);
    }

    public void addConstructorModel(ConstructorModel constructorModel) {
        constructorModels.add(constructorModel);
    }
    public void addImport(String aImport) {
        imports.add(aImport);
    }
    public void addImport(List<String> aImports) {
        imports.addAll(aImports);
    }


    @Override
    public String getModel() {
        return ClassUtil.buildClassStr(modifier,
                name, type, status, aPackage, aExtends, impls, imports, fieldModels, methodModels, constructorModels);
    }
}
