package com.wcc.swen.model;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class NewsModel {

    // 共同
    public int replyCount;
    public String docid;
    public String title;
    public String source;
    public String imgsrc;
    // 顶部
    public List<Ads> ads;
    public String photosetID;
    // 多图
    public List<ImageModel> imgextra;

    // 普通
    public String url;
    public String subtitle;


}
