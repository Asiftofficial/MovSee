package com.astech.movsee.data.source.remote.response;

public class CatalogResponse {
    private String catalogId;
    private String poster;
    private String score;

    public CatalogResponse(){

    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
