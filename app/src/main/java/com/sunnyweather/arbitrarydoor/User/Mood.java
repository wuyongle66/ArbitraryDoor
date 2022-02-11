package com.sunnyweather.arbitrarydoor.User;



public class Mood {
    private String creattime;
    private int imageId;
    private String emotionalStickers;
    private String eventRecord;
    private String textRecord;
    private String creattime1;

    public Mood(String creattime,int imageId,String emotionalStickers,String eventRecord,String textRecord,String creattime1){
        this.creattime=creattime;
        this.imageId=imageId;
        this.emotionalStickers=emotionalStickers;
        this.eventRecord=eventRecord;
        this.textRecord=textRecord;
        this.creattime1=creattime1;
    }
    public String getCreattime() {
        return creattime;
    }

    public int getImageId() {
        return imageId;
    }

    public String getEmotionalStickers() {
        return emotionalStickers;
    }

    public String getEventRecord() {
        return eventRecord;
    }

    public String getTextRecord() {
        return textRecord;
    }

    public String getCreattime1() {
        return creattime1;
    }
}
