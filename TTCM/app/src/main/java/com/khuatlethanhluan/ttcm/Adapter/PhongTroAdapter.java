package com.khuatlethanhluan.ttcm.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.khuatlethanhluan.ttcm.MainActivity;
import com.khuatlethanhluan.ttcm.Model.PhongTro;
import com.khuatlethanhluan.ttcm.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class PhongTroAdapter extends ArrayAdapter<PhongTro> {

    private Context context;
    private int resource;
    private ArrayList<PhongTro> arrPhongTro;


    public PhongTroAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PhongTro> arrPhongTro) {
        super(context, resource, arrPhongTro);
        this.context = context;
        this.resource = resource;
        this.arrPhongTro = arrPhongTro;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.phongtro, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtTenPhong = (TextView) convertView.findViewById(R.id.txtTenPhong);
            viewHolder.txtGia = (TextView) convertView.findViewById(R.id.txtGia);
            viewHolder.txtSoNguoi = (TextView) convertView.findViewById(R.id.txtSoNguoi);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PhongTro phongtro = arrPhongTro.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
        viewHolder.txtTenPhong.setText(phongtro.getTEN_PHONG());
        viewHolder.txtGia.setText(formatter.format(Double.parseDouble(phongtro.getGIA())));
        Cursor a= MainActivity.db.getAllKhach(phongtro.getID());
        if(a.getCount() == 0){
            viewHolder.txtSoNguoi.setText("Phòng trống");
        }
        else{
            viewHolder.txtSoNguoi.setText(a.getCount()+" người ở");
        }
        return convertView;
    }

    public class ViewHolder {
        TextView txtTenPhong, txtGia, txtSoNguoi;
    }
}
