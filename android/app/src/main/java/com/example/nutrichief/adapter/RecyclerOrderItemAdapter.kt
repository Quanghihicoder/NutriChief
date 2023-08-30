package com.example.nutrichief.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutrichief.R
import com.example.nutrichief.datamodels.CartItem

class RecyclerOrderItemAdapter(var context: Context,
                               private val itemOrderedList: ArrayList<CartItem>,
                               private val activityTotalItemTV: TextView,
                               TotalItem: Int,
                               private val activityTotalPriceTV: TextView,
                               TotalPrice: Float,
                               private val activityTotalTaxTV: TextView,
                               TotalTax: Float,
                               private val activitySubTotalTV: TextView,
                               private val activityProceedToPayBtn: Button,
                               private val loadDefaultImage: Int,
                               private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecyclerOrderItemAdapter.ItemListViewHolder>() {

    private var totalPrice: Float = TotalPrice
    private var totalItems = TotalItem
    private var totalTax: Float = TotalTax

    // Interface for defining click listener callbacks
    interface OnItemClickListener {
        fun emptyOrder()
    }

    // View holder class for holding view references
    class ItemListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImageIV: ImageView = itemView.findViewById(R.id.item_image)
        val removeOrderedItem: LinearLayout = itemView.findViewById(R.id.remove_ordered_item)
        val itemNameTV: TextView = itemView.findViewById(R.id.item_name)
        val itemPriceTV: TextView = itemView.findViewById(R.id.item_price)
        val itemShortDesc: TextView = itemView.findViewById(R.id.item_shortDesc)
//        val itemCalories: TextView = itemView.findViewById(R.id.item_calories)
        val itemQuantityTV: TextView = itemView.findViewById(R.id.item_quantity)
        val itemQuantityIncreaseIV: ImageView = itemView.findViewById(R.id.item_plus)
        val itemQuantityDecreaseIV: ImageView = itemView.findViewById(R.id.item_minus)
    }

    // View holder class for holding view references
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_menu_item, parent, false)
        return ItemListViewHolder(itemView)
    }

    // Bind data to view holder
    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val currentItem = itemOrderedList[position]

        holder.removeOrderedItem.visibility = ViewGroup.VISIBLE

        if(loadDefaultImage == 1) holder.itemImageIV.setImageResource(R.drawable.default_item_image)
//        else Picasso.get().load(currentItem.imageUrl).into(holder.itemImageIV)

        holder.itemNameTV.text = currentItem.itemName
        holder.itemPriceTV.text = "$${currentItem.itemPrice}"
        holder.itemShortDesc.text = currentItem.itemDesc
//        holder.itemCalories.text = ""
        holder.itemQuantityTV.text = currentItem.quantity.toString()

        holder.removeOrderedItem.setOnClickListener {
            totalItems -= currentItem.quantity
            totalPrice -= currentItem.quantity * currentItem.itemPrice
            removeItem(position)
            updateOrderDetails()
        }

        holder.itemQuantityIncreaseIV.setOnClickListener {
            val n = currentItem.quantity
            holder.itemQuantityTV.text = (n+1).toString()
            currentItem.quantity++

            ++totalItems
            totalPrice += currentItem.itemPrice
            updateOrderDetails()
        }

        holder.itemQuantityDecreaseIV.setOnClickListener {
            val n = currentItem.quantity
            if(n!=0) {
                holder.itemQuantityTV.text = (n-1).toString()

                if(n-1 == 0) {
                    totalItems -= currentItem.quantity
                    totalPrice -= currentItem.itemPrice * currentItem.quantity
                    removeItem(position)
                } else {
                    --totalItems
                    totalPrice -= currentItem.itemPrice
                }

                currentItem.quantity--
                updateOrderDetails()
            }
        }

//        holder.itemCalories.text = ""
    }

    private fun removeItem(position: Int) {
        itemOrderedList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        if(itemCount == 0) {
            listener.emptyOrder()
        }
    }

    override fun getItemCount(): Int = itemOrderedList.size

    private fun updateOrderDetails() {
        totalTax = totalPrice * 0.12F
        activityTotalItemTV.text = "$totalItems items"
        activityTotalPriceTV.text = "\$%.2f".format(totalPrice)
        activityTotalTaxTV.text = "\$%.2f".format(totalTax)
        activitySubTotalTV.text = "\$%.2f".format(totalPrice + totalTax)
        activityProceedToPayBtn.text = "Proceed to Pay \$%.2f".format(totalPrice + totalTax)
    }

    fun getTotalItemPrice(): Float = totalPrice
    fun getTotalTax(): Float = totalTax
    fun getSubTotalPrice(): Float = totalPrice + totalTax
}