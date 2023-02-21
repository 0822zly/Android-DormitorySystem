package com.cnki.paotui.db;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.cnki.paotui.App;
import com.cnki.paotui.OrderState;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/15
 * Time: 20:40
 * 此类的作用为：
 */
@DatabaseTable(tableName = "order")
public class Order implements MultiItemEntity, Serializable {
    //自增长 唯一
    @DatabaseField(generatedId = true,  unique = true)
    public Integer id;
    @DatabaseField()
    public String startaddress;//开始地址
    @DatabaseField()
    public String title;//标题
    @DatabaseField()
    public String endaddress;//结束地址
    @DatabaseField()
    public String timestr;//开始日期

    @DatabaseField(dataType = DataType.LONG)
    public long endtime;//创建日期

    @DatabaseField(dataType = DataType.LONG)
    public long creattime;//创建日期

    @DatabaseField()
    public String recivedperson;//接收人
    @DatabaseField()
    public String creatperson;//创建人
    @DatabaseField()
    public String name;//姓名
    @DatabaseField()
    public Integer num;//数量
    @DatabaseField()
    public Integer weight;//重量
    @DatabaseField()
    public String content;//备注
    @DatabaseField()
    public String imagepath;
    @DatabaseField()
    public Integer price;//赏金
    @DatabaseField()
    public String man;//1男 0女
    @DatabaseField()
    public int ordertype; //0 快递  1辅导  2组队
    @DatabaseField()
    public int orderstate; //0 发布待接受中 1接受执行中 2执行完结束中
    @DatabaseField()
    public String org0;
    @DatabaseField()
    public String org1;
    @DatabaseField()
    public String org2;

    public Order() {
    }

    @Override
    public int getItemType() {
        return ordertype;
    }
    public void tocommit(Context mContext){
        if(creatperson.equals(App.user.username)){
            Toast.makeText(mContext, "不能抢自己的单", Toast.LENGTH_SHORT).show();
            return;
        }
        if(App.user.usertype!=2){
            Toast.makeText(mContext, "请先成为跑腿员", Toast.LENGTH_SHORT).show();
            return;
        }
        if(App.user.userstate!=1){
            Toast.makeText(mContext, "请先切换用户类型跑腿员", Toast.LENGTH_SHORT).show();
            return;
        }
        if(man.equals("男")&&App.user.sex==0){
            Toast.makeText(mContext, "性别不符合", Toast.LENGTH_SHORT).show();
            return;
        }
        if(man.equals("女")&&App.user.sex==1){
            Toast.makeText(mContext, "性别不符合", Toast.LENGTH_SHORT).show();
            return;
        }
        if(orderstate == OrderState.WIRTEING) {
            orderstate = OrderState.DOING;
            this.recivedperson=App.user.username;
            new OrderDao(mContext).update(this);

        }
    }

    public void refreshcommit(Activity mContext, Button commit, BaseQuickAdapter adapter){
        if(!this.creatperson.equals(App.user.username)) {//如果这个订单不是自己
            if (this.orderstate == OrderState.WIRTEING) {//待下单状态
                commit.setEnabled(true);
                commit.setText("抢单");
                commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tocommit(mContext);
                        if(adapter!=null){
                            adapter.notifyDataSetChanged();
                        }else {
                            mContext.finish();
                        }
                    }
                });
            } else if (this.orderstate == OrderState.DOING) {//正在进行状态
                if(this.recivedperson.equals(App.user.username)) {//正在进行状态,且执行人是自己
                    commit.setEnabled(true);
                    commit.setText("已完成");
                    commit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Order.this.orderstate = OrderState.DONE;
                            OrderDao orderDao = new OrderDao(mContext);
                            orderDao.update(Order.this);
                            if(adapter!=null){
                                adapter.notifyDataSetChanged();
                            }else {
                                mContext.finish();
                            }
                        }
                    });
                }else {//正在进行状态,就是别人在看
                    commit.setEnabled(false);
                    commit.setText("进行中");
                }
            } else if (this.orderstate == OrderState.DONE){
                commit.setEnabled(false);
                commit.setText("待确认");


            }else {
                commit.setEnabled(false);
                commit.setText("已结束");
            }
        }else {//是自己
            if (this.orderstate == OrderState.WIRTEING) {
                commit.setEnabled(true);
                commit.setText("待抢单/取消");
                commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  orderstate=OrderState.END;
                        OrderDao orderDao = new OrderDao(mContext);
                        orderDao.delete(Order.this);
                        if(adapter!=null){
                            adapter.getData().remove(Order.this);
                            adapter.notifyDataSetChanged();
                        }else {
                            mContext.finish();
                        }
                    }
                });
            } else if (this.orderstate == OrderState.DOING) {
                commit.setEnabled(false);
                commit.setText("进行中");
            }
            else if (this.orderstate == OrderState.DONE) {
                commit.setEnabled(true);
                commit.setText("确定完成");
                commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        orderstate=OrderState.END;
                        OrderDao orderDao = new OrderDao(mContext);
                        orderDao.update(Order.this);
                        if(adapter!=null){
                            adapter.notifyDataSetChanged();
                        }else {
                            mContext.finish();
                        }
                    }
                });

            } else {
                commit.setEnabled(false);
                commit.setText("已结束");
            }
        }
    }
}
