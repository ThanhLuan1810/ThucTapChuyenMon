package com.khuatlethanhluan.ttcm.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.khuatlethanhluan.ttcm.Model.DichVu;
import com.khuatlethanhluan.ttcm.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class DichVuAdapter extends ArrayAdapter<DichVu> {

    private Context context;
    private int resource;
    private ArrayList<DichVu> arrDichVu;

    public DichVuAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DichVu> arrDichVu) {
        super(context, resource, arrDichVu);
        this.context = context;
        this.resource = resource;
        this.arrDichVu = arrDichVu;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dong_dich_vu, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtDichVu = (TextView) convertView.findViewById(R.id.txtDichVu);
            viewHolder.txtGiaDichVu = (TextView) convertView.findViewById(R.id.txtGiaDichVu);
            viewHolder.txtstt = (TextView) convertView.findViewById(R.id.txtstt);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DichVu dichvu = arrDichVu.get(position);
        viewHolder.txtstt.setText(String.valueOf(position+1));
        NumberFormat formatter = new DecimalFormat("#,###");
        String gia = formatter.format(Double.parseDouble(dichvu.getDON_GIA()))+" VNĐ/"+dichvu.getCACH_TINH();
        viewHolder.txtDichVu.setText(dichvu.getTEN_DV());
        viewHolder.txtGiaDichVu.setText(gia);
        return convertView;
    }

    public class ViewHolder {
        TextView txtstt, txtDichVu, txtGiaDichVu;
    }
}
