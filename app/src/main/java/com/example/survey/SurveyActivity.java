package com.example.survey;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.survey.Model.PostPutDelSurvey;
import com.example.survey.Rest.ApiClient;
import com.example.survey.Rest.ApiInterface;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity {

    int flag = 0;

    LinearLayout linearQuestion, linearSaran;
    RadioGroup answer, answer1;
    TextView txtQuestion, txtQuestion1;
    TextInputLayout textInputSaran;
    Button btnBack, btnNext, btnDone, btnBackSaran;
    ApiInterface mApiInterface;

    String[] questions = {
            "Apakah hari dan jam praktik pelayanan sesuai dengan yang tercantum pada papan nama fasilitas kesehatan?",
            "Apakah ada petugas administrasi yang melayani Anda?",
            "Apakah Anda mendapatkan informasi hak dan kewajiban sebagai peserta JKN-KIS?",
            "Apakah sarana prasarana ruang tunggu di fasilitas kesehatan ini dapat membuat Anda merasa nyaman?",
            "Apakah waktu anda menunggu untuk pelayanan kesehatan di fasilitas kesehatan ini kurang dari 30 menit?",
            "Apakah dokter memberikan pelayanan kesehatan dengan baik (memeriksa Anda, menjelaskan kondisi kesehatan Anda)?",
            "Apakah fasilitas kesehatan ini membedakan pelayanan (jam pelayanan, ruang tunggu, petugas yang melayani) antara peserta JKN-KIS dengan pasien umum? (ket: perbedaan dengan JKN-KIS lebih tidak nyaman)",
            "Apakah Anda dikenakan biaya pada saat pelayanan?"
    };

    String[] answers = new String[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        init();
        linearSaran.setVisibility(View.GONE);
        txtQuestion.setText(questions[flag]);
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    private void init(){
        linearQuestion = findViewById(R.id.linearSurvey);
        linearSaran = findViewById(R.id.linearSaran);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtQuestion1 = findViewById(R.id.txtQuestion1);
        answer = findViewById(R.id.radioAnswer);
        answer1 = findViewById(R.id.radioAnswer1);
        textInputSaran = findViewById(R.id.textInputSaran);
        btnBack = findViewById(R.id.btnKembali);
        btnBackSaran = findViewById(R.id.btnKembaliSaran);
        btnNext = findViewById(R.id.btnBerikutnya);
        btnDone = findViewById(R.id.btnSelesai);
        btnBack.setOnClickListener(back);
        btnNext.setOnClickListener(next);
        btnBackSaran.setOnClickListener(backSaran);
        btnDone.setOnClickListener(done);
    }

    View.OnClickListener back = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            flag -= 2;
            if (flag < 0){
                SurveyActivity.super.onBackPressed();
            } else {
                setQuestion();
            }
        }
    };

    View.OnClickListener backSaran = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            flag -= 2; // Kurangi nilai flag dengan 1 (menjadi 7), maju ke satu pertanyaan selanjutnya
            linearQuestion.setVisibility(View.VISIBLE); // Halaman pertanyaan muncul kembali
            linearSaran.setVisibility(View.GONE); // Halaman saran akan menghilang
            setQuestion();
        }
    };

    View.OnClickListener next = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (answer.getCheckedRadioButtonId() != -1 && answer1.getCheckedRadioButtonId() != -1){
                // Pengguna sudah menjawab kedua pertanyaan dan mengklik tombol Next
                switch (answer.getCheckedRadioButtonId()){ // Simpan jawaban pengguna
                    case R.id.radioYes:
                        answers[flag] = "YA";
                        break;
                    case R.id.radioNo:
                        answers[flag] = "TIDAK";
                        break;
                }
                switch (answer1.getCheckedRadioButtonId()){ // Simpan jawaban pengguna
                    case R.id.radioYes1:
                        answers[flag + 1] = "YA";
                        break;
                    case R.id.radioNo1:
                        answers[flag + 1] = "TIDAK";
                        break;
                }
                flag+=2; // Tambahkan nilai flag dengan 1, maju ke satu pertanyaan selanjutnya
                if (flag == 8){ // Pertanyaan terakhir sudah tercapai, masuk ke halaman untuk saran
                    linearQuestion.setVisibility(View.GONE);
                    linearSaran.setVisibility(View.VISIBLE);
                } else { // Pertanyaan terakhir belum tercapai, lanjut ke pertanyaan berikutnya
                     setQuestion();
                }
            } else { // Pengguna belum menjawab tetapi sudah mengklik tombol Next
                Toast.makeText(getApplicationContext(), "Mohon pilih salah satu pilihan jawaban", Toast.LENGTH_SHORT).show();
            }
        }
    };

    View.OnClickListener done = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String saran = textInputSaran.getEditText().getText().toString();
            if (saran.isEmpty()){
                saran = "TIDAK ADA";
            }
            String nomorBPJS = getIntent().getStringExtra("nomorBPJS");
            String namaResponden = getIntent().getStringExtra("namaResponden");
            final String tanggalBerkunjung = getIntent().getStringExtra("tanggalBerkunjung");
            Call<PostPutDelSurvey> postSurveyCall = mApiInterface.postSurvey("DU-U0006", "FKTP DOKTER HERI",
                    nomorBPJS, namaResponden, tanggalBerkunjung, answers[0], answers[1], answers[2], answers[3], answers[4],
                    answers[5], answers[6], answers[7], saran);
            postSurveyCall.enqueue(new Callback<PostPutDelSurvey>() {
                @Override
                public void onResponse(Call<PostPutDelSurvey> call, Response<PostPutDelSurvey> response) {
                    Toast.makeText(getApplicationContext(), "Respon Anda berhasil tersimpan", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), ThankyouActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }

                @Override
                public void onFailure(Call<PostPutDelSurvey> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error: Respon tidak dapat disimpan", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    public void setQuestion(){ // Method untuk mengatur tampilan pertanyaan pada layar
        txtQuestion.setText(questions[flag]);
        txtQuestion1.setText(questions[flag + 1]);
        if (answers[flag] == null && answers[flag + 1] == null){ // Jika pertanyaan sebelumnya/berikutnya belum dijawab, jawaban dikosongkan
            answer.clearCheck();
            answer1.clearCheck();
        } else { // Jika pertanyaan pertanyaan sebelumnya/berikutnya telah terjawab, pilih sesuai jawaban yang disimpan pengguna
            if (answers[flag].equals("YA")){
                answer.check(R.id.radioYes);
            } else if (answers[flag].equals("TIDAK")){
                answer.check(R.id.radioNo);
            }
            if (answers[flag + 1].equals("YA")){
                answer1.check(R.id.radioYes1);
            } else if (answers[flag + 1].equals("TIDAK")){
                answer1.check(R.id.radioNo1);
            }
        }
    }
}
