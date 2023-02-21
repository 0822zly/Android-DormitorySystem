package com.cnki.paotui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.cnki.paotui.App;
import com.cnki.paotui.OrderState;
import com.cnki.paotui.R;
import com.cnki.paotui.db.Order;
import com.cnki.paotui.db.OrderDao;

import java.util.List;

/**
 * Created by Android Studio.
 * User: cjr
 * Date: 2023/1/19
 * Time: 16:53
 * 此类的作用为：
 */
public class OrderAdapter  extends BaseMultiItemQuickAdapter<Order, BaseViewHolder> {


    public OrderAdapter(@Nullable List<Order> data) {
        super(data);
        addItemType(0,R.layout.item_order1);
        addItemType(1,R.layout.item_order);
        addItemType(2,R.layout.item_order);
    }
    public boolean isMyList;//是否是自己展示额列表，如果是就不展示抢单

    public boolean isMyList() {
        return isMyList;
    }

    public void setMyList(boolean myList) {
        isMyList = myList;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Order order) {
        baseViewHolder.setText(R.id.tv_icon,getTypeName(order));
        baseViewHolder.setText(R.id.tv_title,"完成时间:"+order.timestr);
        baseViewHolder.setText(R.id.tv_shangjinnum,order.price+"");
        baseViewHolder.setText(R.id.tv_orderid,"订单号"+order.creattime);
       Button button= baseViewHolder.getView(R.id.tv_orderstate);
       order.refreshcommit((Activity) getContext(),button,OrderAdapter.this);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                order.tocommit(getContext());
//                notifyDataSetChanged();
//            }
//        });
//       if(!isMyList) {
//           if (order.orderstate == OrderState.WIRTEING) {
//               button.setEnabled(true);
//               button.setText("抢单");
//           } else if (order.orderstate == OrderState.DOING) {
//               button.setEnabled(false);
//               button.setText("进行中");
//           } else if (order.orderstate == OrderState.DONE) {
//               button.setEnabled(false);
//               button.setText("待确认");
//           } else {
//               button.setEnabled(false);
//               button.setText("已结束");
//           }
//       }else {
//           if (order.orderstate == OrderState.WIRTEING) {
//               button.setEnabled(false);
//               button.setText("待抢单");
//           } else if (order.orderstate == OrderState.DOING) {
//               button.setEnabled(false);
//               button.setText("进行中");
//           } else if (order.orderstate == OrderState.DONE) {
//               button.setEnabled(false);
//               button.setText("确认");
//           } else {
//               button.setEnabled(false);
//               button.setText("已结束");
//           }
//       }
        switch (order.getItemType()) {
            case 0:
                baseViewHolder.setText(R.id.item_tv_send,order.startaddress);
                baseViewHolder.setText(R.id.item_tv_reiver,order.endaddress);

                break;
            case 1:

                baseViewHolder.setText(R.id.tv_content,order.content);

                break;
            case 2:
                baseViewHolder.setText(R.id.tv_content,order.content);
                break;
        }

    }
    private String getTypeName(Order order){
        if(order.ordertype==0){
            return "跑腿";
        }
        if(order.ordertype==1){
            return "学业指导";
        }
        if(order.ordertype==2){
            return "竞赛组队";
        }
        return "";
    }
}
