package com.example.flycodeveloper.kisileruygulamasi;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private Toolbar toolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    Context context= this;
    private ArrayList<Kisiler> kisilerArrayList;
    private KisilerAdapter adapter;
    private  Veritabani vt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        rv = findViewById(R.id.recylerView);
        fab = findViewById(R.id.floatingActionButton);

        vt = new Veritabani(context);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(context));

        toolbar.setTitle("Kişiler");
        setSupportActionBar(toolbar);

        kisilerArrayList = new KisilerDao().tumKisiler(vt);

       /* kisilerArrayList = new ArrayList<>();
        Kisiler k1 = new Kisiler(1,"ahmet","1234567");
        Kisiler k2 = new Kisiler(2,"can","8776642");
        kisilerArrayList.add(k1);
        kisilerArrayList.add(k2); */

        adapter = new KisilerAdapter(context,kisilerArrayList,vt);
        rv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertGoster();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.actionAra);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.e("onQueryTextSubmit " , query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        kisilerArrayList = new KisilerDao().kisiArama(vt,newText);
        adapter = new KisilerAdapter(context,kisilerArrayList,vt);
        rv.setAdapter(adapter);
        Log.e("onQueryTextChange ",newText);
        return false;
    }

    void alertGoster() {
        View tasarim = getLayoutInflater().inflate(R.layout.alert_tasarim,null);
        final EditText editTextAd = tasarim.findViewById(R.id.editTextAd);
        final EditText editTextTel = tasarim.findViewById(R.id.editTextTel);
        final AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("Kişi Ekle");
        ad.setMessage("Message");
        ad.setView(tasarim);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                
                String kisi_ad = editTextAd.getText().toString().trim();
                String kisi_tel = editTextTel.getText().toString().trim();

                new KisilerDao().kisiEkle(vt,kisi_ad,kisi_tel);
                kisilerArrayList = new KisilerDao().tumKisiler(vt);
                adapter = new KisilerAdapter(context,kisilerArrayList,vt);
                rv.setAdapter(adapter);

                Toast.makeText(context, kisi_ad + " - " + kisi_tel, Toast.LENGTH_SHORT).show();
            }
        });
        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        ad.create().show();

    }
}
