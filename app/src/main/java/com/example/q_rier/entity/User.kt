package com.example.q_rier.entity

class User (var nama: String, var email: String, var tglLahir: String, var noTelp : String){
    companion object{
        @JvmField
        var listOfUser = arrayOf(
            User("Wahyu", "200710844@students.uajy.ac.id", "13 Maret 2002", "082121212121"),
            User("Jeremi", "200710845@students.uajy.ac.id", "14 Maret 2002", "081212121212"),
            User("Richardo", "200710846@students.uajy.ac.id", "15 Maret 2002", "081122221111")
        )
    }
}