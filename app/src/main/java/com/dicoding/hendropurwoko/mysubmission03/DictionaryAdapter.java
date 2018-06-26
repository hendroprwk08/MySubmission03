package com.dicoding.hendropurwoko.mysubmission03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    public Context context;
    ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();

    public DictionaryAdapter(Context c) {
        this.context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("hasil word: ", dictionaryModels.get(position).getWord());
        Log.d("hasil description:", dictionaryModels.get(position).getDescription());

        holder.tvWord.setText(dictionaryModels.get(position).getWord());
        holder.tvDescription.setText(dictionaryModels.get(position).getDescription());
    }

    public void addItem(ArrayList<DictionaryModel> dictionaryModels) {
        this.dictionaryModels = dictionaryModels;
        notifyDataSetChanged();
    }

    public ArrayList<DictionaryModel> getListData(){
        return dictionaryModels;
    }

    @Override
    public int getItemCount() {
        return dictionaryModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvWord, tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWord = (TextView)itemView.findViewById(R.id.tv_word);
            tvDescription = (TextView)itemView.findViewById(R.id.tv_description);
        }
    }
}