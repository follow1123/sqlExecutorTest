package com.yang.SQLExecutor.util.classUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther YF
 * @create 2020-11-07-18:56
 */
public class MethodModel implements Model {

    private String modifier;

    private String status;

    private String returnType;

    private List<String> params;

    private List<String> contents;

    private String name;

    public MethodModel() {
        super();
    }

    public MethodModel(String name) {
        this.name = name;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getModel() {
        if (params == null) {
            params = new ArrayList<>();
        }
        if (contents == null) {
            contents = new ArrayList<>();
        }
        return ClassUtil.buildMethodStr(modifier, status, returnType, name, params, contents);
    }
}
