package com.example.howtofail

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.android.parcel.Parcelize

data class User(val name: String="no name",val hobby: String="no hobby")

@Parcelize
data class Story(val title: String="no title", val content: String = "no content",val author_name: String = "no name",
                  val imageUrl: String="no image",val date:String = "no date"): Parcelable