package com.astech.movsee.data.source.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movieDetailEntities")
public class MovieDetailEntity implements Parcelable {

    @Embedded
    public VideoEntity videoEntity;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "catalogId")
    private String catalogId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "release")
    private String release;

    @ColumnInfo(name = "genres")
    private String genre;

    @ColumnInfo(name = "minutes")
    private String minute;

    @ColumnInfo(name = "score")
    private String score;

    public MovieDetailEntity(@NonNull String catalogId, String title, String poster, String overview, String release, String genre, String minute, String score) {
        this.catalogId = catalogId;
        this.title = title;
        this.poster = poster;
        this.overview = overview;
        this.release = release;
        this.genre = genre;
        this.minute = minute;
        this.score = score;
    }

    protected MovieDetailEntity(Parcel in) {
        catalogId = in.readString();
        title = in.readString();
        poster = in.readString();
        overview = in.readString();
        release = in.readString();
        genre = in.readString();
        minute = in.readString();
        score = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(catalogId);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(overview);
        dest.writeString(release);
        dest.writeString(genre);
        dest.writeString(minute);
        dest.writeString(score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieDetailEntity> CREATOR = new Creator<MovieDetailEntity>() {
        @Override
        public MovieDetailEntity createFromParcel(Parcel in) {
            return new MovieDetailEntity(in);
        }

        @Override
        public MovieDetailEntity[] newArray(int size) {
            return new MovieDetailEntity[size];
        }
    };

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
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
