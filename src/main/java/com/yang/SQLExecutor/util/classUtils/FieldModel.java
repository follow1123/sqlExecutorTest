package com.yang.SQLExecutor.util.classUtils;
/**
 * @auther YF
 * @create 2020-11-07-18:18
 */
public class FieldModel implements Model {
    private String modifier;

    private String status;

    private String type;

    private String name;

    public FieldModel() {
        super();
    }

    public FieldModel(String name) {
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
    @Override
    public String getModel() {
        return ClassUtil.buildFieldStr(modifier, status, type, name);
    }
}
