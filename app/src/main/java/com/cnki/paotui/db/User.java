package com.cnki.paotui.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/15
 * Time: 12:52
 * 此类的作用为：
 */
@DatabaseTable(tableName = "user")
public class User {


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User() {

    }
    //自增长 唯一
    @DatabaseField(generatedId = true,  unique = true)
    public Integer id;
    @DatabaseField( unique = true)
    public String username;
    @DatabaseField
    public String password;
    public String password1;
    @DatabaseField
    public String url;
    @DatabaseField(canBeNull = false)
    public int usertype; //0 普通 1 申请接单员中 2 接单员
    @DatabaseField(canBeNull = false)
    public int userstate; //0 普通状态 1 接单员 状态
    @DatabaseField(canBeNull = false)
    public int sex; //0 女 1 男
    public String getShowType(){
        if(0==usertype){
            return "申请";
        }
        if(1==usertype){
            return "申请中";
        }
        if(2==usertype){
            return "已是跑腿员";
        }
        return  "申请";
    }
}
