package com.pbtd.mobile.model;

import java.io.Serializable;

/**
 * Created by xuqinchao on 17/4/1.
 */

public class ImageInfo implements Serializable {
    private String imageID;
    private String imageURL;

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
