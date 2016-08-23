package com.wcc.swen.model;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/19.
 */
public class NewsModel {

    // 共同
    public String postid;
    public int votecount;
    public int replyCount;
    public String skipID;
    public String digest;
    public String skipType;
    public String docid;
    public String title;
    public String source;
    public int priority;
    public String lmodify;
    public String boardid;
    public String imgsrc;
    public String ptime;
    // 顶部
    public boolean hasCover;
    public int hasHead;
    public int hasImg;
    public boolean hasIcon;
    public int order;
    public List<Ads> ads;
    public String photosetID;
    public String template;
    public String alias;
    public String cid;
    public int hasAD;
    public String ename;
    public String tname;
    // 多图
    public List<ImageModel> imgextra;

    // 普通
    public String url_3w;
    public String ltitle;
    public String url;
    public String specialID;
    public String subtitle;


}
