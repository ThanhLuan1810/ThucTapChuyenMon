package com.khuatlethanhluan.ttcm;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khuatlethanhluan.ttcm.Adapter.DichVuAdapter;
import com.khuatlethanhluan.ttcm.Model.DichVu;

import java.util.ArrayList;

public class Dichvu extends AppCompatActivity {

    public ListView lvDichVu;
    ArrayList<DichVu> arrDichVu = new ArrayList<>();
    DichVuAdapter customAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dich_vu);

        lvDichVu = (ListView) findViewById(R.id.lvDichVu);
        showAll();
        customAdaper = new DichVuAdapter(this,R.layout.phongtro,arrDichVu);
        lvDichVu.setAdapter(customAdaper);
        registerForContextMenu(lvDichVu);
        actionBar();
        Menu();
    }

    public void showAll(){
        Cursor a = MainActivity.db.getAllDichVu();
        if(a.getCount() == 0){
        }
        else{
            arrDichVu.clear();
            while(a.moveToNext()){
                DichVu b = new DichVu();
                b.setMaDV(a.getString(0));
                b.setTEN_DV(a.getString(1));
                b.setCACH_TINH(a.getString(2));
                b.setDON_GIA(a.getString(3));
                arrDichVu.add(b);
            }
        }
    }

    public void Xoapt(){
        lvDichVu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int pos, long id) {
                final DichVu pt = (DichVu)arrDichVu.get(pos);
                if(pos==0 || pos==1)
                    Toast.makeText(Dichvu.this,"Kh??ng ???????c xo?? d??ng n??y",Toast.LENGTH_LONG).show();
                else{
                    new AlertDialog.Builder(Dichvu.this)
                            .setMessage("B???n mu???n xo?? d???ch v??? "+pt.getTEN_DV()+"?")
                            .setCancelable(false)
                            .setPositiveButton("Xo??", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    xoaPT(pt.getMaDV());
                                    arrDichVu.remove(pt);
                                    customAdaper.notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Kh??ng", null)
                            .show();
                }
                return false;
            }
        });
    }

    public void ThemDV(View view){
        final Dialog dialog=new Dialog(Dichvu.this);
        dialog.setTitle("Th??m m???i d???ch v???");
        dialog.setContentView(R.layout.them_dich_vu);
        Button btnThemDichVu=(Button)dialog.findViewById(R.id.btnThemDichVu);
        Button btnHuy=(Button)dialog.findViewById(R.id.btnHuy);
        final EditText editTenDV=(EditText)dialog.findViewById(R.id.editTenDV);
        final EditText editCachTinh=(EditText)dialog.findViewById(R.id.editCachTinh);
        final EditText editGia=(EditText)dialog.findViewById(R.id.editGia);
        btnThemDichVu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTenDV.getText().toString().equals("")){
                    editTenDV.setError("Ph???i nh???p t??n d???ch v???");
                    return;
                }
                else if(editCachTinh.getText().toString().equals("")){
                    editCachTinh.setError("Ph???i nh???p qui c??ch");
                    return;
                }
                else if(editGia.getText().toString().equals("")){
                    editGia.setText("0");
                }
                boolean them=MainActivity.db.themDichVu(editTenDV.getText().toString(),editCachTinh.getText().toString(),editGia.getText().toString());
                if(them==true)
                    Toast.makeText(Dichvu.this,"Th??m d???ch v??? th??nh c??ng",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Dichvu.this,"Kh??ng th??m ???????c",Toast.LENGTH_LONG).show();
                showAll();
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

    public void xoaPT(String id) {
        Integer d=MainActivity.db.xoaDichVu(id);
        if(d>0)
            Toast.makeText(this,"Xo?? d???ch v??? th??nh c??ng",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this,"Xo?? d???ch v??? th???t b???i",Toast.LENGTH_LONG).show();
    }

    public void Menu(){
        lvDichVu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v,
                                           int pos, long id) {
                final DichVu pt = (DichVu)arrDichVu.get(pos);
                final Dialog dialog=new Dialog(Dichvu.this);
                dialog.setTitle("Chi ti???t");
                dialog.setContentView(R.layout.them_dich_vu);
                Button btnThemDichVu=(Button)dialog.findViewById(R.id.btnThemDichVu);
                Button btnHuy=(Button)dialog.findViewById(R.id.btnHuy);
                TextView txtTieuDe=(TextView)dialog.findViewById(R.id.txtTieuDe);
                final EditText editTenDV=(EditText)dialog.findViewById(R.id.editTenDV);
                final EditText editCachTinh=(EditText)dialog.findViewById(R.id.editCachTinh);
                final EditText editGia=(EditText)dialog.findViewById(R.id.editGia);
                txtTieuDe.setText("Chi ti???t d???ch v???");
                editTenDV.setText(pt.getTEN_DV());
                editCachTinh.setText(pt.getCACH_TINH());
                editGia.setText(pt.getDON_GIA());
                if(pos==0||pos==1){
                    btnHuy.setEnabled(false);
                    editTenDV.setEnabled(false);
                    editCachTinh.setEnabled(false);
                }
                btnThemDichVu.setText("S???a");
                btnHuy.setText("Xo??");
                btnThemDichVu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(editTenDV.getText().toString().equals("")){
                            editTenDV.setError("Ph???i nh???p t??n d???ch v???");
                            return;
                        }
                        else if(editCachTinh.getText().toString().equals("")){
                            editCachTinh.setError("Ph???i nh???p qui c??ch");
                            return;
                        }
                        else if(editGia.getText().toString().equals("")){
                            editGia.setText("0");
                        }
                        boolean them=MainActivity.db.capnhatDichVu(pt.getMaDV(),editTenDV.getText().toString(),editCachTinh.getText().toString(),editGia.getText().toString());
                        if(them==true)
                            Toast.makeText(Dichvu.this,"S???a d???ch v??? th??nh c??ng",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Dichvu.this,"Kh??ng s???a ???????c",Toast.LENGTH_LONG).show();
                        showAll();
                        customAdaper.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        new AlertDialog.Builder(Dichvu.this)
                                .setMessage("B???n mu???n xo?? d???ch v??? "+pt.getTEN_DV()+"?")
                                .setCancelable(false)
                                .setPositiveButton("Xo??", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        xoaPT(pt.getMaDV());
                                        arrDichVu.remove(pt);
                                        customAdaper.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("Kh??ng", null)
                                .show();
                    }
                });
                dialog.show();
                return false;
            }
        });
    }

    //ActionBar
    public void actionBar(){
        //C??i ?????t ti??u ?????, icon cho actionbar
        getSupportActionBar().setTitle("D???CH V???");
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