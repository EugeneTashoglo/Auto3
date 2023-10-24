package com.example.auto3

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
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
                cartList.clear()
                for (cartSnapshot in dataSnapshot.children) {
                    val cartItem = cartSnapshot.getValue(CartItem::class.java)
                    cartList.add(cartItem!!)
                }
                notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
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
            binding.buyproduct.setOnClickListener {
                showPurchaseDialog(cartItem)
            }
        }
        private fun showPurchaseDialog(cartItem: CartItem) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Подтверждение покупки")

            val view = LayoutInflater.from(context).inflate(R.layout.dialog_purchase, null)

            val textViewProduct = view.findViewById<TextView>(R.id.textViewProduct)
            val editTextAddress = view.findViewById<EditText>(R.id.editTextAddress)
            val editTextPhone = view.findViewById<EditText>(R.id.editTextPhone)
            val editTextCardNumber = view.findViewById<EditText>(R.id.editTextCardNumber)

            textViewProduct.text = cartItem.model
            builder.setView(view)
            builder.setPositiveButton("Принять") { dialog, _ ->
                val purchase = Purchase(
                    productId = cartItem.productId,
                    img = cartItem.img,
                    model = cartItem.model,
                    price = cartItem.price,
                    year = cartItem.year,
                    address = editTextAddress.text.toString(),
                    phone = editTextPhone.text.toString(),
                    cardNumber = editTextCardNumber.text.toString()
                )

                savePurchase(purchase)
                deleteFromCart(cartItem)
                dialog.dismiss()
            }
            builder.setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        }
        private fun savePurchase(purchase: Purchase) {
            val database = FirebaseDatabase.getInstance()
            val purchasesRef = database.getReference("Purchases")

            purchasesRef.child(purchase.productId!!).setValue(purchase)
        }


    }
        private fun deleteFromCart(cartItem: CartItem) {
            val database = FirebaseDatabase.getInstance()
            val cartRef = database.getReference("Cart")
            cartRef.child(cartItem.productId!!).removeValue()
        }
    }












