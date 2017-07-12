package com.tma.videotraining.ui.model.NavDrawer;

import java.io.Serializable;

/**
 * Created by nbhung on 7/7/2017.
 */

public class ListVideo implements Serializable {
    private String url;
    private String name;
    private String time;

    public ListVideo(String url, String name, String time) {
        this.url = url;
        this.name = name;
        this.time = time;

    }

    public ListVideo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ListVideo{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

}
