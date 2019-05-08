package com.hrcosta.simpleworkoutlogger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private boolean shouldShow = false;


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @BindView(R.id.tv_user) TextView tvUser;
    @BindView(R.id.tv_arrow) TextView tvArrow;

    @BindView(R.id.btn_logoff) Button btnLogoff;
    @BindView(R.id.compactcalendar_view) CompactCalendarView compactCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        tvUser.setText(firebaseUser.getEmail());

        btnLogoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(CalendarActivity.this,MainActivity.class));
            }
        });

//        final View.OnClickListener showCalendarOnClickLis = getCalendarShowLis();
//        tvArrow.setOnClickListener(showCalendarOnClickLis);

        final View.OnClickListener exposeCalendarListener = getCalendarExposeLis();
        tvArrow.setOnClickListener(exposeCalendarListener);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            firebaseAuth.signOut();
            startActivity(new Intent(CalendarActivity.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @NonNull
    private View.OnClickListener getCalendarShowLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendar();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_up_black_24dp );
                        tvArrow.setCompoundDrawables(img,null,null,null);
                        //todo the arrow button is not updating
                    } else {
                        compactCalendarView.hideCalendar();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_down_black_24dp );
                        tvArrow.setCompoundDrawables(img,null,null,null);
                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

    @NonNull
    private View.OnClickListener getCalendarExposeLis() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!compactCalendarView.isAnimating()) {
                    if (shouldShow) {
                        compactCalendarView.showCalendarWithAnimation();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_up_black_24dp );
                        tvArrow.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                    } else {
                        compactCalendarView.hideCalendarWithAnimation();
                        Drawable img = CalendarActivity.this.getResources().getDrawable( R.drawable.ic_keyboard_arrow_down_black_24dp );
                        tvArrow.setCompoundDrawablesWithIntrinsicBounds(img,null,null,null);

                    }
                    shouldShow = !shouldShow;
                }
            }
        };
    }

}