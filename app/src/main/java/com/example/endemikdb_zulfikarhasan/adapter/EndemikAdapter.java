package com.example.endemikdb_zulfikarhasan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.endemikdb_zulfikarhasan.R;
import com.example.endemikdb_zulfikarhasan.data.model.Endemik;

import java.util.ArrayList;
import java.util.List;

public class EndemikAdapter extends RecyclerView.Adapter<EndemikAdapter.ViewHolder> {
    private List<Endemik> endemikList = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Endemik endemik);
    }

    public EndemikAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setEndemikList(List<Endemik> endemikList) {
        this.endemikList = endemikList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_endemik, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Endemik endemik = endemikList.get(position);
        holder.tvNama.setText(endemik.getNama());
        Glide.with(holder.itemView.getContext())
                .load(endemik.getFoto())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.ivFoto);
        
        holder.itemView.setOnClickListener(v -> listener.onItemClick(endemik));
    }

    @Override
    public int getItemCount() {
        return endemikList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFoto;
        TextView tvNama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            tvNama = itemView.findViewById(R.id.tv_nama);
        }
    }
}
