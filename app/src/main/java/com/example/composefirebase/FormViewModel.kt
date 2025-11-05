package com.example.composefirebase

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FormViewModel : ViewModel() {

    private val db = Firebase.firestore

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _grade = MutableStateFlow("")
    val grade: StateFlow<String> = _grade

    fun onNameChange(newValue: String) {
        _name.value = newValue
    }

    fun onGradeChange(newValue: String) {
        _grade.value = newValue
    }

    fun submitData(context: Context) {
        val data = hashMapOf(
            "name" to name.value,
            "grade" to grade.value,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("students")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(context, "✅ Registro guardado correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "❌ Error al guardar: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
