package com.byted.chapter5;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class ArticleResponse {
    @SerializedName("_id")
    public String id;
    @SerializedName("feedurl")
    public String feedurl;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("likecount")
    public String likecount;
    @SerializedName("avatar")
    public String avatar;

}
