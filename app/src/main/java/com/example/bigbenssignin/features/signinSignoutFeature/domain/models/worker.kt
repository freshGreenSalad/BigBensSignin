package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class People(
    @PrimaryKey(autoGenerate = true) val personId: Int = 0,
    val contact: Contact?,
    val contact_id: Int?,
    val employee_id: String?,
    val first_name: String?,
    val id: Int?,
    val is_employee: Boolean?,
    val last_name: String?,
    val name: String?,
    val origin_id: Int?,
    val user_id: Int?,
    val user_uuid: String?,
    val work_classification_id: Int?
)
@Serializable
data class Contact(
    val is_active: Boolean? = true
)

class ContactConverter {
    @TypeConverter
    fun fromContact(boolean: Boolean?): Contact? {
        return if (boolean == null) null else Contact(boolean)
    }

    @TypeConverter
    fun toBoolean(contact: Contact?): Boolean? {
        return contact?.is_active
    }
}

data class PersonWithoutId(
    val contact: Contact?,
    val contact_id: Int?,
    val employee_id: String?,
    val first_name: String?,
    val id: Int?,
    val is_employee: Boolean?,
    val last_name: String?,
    val name: String?,
    val origin_id: Int?,
    val user_id: Int?,
    val user_uuid: String?,
    val work_classification_id: Int?
)
