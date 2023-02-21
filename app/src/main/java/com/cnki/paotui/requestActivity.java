package com.cnki.paotui;

import static com.cnki.paotui.Ikeys.TYPE;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.cnki.paotui.databinding.ActivityRequestBinding;
import com.cnki.paotui.db.Order;
import com.cnki.paotui.db.OrderDao;
import com.cnki.paotui.db.UserDao;
import com.cnki.paotui.utils.SPUtil;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.kongzue.dialogx.dialogs.InputDialog;
import com.kongzue.dialogx.interfaces.OnInputDialogButtonClickListener;
import com.kongzue.dialogx.util.InputInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class requestActivity extends BaseActivity{

    private com.cnki.paotui.databinding.ActivityRequestBinding viewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityRequestBinding.inflate(getLayoutInflater());
         setContentView(viewBinding.getRoot());
        viewBinding.imgShenfenzheng0.setTag(0);
        viewBinding.imgShenfenzheng1.setTag(0);
        viewBinding.imgXueshengzheng.setTag(0);
        initView();
    }

    private void  initView() {
        viewBinding.imgXueshengzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoceImage(viewBinding.imgXueshengzheng);
            }
        });
        viewBinding.imgShenfenzheng1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoceImage(viewBinding.imgShenfenzheng1);
            }
        });
        viewBinding.imgShenfenzheng0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoceImage(viewBinding.imgShenfenzheng0);
            }
        });
        viewBinding.btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(viewBinding.edGetname.getText().toString().toString())){
                    Toast.makeText(mContext, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(viewBinding.edGetxueyuanname.getText().toString().toString())){
                    Toast.makeText(mContext, "请输入学院", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(viewBinding.edGetzhuanye.getText().toString().toString())){
                    Toast.makeText(mContext, "请输入专业", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(viewBinding.edGetnianji.getText().toString().toString())){
                    Toast.makeText(mContext, "请输入年纪", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(viewBinding.edGetshenfenzheng.getText().toString().toString())){
                    Toast.makeText(mContext, "请输入身份证", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(viewBinding.edGetshenfenzheng.getText().toString().length()!=18){
                    Toast.makeText(mContext, "请输入正确身份证位数", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((int )(viewBinding.imgXueshengzheng.getTag())!=1){
                    Toast.makeText(mContext, "请输入学生证照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((int )(viewBinding.imgShenfenzheng1.getTag())!=1){
                    Toast.makeText(mContext, "请输入身份证正面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if((int )(viewBinding.imgShenfenzheng0.getTag())!=1){
                    Toast.makeText(mContext, "请输入身份证反面照片", Toast.LENGTH_SHORT).show();
                    return;
                }
                String number=viewBinding.edGetshenfenzheng.getText().toString().toString();
               String num=number.substring(16,17);
                System.out.println(num);
             int n=  Integer.parseInt(num);
                UserDao userDao=new UserDao(mContext);
                App.user.usertype=1;
                if(n%2==0){
                    App.user.sex=0;
                }else {
                    App.user.sex=1;
                }
                userDao.update( App.user);
                finish();
            }
        });
    }
    private void showChoceImage(ImageView iamge){
        EasyPhotos.createAlbum(this, true,false, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，是否使用宽高数据（false时宽高数据为0，扫描速度更快），[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.cnki.paotui.fileprovider")//参数说明：见下方`FileProvider的配置`
                .setCount(1)//参数说明：最大可选数，默认1
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                       if(photos!=null&&photos.size()>0){
                           iamge.setImageURI(photos.get(0).uri);
                           iamge.setTag(1);
                       }
                    }

                    @Override
                    public void onCancel() {
//                                Toast.makeText(SampleActivity.this, "Cancel", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
