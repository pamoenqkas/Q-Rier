package com.example.q_rier.entity

class User (var nama: String, var email: String, var tglLahir: String, var noTelp : String){
    companion object{
        @JvmField
        var listOfUser = arrayOf(
            User("Wahyu", "200710844@students.uajy.ac.id", "13 Maret 2002", "082121212121"),
            User("Sutanto", "200710845@students.uajy.ac.id", "14 Maret 2002", "081212121212"),
            User("Pamungkas", "200710846@students.uajy.ac.id", "15 Maret 2002", "081122221111"),
            User("Jeremi", "200710847@students.uajy.ac.id", "16 Maret 2002", "082321312321"),
            User("Sandy", "200710849@students.uajy.ac.id", "17 Maret 2002", "0821323231232"),
            User("Tumanggor", "200710849@students.uajy.ac.id", "18 Maret 2002", "083425227234"),
            User("Richardo", "200710850@students.uajy.ac.id", "19 Maret 2002", "089384738291"),
            User("Thung", "200710851@students.uajy.ac.id", "20 Maret 2002", "089382758192"),
            User("Agustino", "200710852@students.uajy.ac.id", "21 Maret 2002", "089384020193"),
            User("William", "200710853@students.uajy.ac.id", "22 Maret 2002", "080229439302")
        )
    }
}