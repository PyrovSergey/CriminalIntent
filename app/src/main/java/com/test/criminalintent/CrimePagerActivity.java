package com.test.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<Crime> mCrimes;
    private ImageButton imageButtonFirstCrime;
    private ImageButton imageButtonLastCrime;

    private static final String EXTRA_CRIME_ID = "crime_id";

    public static Intent newIntent(Context context, UUID crime_id) {
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crime_id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        initialization();

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Crime crime = mCrimes.get(position);
                Log.d("TEST", "Получен фрагмент с позицией - " + position);
                setClickableButtons(mViewPager.getCurrentItem());
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++) {
            if (mCrimes.get(i).getId().equals(crimeId)) {
                Log.d("TEST", "Получен выбранный фрагмент - " + i);

                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public void setClickableButtons(int position) {
        if (position == 0) {
            imageButtonFirstCrime.setClickable(false);
            imageButtonFirstCrime.setEnabled(false);
            imageButtonLastCrime.setClickable(true);
            imageButtonLastCrime.setEnabled(true);
        } else if (position == mCrimes.size() - 1) {
            imageButtonLastCrime.setClickable(false);
            imageButtonLastCrime.setEnabled(false);
            imageButtonFirstCrime.setClickable(true);
            imageButtonFirstCrime.setEnabled(true);
        } else {
            imageButtonFirstCrime.setClickable(true);
            imageButtonFirstCrime.setEnabled(true);
            imageButtonLastCrime.setClickable(true);
            imageButtonLastCrime.setEnabled(true);
        }
    }

    public void initialization() {
        imageButtonFirstCrime = (ImageButton) findViewById(R.id.button_first_crime);
        imageButtonLastCrime = (ImageButton) findViewById(R.id.button_last_crime);

        imageButtonFirstCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });
        imageButtonLastCrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mCrimes.size() - 1);
            }
        });
    }
}
