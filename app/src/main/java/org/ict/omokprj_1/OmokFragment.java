package org.ict.omokprj_1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.ict.omokprj_1.retrofit.OmokPlayer;
import org.ict.omokprj_1.retrofit.RetrofitClient;
import org.ict.omokprj_1.retrofit.RetrofitInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OmokFragment extends OmokBaseFragment {

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;


    public static OmokFragment getInstance(){return new OmokFragment();}

    @Override
    public int getFragmentId() {
        return R.layout.fragment_omok;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getFragmentView();
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();
        TextView user1 = (TextView)view.findViewById(R.id.user1);
        TextView user2 = (TextView)view.findViewById(R.id.user2);

        //setText를 이용해서 데이터베이스에서 값을 받아온다.
        retrofitInterface.getOmokList().enqueue(new Callback<List<OmokPlayer>>() {
            @Override
            public void onResponse(Call<List<OmokPlayer>> call, Response<List<OmokPlayer>> response) {
                List<OmokPlayer> list = response.body();
                user1.setText(list.get(0).getOname());
                user2.setText(list.get(1).getOname());

                Log.d("일단",list.get(1).getOname());

            }

            @Override
            public void onFailure(Call<List<OmokPlayer>> call, Throwable t) {

            }
        });



    }

    @Override
    public void onResume(Preferences preferences) {
        super.onResume(preferences);
        makeMove();

    }

    @Override
    protected boolean makeMove() {
        if (super.makeMove()) {
            getBoardView().setBoard(logic.getBoard(), prevX, prevY);
            updatePutButtonStates();
            return true;
        }
        return false;
    }
    private void updatePutButtonStates() {
        View view = getFragmentView();
        ImageButton btnFirst = (ImageButton)view.findViewById(R.id.btnPut);

    }




}
