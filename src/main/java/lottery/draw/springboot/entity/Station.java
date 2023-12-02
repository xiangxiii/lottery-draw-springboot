package lottery.draw.springboot.entity;

import lombok.Data;

/**
 * @author liux
 * @date 2023/11/30 15:24
 */
@Data
public class Station {
    private int id;
    private int parentid;
    private String name;
    private int orderindex;
    private double jd;
    private double wd;
    private int polerate;
    private String memo;
    private int capacity;
    private String stationaddress;
    private int network;
    private String  address;
    private Data createtime;
    private Data runtime;
    private int status;
    private String assertcode;
    private String iccid;
    private String voltage;
    private String current;
    private String model;
    private String productdate;
    private String verfirm;
    private String versoft;
    private String aliasroad1;
    private String aliasroad2;
    private String aliasroad3;
    private String aliasroad4;
    private double a1;
    private double b1;
    private double c1;
    private double a2;
    private double b2;
    private double c2;
    private double a3;
    private double b3;
    private double c3;
    private double a4;
    private double b4;
    private double c4;
}
