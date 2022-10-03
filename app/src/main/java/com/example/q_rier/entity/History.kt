package com.example.q_rier.entity

class History(var jenisBarang: String, var atasNama: String, var alamatTujuan: String, var estimasi: String) {
    companion object {
        @JvmField
        var lisOfHistories = arrayOf(
            History("Sepatu", "Johan", "Jln.Babarsari", "3 Hari"),
            History("Handphone", "Johan", "Jln.Babarsari", "1 Hari"),
            History("Sweater", "Johan", "Jln.Babarsari", "2 Hari"),
            History("Jam Tangan", "Johan", "Jln.Babarsari", "3 Jam"),
        )
    }

    //Test
}