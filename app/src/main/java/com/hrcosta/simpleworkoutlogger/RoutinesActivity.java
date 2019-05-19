package com.hrcosta.simpleworkoutlogger;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutinesActivity extends AppCompatActivity {

//    private TabLayout tabLayout;
//    private ViewPager viewPager;
    @BindView(R.id.tablayout_id) TabLayout tabLayout;
    @BindView(R.id.viewpager_id) ViewPager viewPager;
    @BindView(R.id.btn_addtab) ImageButton btnAddTab;


    private String newRoutineName;
    private ViewPagerAdapterRoutines adapter;

//todo set title

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routines);
        ButterKnife.bind(this);
//
//        tabLayout = findViewById(R.id.tablayout_id);
//        viewPager = findViewById(R.id.viewpager_id);
        adapter = new ViewPagerAdapterRoutines(getSupportFragmentManager());

        //add fragment here
        adapter.AddFragment(new RoutinesFragment(),"RoutineA");
        adapter.AddFragment(new RoutinesFragment(),"RoutineB");
        adapter.AddFragment(new RoutinesFragment(),"RoutineC");
        adapter.AddFragment(new RoutinesFragment(),"RoutineD");
        adapter.AddFragment(new RoutinesFragment(),"RoutineE");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        btnAddTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(v.getContext());
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                //TODO show popup asking the name of the routine
                new AlertDialog.Builder(v.getContext()).setIcon(android.R.drawable.ic_dialog_info).setTitle("New Routine")
                        .setMessage("Please imput the name of this routine.")
                        .setView(input)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                newRoutineName = input.getText().toString();
                                adapter.AddFragment(new RoutinesFragment(),newRoutineName);
                                viewPager.setAdapter(adapter);
                            }
                        }).setNegativeButton("Cancel", null).show();


            }
        });

    }
}
