package com.example.chatbot.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.chatbot.Adapter.ChatRVAdapter;
import com.example.chatbot.Models.ChatsModal;
import com.example.chatbot.Models.MsgModal;
import com.example.chatbot.Models.PostOfficeData;
import com.example.chatbot.Models.pinCodeModal;
import com.example.chatbot.Network.RetrofitApi;
import com.example.chatbot.R;
import com.example.chatbot.utils.cUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements cUtils {
    private RecyclerView chatsRV;
    private EditText userMegEdt;
    private ImageButton sendMsgFab;
    private  final  String BOT_KEY="bot";
    private  final  String USER_KEY="user";
    private ArrayList<ChatsModal> chatsModalArrayList;
    private ChatRVAdapter chatRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chatsRV=findViewById(R.id.idRVChats);
        userMegEdt = findViewById(R.id.idEdtMessage);
        sendMsgFab= findViewById(R.id.idIBSend);
        chatsModalArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModalArrayList,this);
        LinearLayoutManager manager =  new LinearLayoutManager(this);
        chatsRV.setLayoutManager(manager);
        chatsRV.setAdapter(chatRVAdapter);
        sendMsgFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userMegEdt.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter a messsage",Toast.LENGTH_SHORT).show();
                    return;
                }else if(userMegEdt.getText().toString().length()==6 && TextUtils.isDigitsOnly(userMegEdt.getText().toString())){
                    getDataFromPinCode(userMegEdt.getText().toString());
                    chatsRV.scrollToPosition(chatsModalArrayList.size() - 1);

                }else{
                    getResponse(userMegEdt.getText().toString());
                    chatsRV.scrollToPosition(chatsModalArrayList.size() - 1);
                }
                userMegEdt.setText("");
                chatsRV.scrollToPosition(chatsModalArrayList.size() - 1);

            }
        });

    }
    private  void getDataFromPinCode(String pinCode){
        chatsModalArrayList.add(new ChatsModal(pinCode,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();
        String url = "http://www.postalpincode.in/api/pincode/" + pinCode;
        String BASE_URL= "http://www.postalpincode.in/";
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<pinCodeModal> call = retrofitApi.getPincode(url);
        call.enqueue(new Callback<pinCodeModal>() {
            @Override
            public void onResponse(Call<pinCodeModal> call, Response<pinCodeModal> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus().toString().equals("Success")){
                        pinCodeModal modal=response.body();
                        ArrayList<PostOfficeData> postOfficeDataList=modal.getPostOffice();
                        String display = "Name: "+postOfficeDataList.get(0).getName()+"\n"+"District: "+postOfficeDataList.get(0).getDistrict()+"\n"+"District: "+postOfficeDataList.get(0).getDistrict()+"\n"+"Division: "+postOfficeDataList.get(0).getDivision()+"\n"+"Region: "+postOfficeDataList.get(0).getRegion()+"\n"+"Country: "+postOfficeDataList.get(0).getCountry()+"\n";
                        chatsModalArrayList.add(new ChatsModal(display,BOT_KEY));
                        chatRVAdapter.notifyDataSetChanged();
                        chatsRV.scrollToPosition(chatsModalArrayList.size() - 1);
                    }else{
                        chatsModalArrayList.add(new ChatsModal(response.body().getMessage().toString(),BOT_KEY));
                        chatRVAdapter.notifyDataSetChanged();
                    }


                }
            }

            @Override
            public void onFailure(Call<pinCodeModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please Check your Pincode ",BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }
        });

    }
    private  void  getResponse(String  message){
        chatsModalArrayList.add(new ChatsModal(message,USER_KEY));
        chatRVAdapter.notifyDataSetChanged();

        String url = "http://api.brainshop.ai/get?bid="+cUtils.Brain_id+"&key="+cUtils.Brain_Api_key+"&uid=[uid]&msg="+message;
        String BASE_URL= "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<MsgModal> call = retrofitApi.getMessage(url);




        call.enqueue(new Callback<MsgModal>() {
            @Override
            public void onResponse(Call<MsgModal> call, Response<MsgModal> response) {
                if(response.isSuccessful()){
                    MsgModal modal=response.body();
                    chatsModalArrayList.add(new ChatsModal(modal.getCnt(),BOT_KEY));
                    chatRVAdapter.notifyDataSetChanged();
                    chatsRV.scrollToPosition(chatsModalArrayList.size() - 1);

                }
            }

            @Override
            public void onFailure(Call<MsgModal> call, Throwable t) {
                chatsModalArrayList.add(new ChatsModal("Please revert your question ",BOT_KEY));
            chatRVAdapter.notifyDataSetChanged();
            }
        });



    }
}