
package com.david.crossfit.model.dto.video_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snippet {

    @SerializedName("publishedAt")
    @Expose
    public String publishedAt;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("thumbnails")
    @Expose
    public Thumbnails thumbnails;

    @SerializedName("resourceId")
    @Expose
    public ResourceId resourceId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Snippet() {
    }

    /**
     * 
     * @param publishedAt
     * @param title
     * @param thumbnails
     */
    public Snippet(String publishedAt, String title, Thumbnails thumbnails, ResourceId resourceId) {
        super();
        this.publishedAt = publishedAt;
        this.title = title;
        this.thumbnails = thumbnails;
        this.resourceId = resourceId;
    }

}
