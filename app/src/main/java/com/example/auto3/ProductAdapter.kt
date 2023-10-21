package com.example.auto3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auto3.databinding.ProductItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ProductAdapter(private val context: Context) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var productList = ArrayList<Product>()

    init {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Product")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (productSnapshot in dataSnapshot.children) {
                    val product = productSnapshot.getValue(Product::class.java)
                    productList.add(product!!)
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    data class Product(
        var img: String? = "",
        var model: String? = "",
        var price: String? = "",
        var year: String? = ""
    )
    inner class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textViewModel.text = product.model
            binding.textViewPrice.text = product.price
            binding.textViewYear.text = product.year
            Picasso.get().load(product.img).into(binding.imageView)
        }
    }
}
