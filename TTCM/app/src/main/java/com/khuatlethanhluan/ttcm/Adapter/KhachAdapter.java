package com.khuatlethanhluan.ttcm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.khuatlethanhluan.ttcm.Model.Khach;
import com.khuatlethanhluan.ttcm.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KhachAdapter extends ArrayAdapter<Khach> {

    private Context context;
    private int resource;
    private ArrayList<Khach> arrKhach;

    public KhachAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Khach> arrDichVu) {
        super(context, resource, arrDichVu);
        this.context = context;
        this.resource = resource;
        this.arrKhach = arrDichVu;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dong_khach, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTenKhach = (TextView) convertView.findViewById(R.id.txtTenKhach);
            viewHolder.txtTuoi = (TextView) convertView.findViewById(R.id.txtTuoi);
            viewHolder.txtSDT = (TextView) convertView.findViewById(R.id.txtSDT);
            viewHolder.ivAvatar = (ImageView)convertView.findViewById(R.id.ivAvatar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Khach k = arrKhach.get(position);
        viewHolder.txtTenKhach.setText(k.getTEN_KH());
        Calendar c = Calendar.getInstance();
        int a = c.get(Calendar.YEAR);
        String tuoi = (a - Integer.parseInt(k.getNAM_SINH())) + "";
        if(k.getGIOI_TINH().equals("Nam"))
            viewHolder.ivAvatar.setImageResource(R.drawable.male);
        else
            viewHolder.ivAvatar.setImageResource(R.drawable.female);
        viewHolder.txtTuoi.setText(tuoi+" tuổi");
        viewHolder.txtSDT.setText(k.getSDT());
        return convertView;
    }

    public class ViewHolder {
        TextView txtTenKhach,txtTuoi, txtSDT;
        ImageView ivAvatar;
    }
}
