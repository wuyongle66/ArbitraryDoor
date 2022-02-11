package com.sunnyweather.arbitrarydoor.User;

import java.io.Serializable;
import java.util.List;

public class User_mood_list implements Serializable {

    private Integer code;
    private String message;
    private List<Mood_List> data;


    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Mood_List> getData() {
        return data;
    }

    public void setData(List<Mood_List> data) {
        this.data = data;
    }
    public static class Mood_List implements Serializable{
        private String createTime;
        private List diaryFiles;
        private String diaryId;
        private double emotion;
        private String emotionalStickers;
        private String eventRecord;
        private String textRecord;
        private String userId;

        public List getDiaryFiles() {
            return diaryFiles;
        }

        public void setDiaryFiles(List diaryFiles) {
            this.diaryFiles = diaryFiles;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDiaryId() {
            return diaryId;
        }

        public void setDiaryId(String diaryId) {
            this.diaryId = diaryId;
        }

        public double getEmotion() {
            return emotion;
        }

        public void setEmotion(double emotion) {
            this.emotion = emotion;
        }

        public String getEmotionalStickers() {
            return emotionalStickers;
        }

        public void setEmotionalStickers(String emotionalStickers) {
            this.emotionalStickers = emotionalStickers;
        }

        public String getEventRecord() {
            return eventRecord;
        }

        public void setEventRecord(String eventRecord) {
            this.eventRecord = eventRecord;
        }

        public String getTextRecord() {
            return textRecord;
        }

        public void setTextRecord(String textRecord) {
            this.textRecord = textRecord;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
