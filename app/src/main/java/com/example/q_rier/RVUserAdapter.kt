package com.example.q_rier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.q_rier.entity.User

class RVUserAdapter(private val data: Array<User>) : RecyclerView.Adapter<RVUserAdapter.viewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVUserAdapter.viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_user, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNama.text = currentItem.nama
        holder.tvEmail.text= currentItem.email
        holder.tvNoTelp.text = currentItem.noTelp
        holder.tvTglLahir.text = currentItem.tglLahir
    }

    override fun getItemCount(): Int{
        return data.size
    }

    class viewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tvNama : TextView = itemView.findViewById(R.id.tv_nama)
        val tvEmail : TextView = itemView.findViewById(R.id.tv_email)
        val tvNoTelp : TextView = itemView.findViewById(R.id.tv_noTelp)
        val tvTglLahir : TextView = itemView.findViewById(R.id.tv_tglLahir)
    }
}