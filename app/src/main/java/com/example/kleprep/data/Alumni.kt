package com.example.kleprep.data

import android.provider.ContactsContract

data class AlumniTeam(
    var alumniTeam: ArrayList<Alumni>
)

data class Alumni(
    var batch: String,
    var name: String,
    var company: String,
    var email: String,
    var mobile: String
)
