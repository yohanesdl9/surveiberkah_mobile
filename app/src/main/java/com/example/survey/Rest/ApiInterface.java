package com.example.survey.Rest;

import com.example.survey.Model.PostPutDelSurvey;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("survey")
    Call<PostPutDelSurvey> postSurvey(@Field("kode_faskes") String kode_faskes, @Field("nama_faskes") String nama_faskes,
                                     @Field("nomor_bpjs") String nomor_bpjs, @Field("nama_responden") String nama_responden,
                                     @Field("tanggal_berkunjung") String tanggal_berkunjung, @Field("jawaban_1") String jawaban_1,
                                     @Field("jawaban_2") String jawaban_2, @Field("jawaban_3") String jawaban_3,
                                     @Field("jawaban_4") String jawaban_4, @Field("jawaban_5") String jawaban_5,
                                     @Field("jawaban_6") String jawaban_6, @Field("jawaban_7") String jawaban_7,
                                     @Field("jawaban_8") String jawaban_8, @Field("saran") String saran);
}
