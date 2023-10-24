package com.example.auto3.ui.notifications


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.auto3.Purchase
import com.example.auto3.PurchaseAdapter
import com.example.auto3.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NotificationsFragment : Fragment() {
    private var purchaseList = ArrayList<Purchase>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.buyproductRecyclerView)

        // Используйте LinearLayoutManager для вашего RecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        // Создайте и установите адаптер с purchaseList
        val adapter = PurchaseAdapter(requireContext(), purchaseList)
        recyclerView.adapter = adapter

        // Получите ссылку на "Purchases" в Firebase
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Purchases")

        // Добавьте слушателя к "Purchases"
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                purchaseList.clear()
                for (purchaseSnapshot in dataSnapshot.children) {
                    val purchase = purchaseSnapshot.getValue(Purchase::class.java)
                    purchaseList.add(purchase!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Firebase", "Failed to read value.", error.toException())
            }
        })

        return root
    }

}
