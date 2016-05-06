package com.dou361.utils;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dou361.adapter.EmoticonsAdapter;
import com.dou361.adapter.PageSetAdapter;
import com.dou361.adapter.TextEmoticonsAdapter;
import com.dou361.bean.EmojiBean;
import com.dou361.bean.EmojiDisplay;
import com.dou361.bean.EmoticonEntity;
import com.dou361.bean.EmoticonPageEntity;
import com.dou361.bean.EmoticonPageSetEntity;
import com.dou361.bean.PageEntity;
import com.dou361.bean.PageSetEntity;
import com.dou361.filter.CustomFilter;
import com.dou361.filter.EmojiFilter;
import com.dou361.filter.QqFilter;
import com.dou361.listener.EmoticonClickListener;
import com.dou361.listener.EmoticonDisplayListener;
import com.dou361.listener.PageViewInstantiateListener;
import com.dou361.parse.CustomEmoticons;
import com.dou361.parse.DefEmoticons;
import com.dou361.parse.DefQqEmoticons;
import com.dou361.ui.EmoticonPageView;
import com.dou361.ui.EmoticonsEditText;
import com.dou361.ui.SimpleQqGridView;
import com.dou361.utils.imageloader.ImageBase;
import com.dou361.utils.imageloader.ImageLoader;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;

public class SimpleCommonUtils {

    /**
     * 过滤器
     */
    public static void initEmoticonsEditText(EmoticonsEditText etContent) {
        etContent.addEmoticonFilter(new EmojiFilter());
    }

    /**
     * 过滤器
     */
    public static void initQqEmoticonsEditText(EmoticonsEditText etContent) {
        etContent.addEmoticonFilter(new QqFilter());
    }

    /**
     * 过滤器
     */
    public static void initAllEmoticonsEditText(EmoticonsEditText etContent) {
        etContent.addEmoticonFilter(new EmojiFilter());
        etContent.addEmoticonFilter(new QqFilter());
    }

    /**
     * 过滤器
     */
    public static void initCustomEmoticonsEditText(EmoticonsEditText etContent) {
        etContent.addEmoticonFilter(new EmojiFilter());
        etContent.addEmoticonFilter(new CustomFilter());
    }

    public static EmoticonClickListener getCommonEmoticonClickListener(final EditText editText) {
        return new EmoticonClickListener() {
            @Override
            public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {
                if (isDelBtn) {
                    SimpleCommonUtils.delClick(editText);
                } else {
                    if (o == null) {
                        return;
                    }
                    if (actionType == KeyBoardParams.EMOTICON_CLICK_TEXT) {
                        String content = null;
                        if (o instanceof EmojiBean) {
                            content = ((EmojiBean) o).emoji;
                        } else if (o instanceof EmoticonEntity) {
                            content = ((EmoticonEntity) o).getContent();
                        }

                        if (TextUtils.isEmpty(content)) {
                            return;
                        }
                        int index = editText.getSelectionStart();
                        Editable editable = editText.getText();
                        editable.insert(index, content);
                    }
                }
            }
        };
    }


    public static PageSetAdapter getCustomAdapter(Context context, EmoticonClickListener emoticonClickListener) {

        PageSetAdapter pageSetAdapter = new PageSetAdapter();

        addEmojiPageSetEntity(pageSetAdapter, context, emoticonClickListener);

        addKaomojiPageSetEntity(pageSetAdapter, context, emoticonClickListener);

        return pageSetAdapter;
    }

    public static PageSetAdapter getCustomSimpleAdapter(Context context, EmoticonClickListener emoticonClickListener) {

        PageSetAdapter pageSetAdapter = new PageSetAdapter();

        addCustomPageSetEntity(pageSetAdapter, context, emoticonClickListener);

        return pageSetAdapter;
    }

