package com.ogalo.partympakache.wanlive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    public BottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View becauseRailaIsGod=inflater.inflate(R.layout.bottom_sheetfrag, container, false);


        TextView titl=(TextView)becauseRailaIsGod.findViewById(R.id.title);
        TextView content=(TextView)becauseRailaIsGod.findViewById(R.id.status);
        Button view=(Button)becauseRailaIsGod.findViewById(R.id.view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MainFeed.class));
            }
        });



        Bundle bundle = this.getArguments();
//        String myValue = bundle.getString("title");

//
//        Toast.makeText(getContext(), myValue, Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        WanMaps myActiv=(WanMaps)getActivity();
        String title=myActiv.getTitleso();
        String contents=myActiv.getContentso();

        titl.setText(title);
        content.setText(contents);
//        String contentso=myActiv.getContentso();
//
//        Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show();


        return becauseRailaIsGod;
    }

    }
