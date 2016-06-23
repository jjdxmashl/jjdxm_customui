package com.dou361.customui.utils;

import android.content.Context;

import com.dou361.customui.bean.EmoticonEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ParseDataUtils {

    public static ArrayList<EmoticonEntity> ParseQqData(HashMap<String, Integer> data) {
        Iterator iter = data.entrySet().iterator();
        if (!iter.hasNext()) {
            return null;
        }
        ArrayList<EmoticonEntity> emojis = new ArrayList<>();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            EmoticonEntity entity = new EmoticonEntity();
            entity.setContent((String) key);
            entity.setIconUri("" + val);
            emojis.add(entity);
        }
        return emojis;
    }

    public static ArrayList<EmoticonEntity> parseKaomojiData(Context context) {
        ArrayList<EmoticonEntity> textEmotionArray = new ArrayList<>();
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("kaomoji"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                EmoticonEntity bean = new EmoticonEntity(line.trim());
                textEmotionArray.add(bean);
            }
            return textEmotionArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
