package com.cnki.paotui.utils;
import android.content.Context;
import android.content.SharedPreferences;

import com.cnki.paotui.App;

/**
 * 类名称: SPUtils<br/>
 * 描述: SharedPreferences存储数据的工具类
 *
 * @author:yefx
 * @Date:2016-3-2下午5:01:27
 */
public class SPUtil {
    private static SPUtil instance;
    private SharedPreferences sp;

    public SPUtil(){
        sp = App.mIntance.getSharedPreferences("mksh",  Context.MODE_PRIVATE);
    }

    public static SPUtil getInstance(){
        if(instance == null){
            synchronized (SPUtil.class){
                if(instance == null){
                    instance = new SPUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 设置数据
     * @param key
     * @param value
     */
    public void setValue(String key, Object value){
        SharedPreferences.Editor editor = sp.edit();
        if(value instanceof String){
            editor.putString(key, (String) value);
        }else if(value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if(value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if(value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else if(value instanceof Long){
            editor.putLong(key, (Long) value);
        }
        editor.commit();
    }

    public String getString(String key){
        return sp.getString(key,"");
    }

    public int getInt(String key){
        return sp.getInt(key,0);
    }

    public Boolean getBoolean(String key){
        return sp.getBoolean(key,false);
    }

    public float getFloat(String key){
        return sp.getFloat(key,0);
    }

    public Long getLong(String key){
        return sp.getLong(key,0);
    }


    /**
     * 删除对应的key
     * @param key
     */
    public void remove(String key){
        sp.edit().remove(key).commit();
    }
}

