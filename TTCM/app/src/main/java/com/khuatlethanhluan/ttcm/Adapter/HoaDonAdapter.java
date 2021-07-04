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
import com.khuatlethanhluan.ttcm.Model.HoaDon;
import com.khuatlethanhluan.ttcm.Model.PhongTro;
import com.khuatlethanhluan.ttcm.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends ArrayAdapter<HoaDon> {
    private Context context;
    private int resource;
    private ArrayList<HoaDon> arrHoaDon;

    public HoaDonAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HoaDon> arrDichVu) {
        super(context, resource, arrDichVu);
        this.context = context;
        this.resource = resource;
        this.arrHoaDon = arrDichVu;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dong_hoa_don, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.txtThangHD = (TextView) convertView.findViewById(R.id.txtThangHD);
            viewHolder.txtPhong = (TextView) convertView.findViewById(R.id.txtPhong);
            viewHolder.txtGiaHD = (TextView) convertView.findViewById(R.id.txtGiaHD);
            viewHolder.txtChiPhiKhac = (TextView) convertView.findViewById(R.id.txtChiPhiKhac);
            viewHolder.txtTT = (TextView) convertView.findViewById(R.id.txtTT);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HoaDon hoadon = arrHoaDon.get(position);
        PhongTro b = new PhongTro();
        Cursor a = MainActivity.db.Lay1PhongTro(hoadon.getPHONGID());
        if(a.getCount() == 0){
            viewHolder.txtPhong.setText("Đã xoá phòng");
        }
        else{
            while(a.moveToNext()){
                b.setID(a.getString(0));
                b.setTEN_PHONG(a.getString(1));
                b.setSO_NGUOI(a.getString(2));
                b.setDIEN_TICH(a.getString(3));
                b.setGIA(a.getString(4));
                b.setTHONG_TIN_KHAC(a.getString(5));
                b.setSO_DIEN(a.getString(6));
                b.setSO_NUOC(a.getString(7));
            }
        }
        viewHolder.txtPhong.setText(b.getTEN_PHONG());
        viewHolder.txtThangHD.setText("Tháng "+hoadon.getTHANG());
        NumberFormat formatter = new DecimalFormat("#,###");
        String gia=formatter.format(Double.parseDouble(b.getGIA()));
        String khac=Integer.parseInt(hoadon.getSO_DIEN())*MainActivity.db.LayGiaDien()+Integer.parseInt(hoadon.getSO_NUOC())*MainActivity.db.LayGiaNuoc()+Integer.parseInt(hoadon.getCHI_PHI_KHAC())+"";
        viewHolder.txtChiPhiKhac.setText("Khác( điện, nước,...):"+formatter.format(Double.parseDouble(khac)));
        viewHolder.txtTT.setText("Thành tiền:"+formatter.format(Double.parseDouble(hoadon.getTHANH_TIEN())));
        viewHolder.txtGiaHD.setText("Phòng: "+gia);
        return convertView;
    }

    public class ViewHolder {
        TextView txtPhong, txtChiPhiKhac, txtGiaHD, txtTT, txtThangHD;
    }
}
