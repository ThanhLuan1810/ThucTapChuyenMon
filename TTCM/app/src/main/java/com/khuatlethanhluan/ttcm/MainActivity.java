package com.khuatlethanhluan.ttcm;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khuatlethanhluan.ttcm.Adapter.PhongTroAdapter;
import com.khuatlethanhluan.ttcm.Model.PhongTro;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static Database db;
    private ListView lvPhongTro;
    ArrayList<PhongTro> arrPhongTro = new ArrayList<>();
    PhongTroAdapter customAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        lvPhongTro = (ListView) findViewById(R.id.lvPhongTro);
        DichVuMacDinh();
        XoaHDCu();
        show();
        customAdaper = new PhongTroAdapter(this,R.layout.phongtro,arrPhongTro);
        lvPhongTro.setAdapter(customAdaper);
        click();
        registerForContextMenu(lvPhongTro);
    }

    private void show() {
        Cursor a = db.getAllPhongTro();
        if(a.getCount() == 0){
        }
        else{
            arrPhongTro.clear();
            while(a.moveToNext()){
                PhongTro b = new PhongTro();
                b.setID(a.getString(0));
                b.setTEN_PHONG(a.getString(1));
                b.setSO_NGUOI(a.getString(2));
                b.setDIEN_TICH(a.getString(3));
                b.setGIA(a.getString(4));
                b.setTHONG_TIN_KHAC(a.getString(5));
                b.setSO_DIEN(a.getString(6));
                b.setSO_NUOC(a.getString(7));
                arrPhongTro.add(b);
            }
        }
    }

    public void xoaPT(String id) {
        Integer d = db.xoaPhongTro(id);
        Integer p = MainActivity.db.xoaAllKhachP(id);
        Integer h = MainActivity.db.xoaHoaDonP(id);
        if(d > 0 && p > 0 && h > 0)
            Toast.makeText(this,"Xo?? ph??ng tr???, kh??ch,ho?? ????n th??nh c??ng",Toast.LENGTH_LONG).show();
        else if(d > 0 && p > 0)
            Toast.makeText(this,"Xo?? ph??ng tr???, kh??ch th??nh c??ng",Toast.LENGTH_LONG).show();
        else if(d > 0)
            Toast.makeText(this,"Xo?? ph??ng tr??? th??nh c??ng",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Ph??ng tr??? kh??ng t???n t???i",Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        show();
        customAdaper.notifyDataSetChanged();
    }

    private void click() {
        lvPhongTro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, Phongtro.class);
                intent.putExtra("EDIT", (Serializable) customAdaper.getItem(i));
                startActivityForResult(intent, 1);
            }
        });
    }

    private void DichVuMacDinh() {
        SQLiteDatabase sdb = db.getWritableDatabase();
        if(db.getAllDichVu().getCount() == 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("TEN_DV","??i???n");
            contentValues.put("CACH_TINH","kWh");
            contentValues.put("DON_GIA","3500");
            sdb.insert("DICHVU",null,contentValues);
            contentValues=new ContentValues();
            contentValues.put("TEN_DV","N?????c");
            contentValues.put("CACH_TINH","m??");
            contentValues.put("DON_GIA","12000");
            sdb.insert("DICHVU",null,contentValues);
        }
    }

    private void XoaHDCu() {
        Calendar c = Calendar.getInstance();
        String thang = (c.get(Calendar.MONTH)+1) + "";
        Integer d = db.xoaHoaDonT(thang);
    }

    public void ThemPhongTro(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Th??m m???i ph??ng tr???");
        dialog.setContentView(R.layout.them_phong_tro);
        Button btnHuyThemPT = (Button)dialog.findViewById(R.id.btnHuyThemPT);
        Button btnThemPT = (Button)dialog.findViewById(R.id.btnThemPT);
        final EditText editTenPhong = (EditText)dialog.findViewById(R.id.editTenPhong);
        final EditText editSoNguoi = (EditText)dialog.findViewById(R.id.editSoNguoi);
        final EditText editDienTich = (EditText)dialog.findViewById(R.id.editDienTich);
        final EditText editGiaThue = (EditText)dialog.findViewById(R.id.editGiaThue);
        final EditText editSoDien = (EditText)dialog.findViewById(R.id.editSoDien);
        final EditText editSoNuoc = (EditText)dialog.findViewById(R.id.editSoNuoc);
        final EditText editTTKhac = (EditText)dialog.findViewById(R.id.editTTKhac);
        btnThemPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTenPhong.getText().toString().equals("")){
                    editTenPhong.setError("T??n ph??ng kh??ng th??? tr???ng");
                    return;
                }
                else if(editGiaThue.getText().toString().equals("")){
                    editGiaThue.setError("Gi?? thu?? kh??ng th??? tr???ng");
                    return;
                }
                else if(editSoDien.getText().toString().equals("")){
                    editSoDien.setError("S??? ??i???n kh??ng th??? tr???ng");
                    return;
                }
                else if(editSoNuoc.getText().toString().equals("")){
                    editSoNuoc.setError("S??? n?????c kh??ng th??? tr???ng");
                    return;
                }
                boolean them=MainActivity.db.themPhongTro(editTenPhong.getText().toString(),
                        editSoNguoi.getText().toString(),
                        editDienTich.getText().toString(),
                        editGiaThue.getText().toString(),
                        editTTKhac.getText().toString(),
                        editSoDien.getText().toString(),
                        editSoNuoc.getText().toString());
                if(them==true)
                    Toast.makeText(MainActivity.this,"Them phong tro thanh cong",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Them phong tro that bai",Toast.LENGTH_LONG).show();
                show();
                customAdaper.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnHuyThemPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void XemChiTiet(@NonNull final PhongTro p){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setTitle("Chi ti???t");
        dialog.setContentView(R.layout.them_phong_tro);
        Button btnThemPT = (Button)dialog.findViewById(R.id.btnThemPT);
        Button btnHuyThemPT = (Button)dialog.findViewById(R.id.btnHuyThemPT);
        final TextView txtTieuDePT = (TextView)dialog.findViewById(R.id.txtTieuDePT);
        final EditText editTenPhong = (EditText)dialog.findViewById(R.id.editTenPhong);
        final EditText editSoNguoi = (EditText)dialog.findViewById(R.id.editSoNguoi);
        final EditText editDienTich = (EditText)dialog.findViewById(R.id.editDienTich);
        final EditText editGiaThue = (EditText)dialog.findViewById(R.id.editGiaThue);
        final EditText editSoDien = (EditText)dialog.findViewById(R.id.editSoDien);
        final EditText editSoNuoc = (EditText)dialog.findViewById(R.id.editSoNuoc);
        final EditText editTTKhac = (EditText)dialog.findViewById(R.id.editTTKhac);
        txtTieuDePT.setText("Chi ti???t ph??ng tr???");
        editTenPhong.setText(p.getTEN_PHONG().toString());
        editTenPhong.setEnabled(false);
        editSoNguoi.setText(p.getSO_NGUOI().toString());
        editSoNguoi.setHint("");
        editSoNguoi.setEnabled(false);
        editDienTich.setText(p.getDIEN_TICH().toString());
        editDienTich.setEnabled(false);
        editDienTich.setHint("");
        editGiaThue.setText(p.getGIA().toString());
        editGiaThue.setEnabled(false);
        editSoDien.setText(p.getSO_DIEN().toString());
        editSoDien.setEnabled(false);
        editSoNuoc.setText(p.getSO_NUOC().toString());
        editSoNuoc.setEnabled(false);
        editTTKhac.setText(p.getTHONG_TIN_KHAC().toString());
        editTTKhac.setEnabled(false);
        editTTKhac.setHint("");
        btnThemPT.setVisibility(View.GONE);
        btnHuyThemPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void Updatept(final PhongTro p){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Chi ti???t");
        dialog.setContentView(R.layout.them_phong_tro);
        Button btnThemPT=(Button)dialog.findViewById(R.id.btnThemPT);
        Button btnHuyThemPT=(Button)dialog.findViewById(R.id.btnHuyThemPT);
        final TextView txtTieuDePT = (TextView)dialog.findViewById(R.id.txtTieuDePT);
        final EditText editTenPhong = (EditText)dialog.findViewById(R.id.editTenPhong);
        final EditText editSoNguoi = (EditText)dialog.findViewById(R.id.editSoNguoi);
        final EditText editDienTich = (EditText)dialog.findViewById(R.id.editDienTich);
        final EditText editGiaThue = (EditText)dialog.findViewById(R.id.editGiaThue);
        final EditText editSoDien = (EditText)dialog.findViewById(R.id.editSoDien);
        final EditText editSoNuoc = (EditText)dialog.findViewById(R.id.editSoNuoc);
        final EditText editTTKhac = (EditText)dialog.findViewById(R.id.editTTKhac);
        txtTieuDePT.setText("Chi ti???t ph??ng tr???");
        editTenPhong.setText(p.getTEN_PHONG().toString());
        editSoNguoi.setText(p.getSO_NGUOI().toString());
        editDienTich.setText(p.getDIEN_TICH().toString());
        editGiaThue.setText(p.getGIA().toString());
        editSoDien.setText(p.getSO_DIEN().toString());
        editSoNuoc.setText(p.getSO_NUOC().toString());
        editTTKhac.setText(p.getTHONG_TIN_KHAC().toString());
        btnThemPT.setText("S???a");
        btnHuyThemPT.setText("Xo??");
        btnThemPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTenPhong.getText().toString().equals("")){
                    editTenPhong.setError("T??n ph??ng kh??ng th??? tr???ng");
                    return;
                }
                else if(editGiaThue.getText().toString().equals("")){
                    editGiaThue.setError("Gi?? thu?? kh??ng th??? tr???ng");
                    return;
                }
                else if(editSoDien.getText().toString().equals("")){
                    editSoDien.setError("S??? ??i???n kh??ng th??? tr???ng");
                    return;
                }
                else if(editSoNuoc.getText().toString().equals("")){
                    editSoNuoc.setError("S??? n?????c kh??ng th??? tr???ng");
                    return;
                }
                boolean them=MainActivity.db.capnhatPhongTro(p.getID(),editTenPhong.getText().toString(),
                        editSoNguoi.getText().toString(),
                        editDienTich.getText().toString(),
                        editGiaThue.getText().toString(),
                        editTTKhac.getText().toString(),
                        editSoDien.getText().toString(),
                        editSoNuoc.getText().toString());
                if(them==true)
                    Toast.makeText(MainActivity.this,"C???p nh???t ph??ng tr??? th??nh c??ng",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"Kh??ng th??nh c??ng",Toast.LENGTH_LONG).show();
                show();
                customAdaper.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnHuyThemPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("B???n mu???n xo?? ph??ng "+p.getTEN_PHONG()+"?")
                        .setCancelable(false)
                        .setPositiveButton("Xo??", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                xoaPT(p.getID());
                                arrPhongTro.remove(p);
                                customAdaper.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Kh??ng", null)
                        .show();
            }
        });
        dialog.show();
    }

    public void ThemHoaDon(final PhongTro p){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setTitle("L???p ho?? ????n");
        dialog.setContentView(R.layout.them_hoa_don);
        Button btnThemHoaDon = (Button)dialog.findViewById(R.id.btnThemHoaDon);
        Button btnHuy = (Button)dialog.findViewById(R.id.btnHuy);
        final TextView txtTieuDe = (TextView)dialog.findViewById(R.id.txtTieuDe);
        final EditText editThangHD = (EditText)dialog.findViewById(R.id.editThangHD);
        final TextView txtNgay = (TextView)dialog.findViewById(R.id.txtNgay);
        final EditText editDien = (EditText)dialog.findViewById(R.id.editDien);
        final EditText editNuoc = (EditText)dialog.findViewById(R.id.editNuoc);
        final EditText editChiKhac = (EditText)dialog.findViewById(R.id.editChiKhac);
        final int tiendien;
        final int tiennuoc;
        editDien.setHint("S??? ??i???n > "+p.getSO_DIEN());
        editNuoc.setHint("S??? n?????c > "+p.getSO_NUOC());
        tiendien = MainActivity.db.LayGiaDien();
        tiennuoc =  MainActivity.db.LayGiaNuoc();
        Calendar c = Calendar.getInstance();
        final String phong=p.getID();
        final int giathue=Integer.parseInt(p.getGIA());
        int ngay=c.get(Calendar.DATE);
        int thang=c.get(Calendar.MONTH);
        final int nam=c.get(Calendar.YEAR);
        txtTieuDe.setText("L???p ho?? ????n ph??ng "+p.getTEN_PHONG());
        editThangHD.setText((thang+1)+"");
        txtNgay.setText("Ng??y l???p: "+ngay+"/"+(thang+1)+"/"+nam);
        final String Thang=(thang+1)+"";
        final String day=ngay+"/"+(thang+1)+"/"+nam;
        btnThemHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor kt = db.DaLapHoaDon(phong, editThangHD.getText().toString());
                if(editThangHD.getText().toString().equals("")||Integer.parseInt(editThangHD.getText().toString())<1||Integer.parseInt(editThangHD.getText().toString())>12){
                    editThangHD.setError("Th??ng ph???i t??? 1-12");
                    return;
                }
                else if(Integer.parseInt(Thang)==1){
                    if(!(Integer.parseInt(editThangHD.getText().toString())==12||Integer.parseInt(editThangHD.getText().toString())==1)){
                        editThangHD.setError("Th??ng ph???i nh??? h??n ho???c b???ng th??ng hi???n t???i");
                        return;
                    }
                }
                else if(Integer.parseInt(Thang)-Integer.parseInt(editThangHD.getText().toString())>1||Integer.parseInt(Thang)-Integer.parseInt(editThangHD.getText().toString())<0){
                    editThangHD.setError("Th??ng ph???i nh??? h??n ho???c b???ng th??ng hi???n t???i");
                    return;
                }
                else if(kt.getCount()>0){
                    while(kt.moveToNext()) {
                        String[] Ngaycap = kt.getString(7).split("/");
                        if (Ngaycap[Ngaycap.length - 1].equals(nam+"")){
                            if (kt.getString(8).equals("???? thanh to??n")) {
                                Toast.makeText(MainActivity.this, "Ho?? ????n ???? l???p v?? thanh to??n", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                Toast.makeText(MainActivity.this, "Ho?? ????n ???? l???p v?? ch??a thanh to??n", Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    }
                }
                else if(editDien.getText().toString().equals("")){
                    editDien.setError("Ph???i nh???p s??? ??i???n");
                    return;
                }
                else if(Integer.parseInt(editDien.getText().toString())<Integer.parseInt(p.getSO_DIEN())){
                    editDien.setError("S??? ??i???n kh??ng ???????c nh??? h??n "+p.getSO_DIEN());
                    return;
                }
                else if(editNuoc.getText().toString().equals("")){
                    editNuoc.setError("Ph???i nh???p s??? n?????c");
                    return;
                }
                else if(Integer.parseInt(editNuoc.getText().toString())<Integer.parseInt(p.getSO_NUOC())){
                    editNuoc.setError("S??? n?????c kh??ng ???????c nh??? h??n "+p.getSO_NUOC());
                    return;
                }
                else if(editChiKhac.getText().toString().equals("")){
                    editChiKhac.setText("0");
                }
                int SoDien=(Integer.parseInt(editDien.getText().toString())-Integer.parseInt(p.getSO_DIEN()));
                int SoNuoc=(Integer.parseInt(editNuoc.getText().toString())-Integer.parseInt(p.getSO_NUOC()));
                int cpk=Integer.parseInt(editChiKhac.getText().toString());
                String thanhtien=SoDien*tiendien+SoNuoc*tiennuoc+cpk+giathue+"";
                boolean them=MainActivity.db.themHoaDon(editThangHD.getText().toString(),phong,SoDien+"",
                        SoNuoc+"",editChiKhac.getText().toString(),thanhtien,day,"Ch??a thanh to??n");
                if(them==true){
                    dialog.dismiss();
                    Showhd(editThangHD.getText().toString(),day,SoDien,SoNuoc,editChiKhac.getText().toString(),thanhtien,p);
                    Toast.makeText(MainActivity.this,"Th??m ho?? ????n th??nh c??ng",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this,"Th??m ho?? ????n th???t b???i",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void Showhd(String thang,String ngay,int sodien,int sonuoc,String cpk,String tt,final PhongTro p){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.setTitle("Chi ti???t ho?? ????n");
        dialog.setContentView(R.layout.chi_tiet_hoa_don);
        Button btnSuaHoaDon=(Button)dialog.findViewById(R.id.btnSuaHoaDon);
        Button btnHuySua=(Button)dialog.findViewById(R.id.btnHuySua);
        final TextView txtThanhToan=(TextView)dialog.findViewById(R.id.txtThanhToan);
        final TextView txtTieuDeHD=(TextView)dialog.findViewById(R.id.txtTieuDeHD);
        final TextView txtThangCTHD=(TextView)dialog.findViewById(R.id.txtThangCTHD);
        final TextView txtNgayHD=(TextView)dialog.findViewById(R.id.txtNgayHD);
        final TextView txtGiaPhong=(TextView)dialog.findViewById(R.id.txtGiaPhong);
        final TextView txtSoDien=(TextView)dialog.findViewById(R.id.txtSoDien);
        final TextView txtSoNuoc=(TextView)dialog.findViewById(R.id.txtSoNuoc);
        final TextView txtGiaDV=(TextView)dialog.findViewById(R.id.txtGiaDV);
        final TextView txtThanhTien=(TextView)dialog.findViewById(R.id.txtThanhTien);
        final RadioGroup rdgThanhtoan = (RadioGroup)dialog.findViewById(R.id.rdgThanhtoan);
        btnSuaHoaDon.setVisibility(View.GONE);
        txtThanhToan.setVisibility(View.GONE);
        rdgThanhtoan.setVisibility(View.GONE);
        txtTieuDeHD.setText("Ho?? ????n "+ p.getTEN_PHONG());
        txtThangCTHD.setText("Th??ng: "+ thang);
        txtNgayHD.setText("Ng??y l???p "+ ngay);
        NumberFormat formatter = new DecimalFormat("#,###");
        txtGiaPhong.setText(formatter.format(Double.parseDouble(p.getGIA())));
        int tiendien=MainActivity.db.LayGiaDien();
        int tiennuoc=MainActivity.db.LayGiaNuoc();
        String Dien=sodien+"x"+ formatter.format(Double.parseDouble(tiendien+""))+"="+
                formatter.format(Double.parseDouble((sodien*tiendien)+""));
        String Nuoc=sonuoc+"x"+ formatter.format(Double.parseDouble(tiennuoc+""))+"="+
                formatter.format(Double.parseDouble((sonuoc*tiennuoc)+""));
        txtSoDien.setText(Dien);
        txtSoNuoc.setText(Nuoc);
        txtGiaDV.setText(formatter.format(Double.parseDouble(cpk)));
        txtThanhTien.setText(formatter.format(Double.parseDouble(tt)));
        btnHuySua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chinh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent;
        final int result=1;
        switch (item.getItemId()) {
            case R.id.idDV:
                intent = new Intent(this,Dichvu.class);
                startActivityForResult(intent,result);
                break;
            case R.id.idHD:
                intent = new Intent(this,Hoadon.class);
                startActivityForResult(intent,result);
                break;
            case R.id.idThem:
                ThemPhongTro();
                break;
        }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_phong, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int pos = info.position;
        final PhongTro p = arrPhongTro.get(pos);
        switch (item.getItemId())
        {
            case R.id.menuChiTiet:
                XemChiTiet(p);
                return true;

            case R.id.menuUpDel:
                Updatept(p);
                return true;

            case R.id.menuTraPhong:
                Cursor a= MainActivity.db.getAllKhach(p.getID());
                if(a.getCount()!=0){
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Tr??? ph??ng "+p.getTEN_PHONG()+"?")
                            .setCancelable(false)
                            .setPositiveButton("?????ng ??", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Integer d= db.xoaAllKhachP(p.getID());
                                    if(d>0)
                                        Toast.makeText(MainActivity.this,"???? tr??? ph??ng",Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(MainActivity.this,"L???i, vui l??ng thao t??c l???i",Toast.LENGTH_LONG).show();
                                    customAdaper.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Kh??ng", null)
                            .show();
                }
                else
                    Toast.makeText(this,"Ch??a c?? kh??ch thu?? ph??ng",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menuLapHD:
                Cursor b= MainActivity.db.getAllKhach(p.getID());
                if(b.getCount() == 0){
                    Toast.makeText(this,"Thao t??c th???t b???i, ph??ng kh??ng c?? kh??ch thu??",Toast.LENGTH_LONG).show();
                }
                else{
                    ThemHoaDon(p);
                }
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }
}