    public static PageSetAdapter getQqAdapter(Context context, EmoticonClickListener emoticonClickListener) {
        PageSetAdapter pageSetAdapter = new PageSetAdapter();

        addQqPageSetEntity(pageSetAdapter, context, emoticonClickListener);

        PageSetEntity pageSetEntity1 = new PageSetEntity.Builder()
                .addPageEntity(new PageEntity(new SimpleQqGridView(context)))
                .setIconUri(ResourceUtils.getResourceIdByName(context, "mipmap", "dec"))
                .setShowIndicator(false)
                .build();
        pageSetAdapter.add(pageSetEntity1);

        PageSetEntity pageSetEntity2 = new PageSetEntity.Builder()
                .addPageEntity(new PageEntity(new SimpleQqGridView(context)))
                .setIconUri(ResourceUtils.getResourceIdByName(context, "mipmap", "customui_mwi"))
                .setShowIndicator(false)
                .build();
        pageSetAdapter.add(pageSetEntity2);

        return pageSetAdapter;
    }

    /**
     * 插入emoji表情集
     *
     * @param pageSetAdapter
     * @param context
     * @param emoticonClickListener
     */
    public static void addEmojiPageSetEntity(PageSetAdapter pageSetAdapter, final Context context, final EmoticonClickListener emoticonClickListener) {
        ArrayList<EmojiBean> emojiArray = new ArrayList<>();
        Collections.addAll(emojiArray, DefEmoticons.getsEmojiArray(context));
        EmoticonPageSetEntity emojiPageSetEntity
                = new EmoticonPageSetEntity.Builder()
                .setLine(3)
                .setRow(7)
                .setEmoticonList(emojiArray)
                .setIPageViewInstantiateItem(getDefaultEmoticonPageViewInstantiateItem(new EmoticonDisplayListener<Object>() {
                    @Override
                    public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, Object object, final boolean isDelBtn) {
                        final EmojiBean emojiBean = (EmojiBean) object;
                        if (emojiBean == null && !isDelBtn) {
                            return;
                        }

                        viewHolder.ly_root.setBackgroundResource(ResourceUtils.getResourceIdByName(context, "drawable", "bg_emoticon"));

                        if (isDelBtn) {
                            viewHolder.iv_emoticon.setImageResource(ResourceUtils.getResourceIdByName(context, "mipmap", "customui_icon_del"));
                        } else {
                            viewHolder.iv_emoticon.setImageResource(emojiBean.icon);
                        }

                        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (emoticonClickListener != null) {
                                    emoticonClickListener.onEmoticonClick(emojiBean, KeyBoardParams.EMOTICON_CLICK_TEXT, isDelBtn);
                                }
                            }
                        });
                    }
                }))
                .setShowDelBtn(EmoticonPageEntity.DelBtnStatus.LAST)
                .setIconUri(ImageBase.Scheme.DRAWABLE.toUri("customui_icon_emoji"))
                .build();
        pageSetAdapter.add(emojiPageSetEntity);
    }

    public static void addQqPageSetEntity(PageSetAdapter pageSetAdapter, final Context context, final EmoticonClickListener emoticonClickListener) {
        DefQqEmoticons.initData(context);
        EmoticonPageSetEntity qqPageSetEntity
                = new EmoticonPageSetEntity.Builder()
                .setLine(3)
                .setRow(7)
                .setEmoticonList(ParseDataUtils.ParseQqData(DefQqEmoticons.sQqEmoticonHashMap))
                .setIPageViewInstantiateItem(new PageViewInstantiateListener<EmoticonPageEntity>() {
                    @Override
                    public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
                        if (pageEntity.getRootView() == null) {
                            EmoticonPageView pageView = new EmoticonPageView(context);
                            pageView.setNumColumns(pageEntity.getRow());
                            pageEntity.setRootView(pageView);
                            try {
                                EmoticonsAdapter adapter = new EmoticonsAdapter(context, pageEntity, emoticonClickListener);
                                adapter.setItemHeightMaxRatio(1.8);
                                adapter.setOnDisPlayListener(getEmoticonDisplayListener(context, emoticonClickListener));
                                pageView.getEmoticonsGridView().setAdapter(adapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return pageEntity.getRootView();
                    }
                })
                .setShowDelBtn(EmoticonPageEntity.DelBtnStatus.LAST)
                .setIconUri(ImageBase.Scheme.DRAWABLE.toUri("kys"))
                .build();
        pageSetAdapter.add(qqPageSetEntity);
    }

    public static void addCustomPageSetEntity(PageSetAdapter pageSetAdapter, final Context context, final EmoticonClickListener emoticonClickListener) {
        CustomEmoticons.initData(context);
        EmoticonPageSetEntity customPageSetEntity
                = new EmoticonPageSetEntity.Builder()
                .setLine(3)
                .setRow(7)
                .setEmoticonList(ParseDataUtils.ParseQqData(CustomEmoticons.sCustomEmoticonHashMap))
                .setIPageViewInstantiateItem(new PageViewInstantiateListener<EmoticonPageEntity>() {
                    @Override
                    public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
                        if (pageEntity.getRootView() == null) {
                            EmoticonPageView pageView = new EmoticonPageView(context);
                            pageView.setNumColumns(pageEntity.getRow());
                            pageEntity.setRootView(pageView);
                            try {
                                EmoticonsAdapter adapter = new EmoticonsAdapter(context, pageEntity, emoticonClickListener);
                                adapter.setItemHeightMaxRatio(1.8);
                                adapter.setOnDisPlayListener(getEmoticonDisplayListener(context, emoticonClickListener));
                                pageView.getEmoticonsGridView().setAdapter(adapter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        return pageEntity.getRootView();
                    }
                })
                .setShowDelBtn(EmoticonPageEntity.DelBtnStatus.LAST)
                .setIconUri(ImageBase.Scheme.DRAWABLE.toUri("kys"))
                .build();
        pageSetAdapter.add(customPageSetEntity);
    }


    /**
     * 插入颜文字表情集
     *
     * @param pageSetAdapter
     * @param context
     * @param emoticonClickListener
     */
    public static void addKaomojiPageSetEntity(PageSetAdapter pageSetAdapter, Context context, EmoticonClickListener emoticonClickListener) {
        EmoticonPageSetEntity kaomojiPageSetEntity
                = new EmoticonPageSetEntity.Builder()
                .setLine(3)
                .setRow(3)
                .setEmoticonList(ParseDataUtils.parseKaomojiData(context))
                .setIPageViewInstantiateItem(getEmoticonPageViewInstantiateItem(TextEmoticonsAdapter.class, emoticonClickListener))
                .setIconUri(ImageBase.Scheme.DRAWABLE.toUri("customui_icon_kaomoji"))
                .build();
        pageSetAdapter.add(kaomojiPageSetEntity);
    }

    public static EmoticonDisplayListener<Object> getEmoticonDisplayListener(final Context context, final EmoticonClickListener emoticonClickListener) {
        return new EmoticonDisplayListener<Object>() {
            @Override
            public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, Object object, final boolean isDelBtn) {
                final EmoticonEntity emoticonEntity = (EmoticonEntity) object;
                if (emoticonEntity == null && !isDelBtn) {
                    return;
                }
                viewHolder.ly_root.setBackgroundResource(ResourceUtils.getResourceIdByName(context, "drawable", "bg_emoticon"));

                if (isDelBtn) {
                    viewHolder.iv_emoticon.setImageResource(ResourceUtils.getResourceIdByName(context, "mipmap", "customui_icon_del"));
                } else {
                    try {
                        ImageLoader.getInstance(viewHolder.iv_emoticon.getContext()).displayImage(emoticonEntity.getIconUri(), viewHolder.iv_emoticon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (emoticonClickListener != null) {
                            emoticonClickListener.onEmoticonClick(emoticonEntity, KeyBoardParams.EMOTICON_CLICK_TEXT, isDelBtn);
                        }
                    }
                });
            }
        };
    }


    @SuppressWarnings("unchecked")
    public static Object newInstance(Class _Class, Object... args) throws Exception {
        return newInstance(_Class, 0, args);
    }

    @SuppressWarnings("unchecked")
    public static Object newInstance(Class _Class, int constructorIndex, Object... args) throws Exception {
        Constructor cons = _Class.getConstructors()[constructorIndex];
        return cons.newInstance(args);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getDefaultEmoticonPageViewInstantiateItem(final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return getEmoticonPageViewInstantiateItem(EmoticonsAdapter.class, null, emoticonDisplayListener);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(final Class _class, EmoticonClickListener onEmoticonClickListener) {
        return getEmoticonPageViewInstantiateItem(_class, onEmoticonClickListener, null);
    }

    public static PageViewInstantiateListener<EmoticonPageEntity> getEmoticonPageViewInstantiateItem(final Class _class, final EmoticonClickListener onEmoticonClickListener, final EmoticonDisplayListener<Object> emoticonDisplayListener) {
        return new PageViewInstantiateListener<EmoticonPageEntity>() {
            @Override
            public View instantiateItem(ViewGroup container, int position, EmoticonPageEntity pageEntity) {
                if (pageEntity.getRootView() == null) {
                    EmoticonPageView pageView = new EmoticonPageView(container.getContext());
                    pageView.setNumColumns(pageEntity.getRow());
                    pageEntity.setRootView(pageView);
                    try {
                        EmoticonsAdapter adapter = (EmoticonsAdapter) newInstance(_class, container.getContext(), pageEntity, onEmoticonClickListener);
                        if (emoticonDisplayListener != null) {
                            adapter.setOnDisPlayListener(emoticonDisplayListener);
                        }
                        pageView.getEmoticonsGridView().setAdapter(adapter);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return pageEntity.getRootView();
            }
        };
    }

    public static EmoticonDisplayListener<Object> getCommonEmoticonDisplayListener(final Context context, final EmoticonClickListener onEmoticonClickListener, final int type) {
        return new EmoticonDisplayListener<Object>() {
            @Override
            public void onBindView(int position, ViewGroup parent, EmoticonsAdapter.ViewHolder viewHolder, Object object, final boolean isDelBtn) {

                final EmoticonEntity emoticonEntity = (EmoticonEntity) object;
                if (emoticonEntity == null && !isDelBtn) {
                    return;
                }
                viewHolder.ly_root.setBackgroundResource(ResourceUtils.getResourceIdByName(context, "drawable", "bg_emoticon"));

                if (isDelBtn) {
                    viewHolder.iv_emoticon.setImageResource(ResourceUtils.getResourceIdByName(context, "mipmap", "customui_icon_del"));
                } else {
                    try {
                        ImageLoader.getInstance(viewHolder.iv_emoticon.getContext()).displayImage(emoticonEntity.getIconUri(), viewHolder.iv_emoticon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onEmoticonClickListener != null) {
                            onEmoticonClickListener.onEmoticonClick(emoticonEntity, type, isDelBtn);
                        }
                    }
                });
            }
        };
    }

    public static void delClick(EditText editText) {
        int action = KeyEvent.ACTION_DOWN;
        int code = KeyEvent.KEYCODE_DEL;
        KeyEvent event = new KeyEvent(action, code);
        editText.onKeyDown(KeyEvent.KEYCODE_DEL, event);
    }

    public static void spannableEmoticonFilter(TextView tv_content, String content) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);

        Spannable spannable = EmojiDisplay.spannableFilter(tv_content.getContext(),
                spannableStringBuilder,
                content,
                EmoticonsKeyboardUtils.getFontHeight(tv_content));

        spannable = QqFilter.spannableFilter(tv_content.getContext(),
                spannable,
                content,
                EmoticonsKeyboardUtils.getFontHeight(tv_content),
                null);
        tv_content.setText(spannable);
    }

    public static void spannableCustomEmoticonFilter(TextView tv_content, String content) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);

        Spannable spannable = EmojiDisplay.spannableFilter(tv_content.getContext(),
                spannableStringBuilder,
                content,
                EmoticonsKeyboardUtils.getFontHeight(tv_content));

        spannable = CustomFilter.spannableFilter(tv_content.getContext(),
                spannable,
                content,
                EmoticonsKeyboardUtils.getFontHeight(tv_content),
                null);
        tv_content.setText(spannable);
    }
}
