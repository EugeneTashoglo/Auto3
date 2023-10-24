package com.example.auto3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class PurchaseAdapter(private val context: Context, private val purchaseList: ArrayList<Purchase>) : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.buy_item, parent, false)
        return PurchaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val purchase = purchaseList[position]
        holder.bind(purchase)
    }

    override fun getItemCount(): Int {
        return purchaseList.size
    }

    inner class PurchaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.buyview)
        private val modelTextView: TextView = itemView.findViewById(R.id.buymodel)
        private val priceTextView: TextView = itemView.findViewById(R.id.buyprice)
        private val yearTextView: TextView = itemView.findViewById(R.id.buyyear)
        private val deleteButton: Button = itemView.findViewById(R.id.deletePurchaseButton)

        fun bind(purchase: Purchase) {
            modelTextView.text = purchase.model
            priceTextView.text = purchase.price
            yearTextView.text = purchase.year
            Picasso.get().load(purchase.img).into(imageView)

            deleteButton.setOnClickListener {
                deletePurchase(purchase)
            }
        }

        private fun deletePurchase(purchase: Purchase) {
            val database = FirebaseDatabase.getInstance()
            val purchasesRef = database.getReference("Purchases")
            purchasesRef.child(purchase.productId!!).removeValue()
        }
    }
}
