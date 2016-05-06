package com.dou361.bean;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/4/29 17:43
 * <p>
 * 描 述：emoji表情对象
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class EmojiBean {

    public int icon;
    /**
     * 名称
     */
    public String emoji;
    /**
     * 别名
     */
    public String alias;

    public EmojiBean(int icon, String emoji) {
        this.icon = icon;
        this.emoji = emoji;
        this.alias = emoji;
    }

    public EmojiBean(int icon, String emoji, String alias) {
        this.icon = icon;
        this.emoji = emoji;
        this.alias = alias;
    }
}
