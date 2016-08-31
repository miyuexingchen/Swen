package com.wcc.swen.model;

import java.util.List;

/**
 * Created by WangChenchen on 2016/8/31.
 */
public class VideoWrapper {

    public class HotWrapper {
        public List<VideoModel> V9LG4B3A0;
    }

    public class EntertainWrapper {
        public List<VideoModel> V9LG4CHOR;
    }

    public class FunWrapper {
        public List<VideoModel> V9LG4E6VR;
    }

    public class VideoModel {
        public String topicImg;
        public String videosource;
        public String mp4Hd_url;
        public String topicDesc;
        public String topicSid;
        public String cover;
        public String title;
        public int playCount;
        public String replyBoard;
        public VideoTopic videoTopic;
        public String sectiontitle;
        public String replyid;
        public String description;
        public String mp4_url;
        public int length;
        public int playersize;
        public String m3u8Hd_url;
        public String vid;
        public String m3u8_url;
        public String ptime;
        public String topicName;
    }

    public class VideoTopic {
        public String alias;
        public String tname;
        public String ename;
        public String tid;
    }
}
