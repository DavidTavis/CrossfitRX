package com.david.crossfit.model.vo;

/**
 * Created by TechnoA on 04.09.2017.
 */

public class VideoData {
    private String nextPageToken;
    private String title;
    private String videoId;
    private String publishedAt;
    private String duration;
    private String url;

    public VideoData(String url, String nextPageToken, String title, String videoId, String publishedAt, String duration) {
        this.url = url;
        this.nextPageToken = nextPageToken;
        this.title = title;
        this.videoId = videoId;
        this.publishedAt = publishedAt;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "VideoData{" +
                "title='" + title + '\'' +
                ", videoId='" + videoId + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
