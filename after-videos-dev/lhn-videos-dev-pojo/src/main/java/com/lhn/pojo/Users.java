package com.lhn.pojo;

import com.lhn.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;

@ApiModel(value = "用户对象", description = "这是用户对象")
public class Users{
    /**
    *  用户id
    */
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    /**
    *  用户名
    */
    @ApiModelProperty(value = "用户名", name = "username", example = "videosUser", required = true)
    private String username;

    /**
    *  注册密码
    */
    @ApiModelProperty(value = "用户密码", name = "password", example = "123456", required = true)
    private String password;

    /**
    *  用户头像，没有就默认给一个
    */
    @Column(name = "face_image")
    @ApiModelProperty(hidden = true)
    private String faceImage;

    /**
    *  昵称
    */

    private String nickname;

    /**
    *  我的粉丝数
    */
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    /**
    *  我的关注的人总数
    */
    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    /**
    *  我接受到的赞美/收藏 的数量
    */
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getFaceImage() {
        return faceImage;
    }

    public void setFaceImage(String faceImage) {
        this.faceImage = faceImage == null ? null : faceImage.trim();
    }

    public String getNickname() {
        return nickname == null ? username : nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public Integer getFansCounts() {
        return fansCounts;
    }

    public void setFansCounts(Integer fansCounts) {
        this.fansCounts = fansCounts;
    }

    public Integer getFollowCounts() {
        return followCounts;
    }

    public void setFollowCounts(Integer followCounts) {
        this.followCounts = followCounts;
    }

    public Integer getReceiveLikeCounts() {
        return receiveLikeCounts;
    }

    public void setReceiveLikeCounts(Integer receiveLikeCounts) {
        this.receiveLikeCounts = receiveLikeCounts;
    }
}