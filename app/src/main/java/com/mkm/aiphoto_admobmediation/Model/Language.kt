package com.mkm.aiphoto_admobmediation.Model

import android.os.Parcel
import android.os.Parcelable

class Language : Parcelable {
    var name: String? = null
    var value: String? = null

    constructor() {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(value)
    }

    private constructor(`in`: Parcel) {
        name = `in`.readString()
        value = `in`.readString()
    }

    companion object CREATOR : Parcelable.Creator<Language> {
        override fun createFromParcel(parcel: Parcel): Language {
            return Language(parcel)
        }

        override fun newArray(size: Int): Array<Language?> {
            return arrayOfNulls(size)
        }
    }
}
