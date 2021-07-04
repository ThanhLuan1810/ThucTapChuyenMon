package com.khuatlethanhluan.ttcm;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khuatlethanhluan.ttcm.Adapter.KhachAdapter;
import com.khuatlethanhluan.ttcm.Model.Khach;
import com.khuatlethanhluan.ttcm.Model.PhongTro;

import java.util.ArrayList;
import java.util.Calendar;

public class Phongtro extends AppCompatActivity {

    EditText editTenPhong,editSoNguoi,editDienTich,editGiaThue,editSoDien,editSoNuoc,editTTKhac;
    TextView txtPhong,txtSoNguoi,txtDsPT;
    PhongTro p;
    private ListView lvKhach;
    ArrayList<Khach> arrKhach = new ArrayList<>();
    KhachAdapter customAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_tro);
        if (getIntent().getExtras() != null) {
            p = (PhongTro) getIntent().getSerializableExtra("EDIT");
        }
        linkView();
        txtPhong.setText(p.getTEN_PHONG());
        showKhach();
        actionBar();
        lvKhach=(ListView) findViewById(R.id.lvKhach);
        customAdaper = new KhachAdapter(this,R.layout.dong_khach,arrKhach);
        lvKhach.setAdapter(customAdaper);
        MenuKhach();
    }

    private void actionBar() {
        //Lấy chiều cao của ActionBar
        TypedArray styledAttributes =
                getTheme().obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        //Tạo Drawable mới bằng cách thu/phóng
        Drawable drawable= getResources().getDrawable(R.drawable.nha);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(),
                Bitmap.createScaledBitmap(bitmap, actionBarSize,  actionBarSize, true));

        //Cài đặt tiêu đề, icon cho actionbar
        getSupportActionBar().setTitle("PHÒNG TRỌ");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(newdrawable);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.Them:
                ThemKhachHang();
                return true;
            default:break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void linkView() {
        editTenPhong=(EditText)findViewById(R.id.editTenPhong);
        editSoNguoi=(EditText)findViewById(R.id.editSoNguoi);
        editDienTich=(EditText)findViewById(R.id.editDienTich);
        editGiaThue=(EditText)findViewById(R.id.editGiaThue);
        editSoDien=(EditText)findViewById(R.id.editSoDien);
        editSoNuoc=(EditText)findViewById(R.id.editSoNuoc);
        editTTKhac=(EditText)findViewById(R.id.editTTKhac);
        txtPhong=(TextView) findViewById(R.id.txtPhong);
        txtSoNguoi=(TextView) findViewById(R.id.txtSoNguoi);
        txtDsPT=(TextView) findViewById(R.id.txtDsPT);
    }

    private void showKhach() {
        Cursor a=MainActivity.db.getAllKhach(p.getID());
        if(a.getCount() == 0){
            txtSoNguoi.setText("Phòng trống");
            txtDsPT.setText("Chưa có khách thuê phòng");
        }
        else{
            arrKhach.clear();
            txtSoNguoi.setText(a.getCount()+" người ở trọ");
            while(a.moveToNext()){
                Khach b = new Khach();
                b.setID(a.getString(0));
                b.setTEN_KH(a.getString(1));
                b.setGIOI_TINH(a.getString(2));
                b.setNAM_SINH(a.getString(3));
                b.setCMND(a.getString(4));
                b.setNGAY_CAP(a.getString(5));
                b.setSDT(a.getString(6));
                b.setPHONG_O(a.getString(7));
                b.setHINH_ANH(a.getString(8));
                arrKhach.add(b);
            }
        }
    }

    private void ThemKhachHang() {
        final Dialog dialog=new Dialog(Phongtro.this);
        dialog.setTitle("Thêm mới dịch vụ");
        dialog.setContentView(R.layout.them_khach);
        Button btnThemKhach = (Button)dialog.findViewById(R.id.btnThemKhach);
        Button btnHuy = (Button)dialog.findViewById(R.id.btnHuy);
        final EditText editTenKhach = (EditText)dialog.findViewById(R.id.editTenKhach);
        final EditText editNamSinh = (EditText)dialog.findViewById(R.id.editNamSinh);
        final EditText editCMND = (EditText)dialog.findViewById(R.id.editCMND);
        final EditText editNgay = (EditText)dialog.findViewById(R.id.editNgay);
        final EditText editThang = (EditText)dialog.findViewById(R.id.editThang);
        final EditText editNam = (EditText)dialog.findViewById(R.id.editNam);
        final EditText editSDT = (EditText)dialog.findViewById(R.id.editSDT);
        final RadioGroup rdgGioiTinh = (RadioGroup)dialog.findViewById(R.id.rdgGioitinh);
        RadioButton rdNam = (RadioButton)dialog.findViewById(R.id.rdNam);
        RadioButton rdNu = (RadioButton)dialog.findViewById(R.id.rdNu);

        btnThemKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int a = c.get(Calendar.YEAR);
                if(editTenKhach.getText().toString().equals("")){
                    editTenKhach.setError("Tên khách không thể trống");
                    return;
                }
                else if(editNamSinh.getText().toString().equals("")){
                    editNamSinh.setError("Năm sinh không thể trống");
                    return;
                }
                else if(editSDT.getText().toString().equals("")){
                    editSDT.setError("Số điện thoại không thể trống");
                    return;
                }
                String gt = "";
                int checkedRadioId = rdgGioiTinh.getCheckedRadioButtonId();
                if(checkedRadioId == R.id.rdNam) {
                    gt = "Nam";
                } else if(checkedRadioId == R.id.rdNu ) {
                    gt = "Nữ";
                }
                String NgayCap=editNgay.getText().toString()+"/"+editThang.getText().toString()+"/"+editNam.getText().toString();
                boolean them = MainActivity.db.themKhach(editTenKhach.getText().toString(),gt,editNamSinh.getText().toString(),
                        editCMND.getText().toString(),NgayCap,editSDT.getText().toString(),p.getID(),"");
                if(them == true){
                    Toast.makeText(Phongtro.this,"Thêm khách thành công",Toast.LENGTH_LONG).show();
                    txtDsPT.setText("Danh sách khách thuê");
                }
                else
                    Toast.makeText(Phongtro.this,"Không thêm được",Toast.LENGTH_LONG).show();
                showKhach();
                customAdaper.notifyDataSetChanged();
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

    public void xoaKhach(String id) {
        Integer d = MainActivity.db.xoaKhach(id);
        if(d > 0)
            Toast.makeText(this,"Xoá khách hàng thành công",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Xoá khách hàng thất bại",Toast.LENGTH_LONG).show();
    }

    public void MenuKhach(){
        lvKhach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int pos, long id) {
                final Khach pt = (Khach) arrKhach.get(pos);
                final Dialog dialog=new Dialog(Phongtro.this);
                dialog.setTitle("Chi tiết");
                dialog.setContentView(R.layout.them_khach);
                Button btnThemKhach = (Button)dialog.findViewById(R.id.btnThemKhach);
                Button btnHuy = (Button)dialog.findViewById(R.id.btnHuy);
                RadioButton rdNam = (RadioButton)dialog.findViewById(R.id.rdNam);
                RadioButton rdNu = (RadioButton)dialog.findViewById(R.id.rdNu);
                final EditText editTenKhach = (EditText)dialog.findViewById(R.id.editTenKhach);
                final EditText editNamSinh = (EditText)dialog.findViewById(R.id.editNamSinh);
                final EditText editCMND = (EditText)dialog.findViewById(R.id.editCMND);
                final EditText editNgay = (EditText)dialog.findViewById(R.id.editNgay);
                final EditText editThang = (EditText)dialog.findViewById(R.id.editThang);
                final EditText editNam = (EditText)dialog.findViewById(R.id.editNam);
                final EditText editSDT = (EditText)dialog.findViewById(R.id.editSDT);
                TextView txtTieuDe = (TextView)dialog.findViewById(R.id.txtTieuDe);
                final RadioGroup rdgGioiTinh = (RadioGroup)dialog.findViewById(R.id.rdgGioitinh) ;
                txtTieuDe.setText("Chi tiết khách thuê");
                editTenKhach.setText(pt.getTEN_KH());
                editNamSinh.setText(pt.getNAM_SINH());
                editCMND.setText(pt.getCMND());
                if(pt.getNGAY_CAP().equals("")){
                }
                else {
                    String[] Ngaycap = pt.getNGAY_CAP().split("/");
                    editNgay.setText(Ngaycap[0]);
                    editThang.setText(Ngaycap[1]);
                    editNam.setText(Ngaycap[2]);
                }
                editSDT.setText(pt.getSDT());
                btnThemKhach.setText("Sửa");
                btnHuy.setText("Xoá");
                if(pt.getGIOI_TINH().equals("Nam"))
                    rdNam.setChecked(true);
                else
                    rdNu.setChecked(true);
                btnThemKhach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String gt="";
                        int checkedRadioId = rdgGioiTinh.getCheckedRadioButtonId();
                        if(checkedRadioId== R.id.rdNam) {
                            gt="Nam";
                        } else if(checkedRadioId== R.id.rdNu ) {
                            gt="Nữ";
                        }
                        Calendar c = Calendar.getInstance();
                        int a=c.get(Calendar.YEAR);
                        if(editTenKhach.getText().toString().equals("")){
                            editTenKhach.setError("Tên khách không thể trống");
                            return;
                        }
                        else if(editNamSinh.getText().toString().equals("")){
                            editNamSinh.setError("Năm sinh không thể trống");
                            return;
                        }
                        else if(editSDT.getText().toString().equals("")){
                            editSDT.setError("Số điện thoại không thể trống");
                            return;
                        }
                        String NgayCap="";
                        if(editNgay.getText().toString().equals("")||editThang.getText().toString().equals("")||editNam.getText().toString().equals("")){

                        }
                        else {
                            NgayCap=editNgay.getText().toString()+"/"+editThang.getText().toString()+"/"+editNam.getText().toString();
                        }
                        boolean them=MainActivity.db.capnhatKhach(pt.getID(),editTenKhach.getText().toString(),gt,editNamSinh.getText().toString(),
                                editCMND.getText().toString(),NgayCap,editSDT.getText().toString(),p.getID(),"");
                        if(them==true)
                            Toast.makeText(Phongtro.this,"Sửa thông tin khách thành công",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Phongtro.this,"Không sửa được",Toast.LENGTH_LONG).show();
                        showKhach();
                        customAdaper.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        new AlertDialog.Builder(Phongtro.this)
                                .setMessage("Bạn muốn xoá "+pt.getTEN_KH()+"?")
                                .setCancelable(false)
                                .setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        xoaKhach(pt.getID());
                                        arrKhach.remove(pt);
                                        if(arrKhach.size()==0){
                                            txtSoNguoi.setText("Phòng trống");
                                            txtDsPT.setText("Chưa có khách thuê phòng");
                                        }
                                        else
                                            txtSoNguoi.setText(arrKhach.size()+" người ở trọ");
                                        customAdaper.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("Không", null)
                                .show();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }
}