package org.ict.omokprj_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.ict.omokprj_1.retrofit.OmokPlayer;
import org.ict.omokprj_1.retrofit.RetrofitClient;
import org.ict.omokprj_1.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MainHolder> {




    private ArrayList<String> maintext1,maintext2 = null;

    private ArrayList<String> vo = null;
    MainHolder mainHolder;
    private OnItemClickListener clickListener;


    public UserAdapter(ArrayList<String> maintext1, ArrayList<String> maintext2, ArrayList<String> vo) {
        this.maintext1 = maintext1;
        this.maintext2 = maintext2;
        this.vo = vo;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }


    public static class MainHolder extends RecyclerView.ViewHolder{
        private RetrofitClient retrofitClient;
        private RetrofitInterface retrofitInterface;


        public TextView name,count, ono;

        public MainHolder(View view){
            super(view);
            name = view.findViewById(R.id.test1);
            count = view.findViewById(R.id.test2);
            ono = view.findViewById(R.id.ono);
            retrofitClient = RetrofitClient.getInstance();
            retrofitInterface = RetrofitClient.getRetrofitInterface();

            //Toast.makeText(itemView.getContext(), ono.getText().toString(), Toast.LENGTH_SHORT).show();
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Integer a = Integer.parseInt(ono.getText().toString());
                    retrofitInterface.remove(a).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        Toast.makeText(itemView.getContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });

                    return true;
                }
            });
        }
        public void setItem(String maintext1, String maintext2, String vo){
            name.setText(maintext1);
            count.setText(maintext2);
            ono.setText(vo);
        }

    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public UserAdapter.MainHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_holder_view,parent,false);
        mainHolder = new MainHolder(holderView);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull UserAdapter.MainHolder holder, int position) {


        String ono = this.vo.get(position);
        String user = this.maintext1.get(position);
        String count = this.maintext2.get(position);
//        mainHolder.ono.setText(ono);
//        mainHolder.maintext1.setText(user);
//        mainHolder.maintext2.setText(count);
        holder.setItem(user, count, ono);

    }

    @Override
    public int getItemCount() {
        return maintext1.size();
    }
}
