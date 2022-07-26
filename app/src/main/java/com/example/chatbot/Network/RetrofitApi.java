package com.example.chatbot.Network;

import com.example.chatbot.Models.MsgModal;
import com.example.chatbot.Models.pinCodeModal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET
    Call<MsgModal> getMessage(@Url String url);
    @GET
    Call<pinCodeModal> getPincode(@Url String url);
}
