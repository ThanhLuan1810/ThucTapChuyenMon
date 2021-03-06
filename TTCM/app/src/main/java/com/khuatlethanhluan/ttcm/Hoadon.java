package com.khuatlethanhluan.ttcm;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khuatlethanhluan.ttcm.Adapter.HoaDonAdapter;
import com.khuatlethanhluan.ttcm.Model.DichVu;
import com.khuatlethanhluan.ttcm.Model.HoaDon;
import com.khuatlethanhluan.ttcm.Model.PhongTro;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Hoadon extends AppCompatActivity {

    TextView txtDsHD;
    RadioButton rdDaTT,rdChuaTT;
    RadioGroup rdgLoai;
    public ListView lvHoaDon;
    public int tiendien,tiennuoc;
    Calendar c = Calendar.getInstance();
    int thang;
    ArrayList<HoaDon> arrHoaDonDa = new ArrayList<>();
    HoaDonAdapter customAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        linkView();
        showHoaDon(1);
        TienDien();
        TienNuoc();
        customAdaper = new HoaDonAdapter(this,R.layout.dong_hoa_don,arrHoaDonDa);
        lvHoaDon.setAdapter(customAdaper);
        LoaiHoaDon();
        SuaHoaDon();
        actionBar();
    }

    private void linkView() {
        txtDsHD = (TextView) findViewById(R.id.txtDsHD);
        rdDaTT = (RadioButton)findViewById(R.id.rdDaTT);
        rdChuaTT = (RadioButton)findViewById(R.id.rdChuaTT);
        rdgLoai = (RadioGroup)findViewById(R.id.rdgLoai);
        lvHoaDon = (ListView) findViewById(R.id.lvHoaDon);
    }

    private void showHoaDon(int loai) {
        Cursor a = MainActivity.db.getAllHoaDon();
        if(a.getCount() == 0){
        }
        else{
            arrHoaDonDa.clear();
            while(a.moveToNext()){
                HoaDon b = new HoaDon();
                b.setID(a.getString(0));
                b.setTHANG(a.getString(1));
                b.setPHONGID(a.getString(2));
                b.setSO_DIEN(a.getString(3));
                b.setSO_NUOC(a.getString(4));
                b.setCHI_PHI_KHAC(a.getString(5));
                b.setTHANH_TIEN(a.getString(6));
                b.setNGAY_LAP(a.getString(7));
                b.setTINH_TRANG(a.getString(8));
                if(loai == 0 && a.getString(8).equals("???? thanh to??n"))
                    arrHoaDonDa.add(b);
                else if(loai == 1&&a.getString(8).equals("Ch??a thanh to??n"))
                    arrHoaDonDa.add(b);
            }
        }
    }

    public void LoaiHoaDon(){
        rdgLoai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRadioId = rdgLoai.getCheckedRadioButtonId();
                if(checkedRadioId == R.id.rdChuaTT) {
                    showHoaDon(1);
                    customAdaper.notifyDataSetChanged();
                } else if(checkedRadioId == R.id.rdDaTT) {
                    showHoaDon(0);
                    customAdaper.notifyDataSetChanged();
                }
            }
        });
    }

    public void TienDien(){
        DichVu b=new DichVu();
        Cursor a= MainActivity.db.get1DichVu("1");
        if(a.getCount() == 0){
            tiendien=1;
        }
        else{
            while(a.moveToNext()){
                b.setMaDV(a.getString(0));
                b.setTEN_DV(a.getString(1));
                b.setCACH_TINH(a.getString(2));
                b.setDON_GIA(a.getString(3));
            }
            tiendien=Integer.parseInt(b.getDON_GIA());
        }
    }

    public void TienNuoc(){
        DichVu b = new DichVu();
        Cursor a = MainActivity.db.get1DichVu("2");
        if(a.getCount() == 0){
            tiennuoc = 1;
        }
        else{
            while(a.moveToNext()){
                b.setMaDV(a.getString(0));
                b.setTEN_DV(a.getString(1));
                b.setCACH_TINH(a.getString(2));
                b.setDON_GIA(a.getString(3));
            }
            tiennuoc = Integer.parseInt(b.getDON_GIA());
        }
    }

    public void SuaHoaDon(){
        lvHoaDon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int pos, long id) {
                final HoaDon p;
                p = (HoaDon) arrHoaDonDa.get(pos);
                final Dialog dialog=new Dialog(Hoadon.this);
                dialog.setTitle("Chi ti???t ho?? ????n");
                dialog.setContentView(R.layout.chi_tiet_hoa_don);
                Button btnSuaHoaDon = (Button)dialog.findViewById(R.id.btnSuaHoaDon);
                Button btnHuySua = (Button)dialog.findViewById(R.id.btnHuySua);
                final TextView txtTieuDeHD = (TextView)dialog.findViewById(R.id.txtTieuDeHD);
                final TextView txtThangCTHD = (TextView)dialog.findViewById(R.id.txtThangCTHD);
                final TextView txtNgayHD = (TextView)dialog.findViewById(R.id.txtNgayHD);
                final TextView txtGiaPhong = (TextView)dialog.findViewById(R.id.txtGiaPhong);
                final TextView txtSoDien = (TextView)dialog.findViewById(R.id.txtSoDien);
                final TextView txtSoNuoc = (TextView)dialog.findViewById(R.id.txtSoNuoc);
                final TextView txtGiaDV = (TextView)dialog.findViewById(R.id.txtGiaDV);
                final TextView txtThanhTien = (TextView)dialog.findViewById(R.id.txtThanhTien);
                final RadioGroup rdgThanhtoan = (RadioGroup)dialog.findViewById(R.id.rdgThanhtoan);
                RadioButton rdDaTTHD = (RadioButton)dialog.findViewById(R.id.rdDaTTHD);
                RadioButton rdChuaTTHD = (RadioButton)dialog.findViewById(R.id.rdChuaTTHD);
                RadioButton rdXoaHD = (RadioButton)dialog.findViewById(R.id.rdXoaHD);
                PhongTro b = new PhongTro();
                Cursor a= MainActivity.db.Lay1PhongTro(p.getPHONGID());
                if(a.getCount() == 0){
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
                    txtTieuDeHD.setText("Ho?? ????n "+b.getTEN_PHONG());
                }
                final PhongTro c = b;
                txtThangCTHD.setText("Th??ng: "+ p.getTHANG());
                txtNgayHD.setText("Ng??y l???p "+ p.getNGAY_LAP());
                NumberFormat formatter = new DecimalFormat("#,###");
                txtGiaPhong.setText(formatter.format(Double.parseDouble(b.getGIA())));
                String Dien=formatter.format(Double.parseDouble(p.getSO_DIEN()))+"x"+
                        formatter.format(Double.parseDouble(tiendien+""))+"="+
                        formatter.format(Double.parseDouble((Integer.parseInt(p.getSO_DIEN())*tiendien)+""));
                String Nuoc=formatter.format(Double.parseDouble(p.getSO_NUOC()))+"x"+
                        formatter.format(Double.parseDouble(tiennuoc+""))+"="+
                        formatter.format(Double.parseDouble((Integer.parseInt(p.getSO_NUOC())*tiennuoc)+""));
                txtSoDien.setText(Dien);
                txtSoNuoc.setText(Nuoc);
                txtGiaDV.setText(formatter.format(Double.parseDouble(p.getCHI_PHI_KHAC())));
                if(p.getTINH_TRANG().equals("Ch??a thanh to??n"))
                    rdChuaTTHD.setChecked(true);
                else if(p.getTINH_TRANG().equals("???? thanh to??n")){
                    rdDaTTHD.setChecked(true);
                    rdDaTTHD.setEnabled(false);
                    rdChuaTTHD.setVisibility(View.GONE);
                    rdXoaHD.setVisibility(View.GONE);
                    btnSuaHoaDon.setVisibility(View.GONE);
                }
                txtThanhTien.setText(p.getTHANH_TIEN());
                btnSuaHoaDon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tt="";
                        int checkedRadioId = rdgThanhtoan.getCheckedRadioButtonId();
                        if(checkedRadioId== R.id.rdChuaTTHD) {
                            tt="Ch??a thanh to??n";
                            return;
                        } else if(checkedRadioId== R.id.rdDaTTHD) {
                            tt="???? thanh to??n";
                            boolean them=MainActivity.db.capnhatHoaDon(p.getID(),p.getTHANG(),p.getPHONGID(),p.getSO_DIEN(),
                                    p.getSO_NUOC(),p.getCHI_PHI_KHAC(),p.getTHANH_TIEN(),p.getNGAY_LAP(),tt);
                            if(them == true){
                                String sd=Integer.parseInt(c.getSO_DIEN())+Integer.parseInt(p.getSO_DIEN())+"";
                                String sn=Integer.parseInt(c.getSO_NUOC())+Integer.parseInt(p.getSO_NUOC())+"";
                                MainActivity.db.capnhatDienNuoc(c,sd,sn);
                                Toast.makeText(Hoadon.this,"C???p nh???t ho?? ????n th??nh c??ng",Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(Hoadon.this,"Kh??ng th??nh c??ng",Toast.LENGTH_LONG).show();
                        } else if(checkedRadioId== R.id.rdXoaHD){
                            Integer them=MainActivity.db.xoaHoaDon(p.getID());
                            if(them>0){
                                Toast.makeText(Hoadon.this,"???? xo?? ho?? ????n",Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(Hoadon.this,"L???i, vui l??ng thao t??c l???i",Toast.LENGTH_LONG).show();
                        }
                        showHoaDon(1);
                        customAdaper.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btnHuySua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    public void actionBar(){
        //L???y chi???u cao c???a ActionBar
        TypedArray styledAttributes =
                getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        //T???o Drawable m???i b???ng c??ch thu/ph??ng
        Drawable drawable= getResources().getDrawable(R.drawable.icon_hoa_don);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap, actionBarSize,  actionBarSize, true));

        //C??i ?????t ti??u ?????, icon cho actionbar
        getSupportActionBar().setTitle("HO?? ????N");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(newdrawable);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:break;
        }

        return super.onOptionsItemSelected(item);
    }
}