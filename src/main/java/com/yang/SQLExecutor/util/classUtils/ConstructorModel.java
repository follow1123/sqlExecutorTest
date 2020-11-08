package com.yang.SQLExecutor.util.classUtils;

/**
 * @auther YF
 * @create 2020-11-07-18:15
 */
public class ConstructorModel extends MethodModel {

    public ConstructorModel() {
    }

    public ConstructorModel(String name) {
        super(name);
    }

    @Override
    public String getModel() {
        return ClassUtil.buildMethodStr(getModifier(), null, getName(), ""
                , getParams(), getContents());
    }
}
