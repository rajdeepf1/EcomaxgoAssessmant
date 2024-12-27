package com.example.ecomaxgoassessmant.models

data class Location(
    val titleCode: String,
    val titleName: String,
    val latitude: Double,
    val longitude: Double
){
    override fun toString(): String {
        // This will display title and coordinates when it's in the dropdown
        return "$titleCode"
    }
}
