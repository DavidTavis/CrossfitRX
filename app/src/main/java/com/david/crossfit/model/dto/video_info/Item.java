
package com.david.crossfit.model.dto.video_info;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    public Id id;
    @SerializedName("snippet")
    @Expose
    public Snippet snippet;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Item() {
    }

    /**
     * 
     * @param id
     * @param snippet
     */
    public Item(Id id, Snippet snippet) {
        super();
        this.id = id;
        this.snippet = snippet;
    }

}
