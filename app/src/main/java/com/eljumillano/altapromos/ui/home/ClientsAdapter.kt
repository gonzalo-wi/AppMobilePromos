package com.eljumillano.altapromos.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eljumillano.altapromos.R
import com.google.android.material.chip.Chip

class ClientsAdapter(private val onClick: (Client) -> Unit) :
    ListAdapter<Client, ClientsAdapter.VH>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_client, parent, false)
        return VH(v, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    class VH(itemView: View, private val onClick: (Client) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tvClientName)
        private val address: TextView = itemView.findViewById(R.id.tvClientAddress)
        private val reparto: TextView = itemView.findViewById(R.id.tvReparto)
        private val localidad: TextView = itemView.findViewById(R.id.tvLocalidad)
        private val time: TextView = itemView.findViewById(R.id.tvTime)
        private val chip: Chip = itemView.findViewById(R.id.chipStatus)

        fun bind(c: Client) {
            name.text = c.name
            address.text = c.address
            reparto.text = c.reparto
            localidad.text = c.locality
            time.text = c.time
            when (c.status) {
                ClientStatus.NEW -> {
                    chip.setText(R.string.status_new)
                    chip.setChipBackgroundColorResource(R.color.purple_500)
                }
                ClientStatus.PENDING -> {
                    chip.setText(R.string.status_pending)
                    chip.setChipBackgroundColorResource(R.color.accent_blue)
                }
                ClientStatus.COMPLETED -> {
                    chip.setText(R.string.status_completed)
                    chip.setChipBackgroundColorResource(R.color.teal_700)
                }
            }
            itemView.setOnClickListener { onClick(c) }
        }
    }

    private class Diff : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Client, newItem: Client) = oldItem == newItem
    }
}
