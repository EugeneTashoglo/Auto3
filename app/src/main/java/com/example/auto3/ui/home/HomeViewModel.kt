package com.example.auto3.ui.home


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.auto3.ProductAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

//
//class HomeViewModel : ViewModel() {
//    private val _products = MutableLiveData<List<ProductAdapter.Product>>()
//    val products: LiveData<List<ProductAdapter.Product>> get() = _products
//
//    init {
//        loadProducts()
//    }
//
//    private fun loadProducts() {
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("Product")
//
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val productList = ArrayList<ProductAdapter.Product>()
//                for (productSnapshot in dataSnapshot.children) {
//                    val product = productSnapshot.getValue(ProductAdapter.Product::class.java)
//                    productList.add(product!!)
//                }
//                _products.value = productList
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("Firebase", "Failed to read value.", error.toException())
//            }
//        })
//    }
//}
