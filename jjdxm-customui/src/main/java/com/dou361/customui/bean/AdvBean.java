package com.dou361.customui.bean;

import java.io.Serializable;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/12/9 10:34
 * <p/>
 * 描 述：广告
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class AdvBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3428856308723065293L;
    private int id;
    /**
     * 图片对应的页面动作类型 广告的种类，区分点击事件的响应方式
     */
    private int type;
    /**
     * 图片对应的标题说明
     */
    private String title;
    /**
     * 图片对应的地址
     */
    private String url;
    /**
     * 跳转内容
     */
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
