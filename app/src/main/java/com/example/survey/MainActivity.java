package com.example.survey;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tanggalBerkunjung;
    TextInputLayout nomorBPJS;
    TextInputLayout namaResponden;
    Button btnMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        tanggalBerkunjung = findViewById(R.id.textTanggal);
        nomorBPJS = findViewById(R.id.textNomorBPJS);
        namaResponden = findViewById(R.id.textNamaResponden);
        btnMulai = findViewById(R.id.btnMulai);
        btnMulai.setOnClickListener(mulai);
    }

    View.OnClickListener mulai = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nomor_BPJS = nomorBPJS.getEditText().getText().toString();
            String nama_responden = namaResponden.getEditText().getText().toString();
            if (nomor_BPJS.isEmpty() || nama_responden.isEmpty()){
                // Baris kode untuk menampilkan error pada text input nomor kartu BPJS
                if (nomor_BPJS.isEmpty()){
                    setErrorDisplayed(nomorBPJS, "Harap masukkan nomor kartu BPJS Anda");
                    dismissErrorDisplayed(nomorBPJS);
                }
                // Baris kode untuk menampilkan error pada text input nama responden
                if (nama_responden.isEmpty()){
                    setErrorDisplayed(namaResponden, "Harap masukkan nama Anda");
                    dismissErrorDisplayed(namaResponden);
                }
            } else {
                Intent i = new Intent(getApplicationContext(), SurveyActivity.class);
                i.putExtra("nomorBPJS", nomor_BPJS);
                i.putExtra("namaResponden", nama_responden);
                startActivity(i);
            }
        }
    };

    public void setErrorDisplayed(TextInputLayout til, String error_message){
        if (til.getEditText().getText().toString().isEmpty()){
            til.setError(error_message);
        }
    }

    public void dismissErrorDisplayed(final TextInputLayout til){
        til.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                til.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
