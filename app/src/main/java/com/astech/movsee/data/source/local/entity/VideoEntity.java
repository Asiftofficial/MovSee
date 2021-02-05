package com.astech.movsee.data.source.local.entity;

import androidx.room.ColumnInfo;

public class VideoEntity {

    @ColumnInfo(name = "video")
    private String video;

    public VideoEntity(){

    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
