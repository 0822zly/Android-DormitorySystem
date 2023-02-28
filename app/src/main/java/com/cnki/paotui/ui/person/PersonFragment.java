package com.cnki.paotui.ui.person;

import static com.cnki.paotui.Ikeys.ISFROMMAIN;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cnki.paotui.AbortActivity;
import com.cnki.paotui.AddBookActivity;
import com.cnki.paotui.App;
import com.cnki.paotui.GlideEngine;
import com.cnki.paotui.HistoryActivity;
import com.cnki.paotui.HistoryBookActivity;
import com.cnki.paotui.Ikeys;
import com.cnki.paotui.MyAccountActivity;
import com.cnki.paotui.R;
import com.cnki.paotui.databinding.FragmentPersonBinding;
import com.cnki.paotui.db.UserDao;
import com.cnki.paotui.ui.login.LoginActivity;
import com.cnki.paotui.ui.pop.SettingPop;
import com.cnki.paotui.utils.SPUtil;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;

public class PersonFragment extends Fragment {


    private com.cnki.paotui.databinding.FragmentPersonBinding binding;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
      //  FragmentPersonBinding fragmentPersonBinding=

        binding = FragmentPersonBinding.inflate(inflater, container, false);
        view = binding.getRoot();




        refreshview();
        return view;
    }
   private void refreshview(){
        if(App.user==null){
            binding.loginout.setText("登入");
            binding.loginout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    intent.putExtra(ISFROMMAIN,true);
                    getActivity().startActivity(intent);
                }
            });
            binding.lineXingcheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "请先登入", Toast.LENGTH_SHORT).show();
                }
            });
//            if(!TextUtils.isEmpty(App.user.url)){
//                binding.imagePhoto.setImageURI(Uri.parse(App.user.url));
//            }
            binding.tvName.setText("");
            binding.lineShenqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "请先登入", Toast.LENGTH_SHORT).show();
                }
            });

            binding.imagePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "请先登入", Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            binding.loginout.setText("注销");
            binding.loginout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    App.user=null;
                    SPUtil.getInstance().setValue(Ikeys.USERNAME,"");
                    SPUtil.getInstance().setValue(Ikeys.PASSWORD,"");
                    intent.putExtra(ISFROMMAIN,true);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
            binding.lineXingcheng.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), MyAccountActivity.class);

                    getActivity().startActivity(intent);
                }
            });
            binding.history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), HistoryBookActivity.class);

                    getActivity().startActivity(intent);
                }
            });
            if(!TextUtils.isEmpty(App.user.url)){
                binding.imagePhoto.setImageURI(Uri.parse(App.user.url));
            }
            binding.tvName.setText(SPUtil.getInstance().getString(Ikeys.USERNAME));
            //我的收藏
            binding.lineShenqing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), AddBookActivity.class);

                    getActivity().startActivity(intent);
                }
            });
            //搜索历史
            binding.searchhistory1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), HistoryActivity.class);
                    startActivity(intent);
                }
            });
            //头像
            binding.imagePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showChoceImage(binding.imagePhoto);
                }
            });
            //关于软件
            binding.guanyu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getActivity(), AbortActivity.class);
                    getActivity().startActivity(intent);
                }
            });
            //设置
            binding.setting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showSet();
                }
            });
        }

   }
    SettingPop settingPop;

    public void showSet() {

            if(settingPop==null) {
                settingPop = new SettingPop(getActivity());
                View jian = settingPop.findViewById(R.id.bt_jian);
                View jia = settingPop.findViewById(R.id.bt_jia);
                View bg1 = settingPop.findViewById(R.id.bg1);
                View bg2 = settingPop.findViewById(R.id.bg2);
                View bg3 = settingPop.findViewById(R.id.bg3);
                View bg4 = settingPop.findViewById(R.id.bg4);
                View tv_light = settingPop.findViewById(R.id.tv_light);
                View tv_night = settingPop.findViewById(R.id.tv_night);
                TextView tvsize = settingPop.findViewById(R.id.tv_pop_size);
                CheckBox ishuyan = settingPop.findViewById(R.id.ishuyan);
                ishuyan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            //  viewBinding.scroll.setBackgroundColor(Color.parseColor("#E8D3A9"));
                        } else {

                        }
                    }
                });
                bg1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#C8E4CB");

                    }
                });
                bg2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#E8D3A9");

                    }
                });
                bg3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#EBEEF7");

                    }
                });
                bg4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("textcolor", "#DFC8A8");

                    }
                });
                tv_light.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("isnight", false);

                    }
                });
                tv_night.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        SPUtil.getInstance().setValue("isnight", true);

                    }
                });
                jian.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int size = SPUtil.getInstance().getInt("textsize");
                        if (size == 10) {
                            Toast.makeText(getActivity(), "范围为10-20", Toast.LENGTH_SHORT).show();
                        } else {
                            tvsize.setText((size - 1) + "");
                            SPUtil.getInstance().setValue("textsize", size - 1);

                        }
                    }
                });
                jia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int size = SPUtil.getInstance().getInt("textsize");
                        if (size == 20) {
                            Toast.makeText(getActivity(), "范围为10-20", Toast.LENGTH_SHORT).show();
                        } else {
                            tvsize.setText((size + 1) + "");
                            SPUtil.getInstance().setValue("textsize", size + 1);

                        }
                    }
                });

            }
            settingPop.showPopupWindow();

    }
    @Override
    public void onResume() {
        super.onResume();
        refreshview();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            refreshview();
        }
    }

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    //选择图片
    private void showChoceImage(ImageView iamge){
        EasyPhotos.createAlbum(this, true,false, GlideEngine.getInstance())//参数说明：上下文，是否显示相机按钮，是否使用宽高数据（false时宽高数据为0，扫描速度更快），[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                .setFileProviderAuthority("com.cnki.paotui.fileprovider")//参数说明：见下方`FileProvider的配置`
                .setCount(1)//参数说明：最大可选数，默认1
                .start(new SelectCallback() {
                    @Override
                    public void onResult(ArrayList<Photo> photos, boolean isOriginal) {
                        if(photos!=null&&photos.size()>0){
                            App.user.url=photos.get(0).path;
                            new UserDao(getContext()).update(App.user);
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