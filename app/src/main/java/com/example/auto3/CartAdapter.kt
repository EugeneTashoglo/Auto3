package com.example.auto3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.auto3.databinding.CartItemBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class CartAdapter(private val context: Context) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartList = ArrayList<CartItem>()

    init {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Cart")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Очистите список перед добавлением элементов
                cartList.clear()

                for (cartSnapshot in dataSnapshot.children) {
                    val cartItem = cartSnapshot.getValue(CartItem::class.java)
                    cartList.add(cartItem!!)
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CartItemBinding.inflate(inflater, parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartList[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CartItem) {
            binding.cartItemModel.text = cartItem.model
            binding.cartItemPrice.text = cartItem.price
            binding.cartItemYear.text = cartItem.year
            Picasso.get().load(cartItem.img).into(binding.cartItemImage)
            binding.deletCartButton.setOnClickListener {
                deleteFromCart(cartItem)
            }
        }

        private fun deleteFromCart(cartItem: CartItem) {
            val database = FirebaseDatabase.getInstance()
            val cartRef = database.getReference("Cart")

            // Удаляем CartItem из базы данных
            cartRef.child(cartItem.productId!!).removeValue()
        }
    }

        }










