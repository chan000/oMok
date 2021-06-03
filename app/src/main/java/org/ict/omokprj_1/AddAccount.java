package org.ict.omokprj_1;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.ict.omokprj_1.retrofit.OmokPlayer;
import org.ict.omokprj_1.retrofit.RetrofitClient;
import org.ict.omokprj_1.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAccount extends AppCompatActivity {

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;




    EditText userName;
    Button addbtn;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);


        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();


        getList();


        userName = findViewById(R.id.playerName);
        addbtn = findViewById(R.id.addPlayer);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = userName.getText().toString().trim();
                OmokPlayer player = new OmokPlayer();
                player.setOname(name);
                retrofitInterface.register(player).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("check", response.body()+"");
                        getList();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("d",t.toString());
                    }
                });
            }
        });






        //롱클릭으로 계정 삭제



//        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
//
//
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(getApplicationContext(),"터치 먹음",Toast.LENGTH_SHORT).show();
//                TextView num = (TextView)findViewById(R.id.ono);
//                Integer ono = Integer.parseInt(num.getText().toString());
//                retrofitInterface.remove(ono).enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        getList();
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//
//                    }
//                });
//
//
//                return false;
//            }
//        });




    }//onCreate

    private void getList() {
        retrofitInterface.getOmokList().enqueue(new Callback<List<OmokPlayer>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<OmokPlayer>> call, Response<List<OmokPlayer>> response) {

                ArrayList<String> user = new ArrayList<>();
                ArrayList<String> count = new ArrayList<>();
                ArrayList<String> vo = new ArrayList<>();



                List<OmokPlayer> dataList;

                dataList = response.body();

                for (int i = 0; i < response.body().size(); i++){

                    user.add(dataList.get(i).getOname());
                    count.add("Total : "+dataList.get(i).getTotal() + " Win : " + dataList.get(i).getWin()
                            +" Lose : " + dataList.get(i).getLose());
                    vo.add(""+dataList.get(i).getOno());

                }

                adapter = new UserAdapter(user, count, vo);


                recyclerView.setAdapter(adapter);


                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<OmokPlayer>> call, Throwable t) {
                Log.d("에러", t.toString());
            }
        });
    }//getList
}
