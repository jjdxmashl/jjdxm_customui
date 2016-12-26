package com.dou361.customui.widget;

/**
 * 部分文本配置
 * Background model.
 */
public class TextPart {

    /**
     * forground text.
     */
    public CharSequence mTextPart;
    /**
     * forground color.
     */
    public int mFgColor;
    /**
     * Background color.
     */
    public int mBgColor;

    /**
     * Start offset of background.
     */
    public int mStart;

    /**
     * End offset of background.
     */
    public int mEnd;

    public TextPart(CharSequence textPart, int fgColor, int bgColor, int start, int end) {
        mTextPart = textPart;
        mFgColor = fgColor;
        mBgColor = bgColor;
        mStart = start;
        mEnd = end;
    }
}