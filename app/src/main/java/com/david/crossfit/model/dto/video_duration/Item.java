
package com.david.crossfit.model.dto.video_duration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("contentDetails")
    @Expose
    public ContentDetails contentDetails;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param contentDetails
     */
    public Item(ContentDetails contentDetails) {
        super();
        this.contentDetails = contentDetails;
    }

    public ContentDetails getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(ContentDetails contentDetails) {
        this.contentDetails = contentDetails;
    }
}
