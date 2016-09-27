package cn.weiyf.dlframe.sample.entity;

/**
 * Created by weiyf on 2016/9/27.
 */

public class Test {

    private String name;

    private int type;

    public Test(String name) {
        this.name = name;
    }

    public Test(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
