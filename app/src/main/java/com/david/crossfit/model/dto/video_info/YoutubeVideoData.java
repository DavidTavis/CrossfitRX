
package com.david.crossfit.model.dto.video_info;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YoutubeVideoData {

    @SerializedName("nextPageToken")
    @Expose
    public String nextPageToken;
    @SerializedName("pageInfo")
    @Expose
    public PageInfo pageInfo;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public YoutubeVideoData() {
    }

    /**
     *
     * @param items
     * @param pageInfo
     * @param nextPageToken
     */
    public YoutubeVideoData(String nextPageToken, PageInfo pageInfo, List<Item> items) {
        super();
        this.nextPageToken = nextPageToken;
        this.pageInfo = pageInfo;
        this.items = items;
    }

}
