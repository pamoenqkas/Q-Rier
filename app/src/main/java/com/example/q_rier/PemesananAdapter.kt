package com.example.q_rier

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class PemesananAdapter (private var pemesananList: List<Pemesanan>, context: Context) :
    RecyclerView.Adapter<PemesananAdapter.ViewHolder>(), Filterable {

//    private var filteredMahasiswaList: MutableList<Mahasiswa>
    private var filteredPemesananList: MutableList<Pemesanan>
    private val context: Context

    init{
        filteredPemesananList = ArrayList(pemesananList)
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_pemesanan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int{
        return filteredPemesananList.size
    }

    fun setPemesananList(pemesananList: Array<Pemesanan>){
        this.pemesananList = pemesananList.toList()
        filteredPemesananList = pemesananList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pemesanan = filteredPemesananList[position]

        holder.tvNamaPengirim.text = pemesanan.namaPengirim
        holder.tvAlamatPengirim.text = pemesanan.alamatPengirim
        holder.tvNoTelpPengirim.text = pemesanan.noTelpPengirim
        holder.tvNamaPenerima.text = pemesanan.namaPenerima
        holder.tvAlamatPenerima.text = pemesanan.alamatPenerima
        holder.tvNoTelpPenerima.text = pemesanan.noTelpPenerima
        holder.tvJenisBarang.text = pemesanan.jenisBarang
        holder.tvBerat.text = pemesanan.berat

        holder.btnDelete.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin ingin menghapus data pemesanan ini ?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Hapus"){_, _ ->
                    if(context is ShowPemesanan) pemesanan.id?.let { it1 ->
                        context.deletePemesanan(
                            it1
                        )
                    }
                }
                .show()
        }
        holder.cvPemesanan.setOnClickListener{
            val i = Intent(context, AddEditPemesanan::class.java)
            i.putExtra("id", pemesanan.id)
            if(context is ShowPemesanan)
                context.startActivityForResult(i, ShowPemesanan.LAUNCH_ADD_ACTIVITY)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Pemesanan> = java.util.ArrayList()
                if (charSequenceString.isEmpty()){
                    filtered.addAll(pemesananList)
                }else{
                    for(pemesanan in pemesananList){
                        if(pemesanan.namaPengirim.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(pemesanan)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredPemesananList.clear()
                filteredPemesananList.addAll((filterResults.values as List<Pemesanan>))
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvNamaPengirim: TextView
        var tvAlamatPengirim: TextView
        var tvNoTelpPengirim: TextView
        var tvNamaPenerima: TextView
        var tvAlamatPenerima: TextView
        var tvNoTelpPenerima: TextView
        var tvJenisBarang: TextView
        var tvBerat: TextView
        var cvPemesanan: CardView
        var btnDelete: ImageButton

        init{
            tvNamaPengirim = itemView.findViewById(R.id.tv_namaPengirim)
            tvAlamatPengirim = itemView.findViewById(R.id.tv_alamatPengirim)
            tvNoTelpPengirim = itemView.findViewById(R.id.tv_noTelpPengirim)
            tvNamaPenerima = itemView.findViewById(R.id.tv_namaPenerima)
            tvAlamatPenerima = itemView.findViewById(R.id.et_alamatPenerima)
            tvNoTelpPenerima = itemView.findViewById(R.id.et_noTelpPenerima)
            tvJenisBarang = itemView.findViewById(R.id.tv_jenisBarang)
            tvBerat = itemView.findViewById(R.id.tv_berat)
            btnDelete = itemView.findViewById(R.id.btn_delete)
            cvPemesanan = itemView.findViewById(R.id.cv_pemesanan)
        }
    }
}