
package com.david.crossfit.model.dto.video_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails {

    @SerializedName("default")
    @Expose
    public Default _default;
    @SerializedName("medium")
    @Expose
    public Medium medium;
    @SerializedName("high")
    @Expose
    public High high;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Thumbnails() {
    }

    /**
     * 
     * @param _default
     * @param high
     * @param medium
     */
    public Thumbnails(Default _default, Medium medium, High high) {
        super();
        this._default = _default;
        this.medium = medium;
        this.high = high;
    }

}
