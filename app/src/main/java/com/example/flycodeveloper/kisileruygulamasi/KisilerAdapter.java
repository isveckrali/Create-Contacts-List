package com.example.flycodeveloper.kisileruygulamasi;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class KisilerAdapter extends RecyclerView.Adapter<KisilerAdapter.CardTasarimTutucu> {

    private Context context;
    private List<Kisiler> kisilerListe;
    private  Veritabani vt;

    public KisilerAdapter(Context context, List<Kisiler> kisilerListe, Veritabani vt) {
        this.context = context;
        this.kisilerListe = kisilerListe;
        this.vt = vt;
    }

    @NonNull
    @Override
    public CardTasarimTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.kisi_card_tasarim,parent,false);
        return new CardTasarimTutucu(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardTasarimTutucu holder, int position) {

        final Kisiler kisiler = kisilerListe.get(position);
        holder.textViewKisiBili.setText(kisiler.getKisi_ad() + " - " + kisiler.getKisi_tel());
        holder.imageViewNokta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,holder.imageViewNokta);
                popupMenu.getMenuInflater().inflate(R.menu.popop_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.action_sil:
                                Snackbar.make(holder.imageViewNokta,"Kişi Silinsinmi",Snackbar.LENGTH_SHORT)
                                .setAction("Evet", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new KisilerDao().kisiSil(vt,kisiler.getKisi_id());
                                        kisilerListe = new KisilerDao().tumKisiler(vt);
                                        notifyDataSetChanged();
                                    }
                                })
                                        .show();
                                return true;
                            case R.id.action_guncelle:
                                alertGoster(kisiler);
                                //Toast.makeText(context, kisiler.getKisi_ad() +" - " + kisiler.getKisi_tel(), Toast.LENGTH_SHORT).show();
                                //Snackbar.make(holder.imageViewNokta,"Güncelle tıklandı",Snackbar.LENGTH_SHORT).show();
                                return true;
                                default:

                                    return false;
                        }
                    }
                });


                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kisilerListe.size();
    }

    public class CardTasarimTutucu extends RecyclerView.ViewHolder {
        TextView textViewKisiBili;
        private ImageView imageViewNokta;

        public CardTasarimTutucu(View itemView) {
            super(itemView);

            textViewKisiBili = itemView.findViewById(R.id.textViewKisiBilgi);
            imageViewNokta = itemView.findViewById(R.id.imageViewVert);
        }
    }

    void alertGoster(final Kisiler kisi) {
        View tasarim = LayoutInflater.from(context).inflate(R.layout.alert_tasarim,null);
        final EditText editTextAd = tasarim.findViewById(R.id.editTextAd);
        final EditText editTextTel = tasarim.findViewById(R.id.editTextTel);
        editTextAd.setText(kisi.getKisi_ad());
        editTextTel.setText(kisi.getKisi_tel());
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        ad.setTitle("Kişi Guncelle");
        ad.setMessage("Message");
        ad.setView(tasarim);
        ad.setPositiveButton("Guncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String kisi_ad = editTextAd.getText().toString().trim();
                String kisi_tel = editTextTel.getText().toString().trim();
                new KisilerDao().kisiGuncelle(vt,kisi.getKisi_id(),kisi_ad,kisi_tel);
                kisilerListe = new KisilerDao().tumKisiler(vt);
                notifyDataSetChanged();
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
