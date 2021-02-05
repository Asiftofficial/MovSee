package com.astech.movsee.data.source.remote.response;

public class VideoResponse {

    private String catalogId;
    private String video;

    public VideoResponse(){

    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
