package com.test.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment extends Fragment {
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private static final int VIEW_TYPE_POLICE = 1;
    private static final int VIEW_TYPE_NORMAL = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        mAdapter = new CrimeAdapter(crimes);
        mCrimeRecyclerView.setAdapter(mAdapter);
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Button mCallPoliceButton;
        private Crime mCrime;
        private ImageView mSolvedImageView;

        public CrimeHolder(View itemView, int typeXml) {
            super(itemView);
            if (typeXml == VIEW_TYPE_NORMAL) {
                itemView.setOnClickListener(this);
                mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
                mDateTextView = (TextView) itemView.findViewById(R.id.crime_data);
                mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
            } else if (typeXml == VIEW_TYPE_POLICE) {
                itemView.setOnClickListener(this);
                mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
                mDateTextView = (TextView) itemView.findViewById(R.id.crime_data);
                mCallPoliceButton = (Button) itemView.findViewById(R.id.call_police_button);
                mCallPoliceButton.setOnClickListener(this);
            }
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.call_police_button) {
                Toast.makeText(getActivity(), mCrime.getTitle() + " call police!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = CrimeActivity.newIntent(getContext(), mCrime.getId());
                startActivity(intent);
            }
        }

    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (viewType == VIEW_TYPE_POLICE) {
                View viewPolice = layoutInflater.inflate(R.layout.list_item_crime_police, parent, false);
                return new CrimeHolder(viewPolice, VIEW_TYPE_POLICE);
            } else {
                View viewNormal = layoutInflater.inflate(R.layout.list_item_crime, parent, false);
                return new CrimeHolder(viewNormal, VIEW_TYPE_NORMAL);
            }
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemViewType(int position) {
            if (mCrimes.get(position).ismRequiresPolice()) {
                return VIEW_TYPE_POLICE;
            } else {
                return VIEW_TYPE_NORMAL;
            }
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}
