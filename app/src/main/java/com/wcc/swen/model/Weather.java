package com.wcc.swen.model;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/30.
 * 存储一次http请求所获取的天气情况
 */
public class Weather {
    public Loc location;
    public List<Daily> daily;
    public String last_update;
}
