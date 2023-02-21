package com.cnki.paotui;

import static com.cnki.paotui.Ikeys.TYPE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cnki.paotui.adapter.SelectPhotoAdapter;
import com.cnki.paotui.db.Order;
import com.cnki.paotui.db.OrderDao;
import com.cnki.paotui.utils.SPUtil;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.kongzue.dialogx.dialogs.InputDialog;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialogx.util.InputInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Add1Activity extends BaseActivity{
   Order order;

    EditText ed_get;//取货地址
  //  EditText ed_send;//送达地址
    EditText ed_content;//备注
    TextView tv_enddata;//结束年月日
    TextView tv_endtime;//结束时间
    TextView tv_price;//价格
    TextView tv_sex;//性别

    TextView tv_totalprice;//总价格
    TextView tv_images;//总价格
    View addprice;//
    View minusprice;//
    Button commit;//
    RecyclerView recyclerView;//
    CheckBox checkBox;//
    private SelectPhotoAdapter adapter;
    List<Photo> list=new ArrayList<>();
    {
        list.add(new Photo("",null,"",0,0,0,0,0,0,""));
    }
    int orderType;//0 跑腿 1 学业指导 2 竞赛组队
    /**
     * 隐藏输入软键盘
     * @param context
     * @param view
     */
    public static void hideInputManager(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view !=null && imm != null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add1);
        order= (Order) getIntent().getSerializableExtra(Ikeys.EXTRE_ORDER);
          ed_get=findViewById(R.id.ed_get);
        orderType=getIntent().getIntExtra(TYPE,0);
        ed_content=findViewById(R.id.ed_content);
        ed_content.clearFocus();
        ed_get.clearFocus();
        hideInputManager(mContext,ed_get);
        tv_enddata=findViewById(R.id.tv_enddata);
        tv_endtime=findViewById(R.id.tv_endtime);
        tv_price=findViewById(R.id.tv_price);
        tv_sex=findViewById(R.id.tv_sex);
        tv_totalprice=findViewById(R.id.tv_totalprice);
        tv_images=findViewById(R.id.tv_images);
        addprice=findViewById(R.id.addprice);
        minusprice=findViewById(R.id.minusprice);
        commit=findViewById(R.id.commit);
        recyclerView=findViewById(R.id.recyclerView);
        checkBox=findViewById(R.id.checkBox);
        initView();
    }
    private void  initView() {
        if(order!=null){
            list.clear();
            ed_content.setText(TextUtils.isEmpty(order.content)?"":order.content);
            ed_content.setEnabled(false);
            ed_get.setText(TextUtils.isEmpty(order.title)?"":order.title);
            ed_get.setEnabled(false);
            tv_enddata.setText(order.timestr.split(" ")[0]);
            tv_endtime.setText(order.timestr.split(" ")[1]);
            tv_price.setText(order.price+"");
            tv_sex.setText(order.man);
            checkBox.setVisibility(View.INVISIBLE);
            tv_totalprice.setText(order.price+"");
            if(!TextUtils.isEmpty(order.imagepath)) {
                String imagests[] = order.imagepath.split(",");
                for (String path:imagests
                     ) {
                    Photo photo=new Photo(path, Uri.parse(path),path,0,0,0,0,0,0,"");
                    list.add(photo);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                adapter = new SelectPhotoAdapter(R.layout.item_image_chose, list);
                recyclerView.setAdapter(adapter);



            }
          order.refreshcommit(Add1Activity.this,commit,null);
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            tv_enddata.setText(format.format(new Date()));
            tv_endtime.setText("23:59:59");
            commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCommit();
                }
            });
            tv_enddata.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDataPicker();
                }
            });
            tv_endtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showTimePicker();
                }
            });

            tv_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInputPrice();
                }
            });
            findViewById(R.id.tv_price1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showInputPrice();
                }
            });
            tv_sex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showChoceSex();
                }
            });
            addprice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = 0;
                    try {
                        num = Integer.parseInt(tv_price.getText().toString());
                        num++;
                        tv_price.setText(num + "");
                        tv_totalprice.setText(num + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }
            });
            minusprice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int num = 0;
                    try {
                        num = Integer.parseInt(tv_price.getText().toString());
                        if (num == 1) {
                            Toast.makeText(mContext, "不能再减了", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        num--;
                        tv_price.setText(num + "");
                        tv_totalprice.setText(num + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }
            });
            recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            adapter = new SelectPhotoAdapter(R.layout.item_image_chose, list);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter1, @NonNull View view, int position) {
                    if (adapter.getData().get(position).name == "") {
                        showChoceImage();
                    } else {
                        showChoceImage();
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
//    EditText ed_get;//取货地址
//    EditText ed_send;//送达地址
//    EditText ed_name;//姓名
//    EditText ed_content;//备注
//    TextView tv_enddata;//结束年月日
//
//    TextView tv_endtime;//结束时间
//    TextView tv_num;//件数
//    TextView tv_weight;//重量
//    TextView tv_price;//价格
//    TextView tv_sex;//性别
//
//    TextView tv_totalprice;//总价格
//    TextView tv_images;//总价格
//    View addnum;//
//    View minusnum;//
//    View addprice;//
//
//    View minusprice;//
//    View commit;//
//    SeekBar seekBar;//
//    RecyclerView recyclerView;//
//    CheckBox checkBox;//
    /**
     * 提交
     */
    private void onCommit(){
        if(!checkBox.isChecked()){
            Toast.makeText(mContext, "请先同意协议", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(ed_get.getText().toString())){
            Toast.makeText(mContext, "请输入标题", Toast.LENGTH_SHORT).show();
            return;
        }
//        if(TextUtils.isEmpty(ed_name.getText().toString())){
//            Toast.makeText(mContext, "请输入收获人姓名", Toast.LENGTH_SHORT).show();
//            return;
//        }

        Order order=new Order();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer stringBuffer=new StringBuffer("");
        for (int i = 0; i < list.size(); i++) {
            Photo photo=list.get(i);
            if(!TextUtils.isEmpty(photo.name)){
                if(stringBuffer.toString().equals("")){
                    stringBuffer.append(photo.uri.toString());
                }else {
                    stringBuffer.append(","+photo.uri.toString());
                }
            }
        }
        try {
            order.timestr= tv_enddata.getText().toString().trim()+" "+tv_endtime.getText().toString().trim();

          //  order.startaddress=ed_get.getText().toString().trim();
          //  order.endaddress=ed_send.getText().toString().trim();
            order.endtime=format.parse(tv_enddata.getText().toString().trim()+" "+tv_endtime.getText().toString().trim()).getTime();
            order.creattime=System.currentTimeMillis();
          //  order.num=Integer.parseInt(tv_num.getText().toString());
         //   order.weight=Integer.parseInt(tv_weight.getText().toString());
            if(!TextUtils.isEmpty(ed_content.getText().toString())){
                order.content= ed_content.getText().toString().trim();
            }
            order.imagepath=stringBuffer.toString();
            order.price=Integer.parseInt(tv_price.getText().toString());
            order.man=tv_sex.getText().toString();
            order.creatperson=SPUtil.getInstance().getString(Ikeys.USERNAME);
            order.ordertype=orderType;
            order.title=ed_get.getText().toString();
            //order.name=ed_name.getText().toString();
            order.orderstate=OrderState.WIRTEING;
            new OrderDao(App.mIntance).create(order);
            Toast.makeText(mContext, "下单成功", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }






    }

    /**
     * 展示日期控件
     */
    private void showDataPicker(){


                //获取日历的一个实例，里面包含了当前的年月日
                Calendar calendar=Calendar.getInstance();
                //构建一个日期对话框，该对话框已经集成了日期选择器
                //DatePickerDialog的第二个构造参数指定了日期监听器
                DatePickerDialog dialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                       // String desc=String.format("您选择的日期是%d年%d月%d日",dp_date.getYear(),dp_date.getMonth()+1,dp_date.getDayOfMonth());
                        tv_enddata.setText(i+"-"+(i1+1)+"-"+i2);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
                //把日期对话框显示在界面上
                dialog.show();


    }

    /**
     * 展示时间选择器
     */
    private void showTimePicker(){


        Calendar calendar=Calendar.getInstance();
        //构建一个时间对话框，该对话框已经集成了时间选择器
        //TimePickerDialog的第二个构造参数指定了事件监听器
        TimePickerDialog dialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                tv_endtime.setText(i+":"+i1+":00");
            }
        },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);//true表示使用二十四小时制
        //把时间对话框显示在界面上
        dialog.show();


    }
    private void showInputPrice(){
        InputInfo inputInfo=new InputInfo();
        inputInfo.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        new InputDialog("标题", "请输入赏金金额", "确定", "取消", "请输入赏金金额")
                .setCancelable(false)
                .setInputInfo(inputInfo)
                .setOkButton(new OnInputDialogButtonClickListener<InputDialog>() {
                    @Override
                    public boolean onClick(InputDialog baseDialog, View v, String inputStr) {
                        int price=0;
                        try {
                            price=Integer.parseInt(inputStr);
                            tv_price.setText(inputStr);
                            tv_totalprice.setText(inputStr);

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "请输入纯数字整数", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .show();
    }
    private void showChoceSex(){
        // 创建构造器
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("选择性别");
        // 设置内容,
        final String[] cities = {"不限", "男", "女"};
        builder.setSingleChoiceItems(cities, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 传出数据？？？
                tv_sex.setText(cities[which]);
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
    private void showChoceImage(){
        EasyPhotos.createAlbum(this, true,false, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，是否使用宽高数据（false时宽高数据为0，扫描速度更快），[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.cnki.paotui.fileprovider")//参数说明：见下方`FileProvider的配置`
                .setCount(4)//参数说明：最大可选数，默认1
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        list=photos;
                        if(list.size()<4){
                            list.add(new Photo("",null,"",0,0,0,0,0,0,""));
                        }

                        adapter.setNewInstance(list);
                    }

                    @Override
                    public void onCancel() {
//                                Toast.makeText(SampleActivity.this, "Cancel", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
