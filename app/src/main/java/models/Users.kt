package models

class Users  (var username: String, var password: String, var email: String,
              var tanggalLahir: String, var phoneNumber: String) {
    var id: Long? = null
}