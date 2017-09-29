
package com.david.crossfit.model.dto.video_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Id {

    @SerializedName("videoId")
    @Expose
    public String videoId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Id() {
    }

    /**
     * 
     * @param videoId
     */
    public Id(String videoId) {
        super();
        this.videoId = videoId;
    }

}
