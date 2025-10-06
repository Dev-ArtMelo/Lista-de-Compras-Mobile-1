package br.com.unaerp.listadecompras.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.unaerp.listadecompras.databinding.ItemItemBinding
import br.com.unaerp.listadecompras.model.Item

class ItemAdapter(
    private var itens: MutableList<Item>,
    private val onExcluir: (Item) -> Unit,
    private val onEditar: (Item) -> Unit,
    private val onMarcar: (Item) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = itens.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itens[position]

        holder.binding.txtNomeItem.text = item.nome
        holder.binding.txtDetalhesItem.text = "${item.quantidade} ${item.unidade} - ${item.categoria}"

        holder.binding.checkComprado.setOnCheckedChangeListener(null)
        holder.binding.checkComprado.isChecked = item.comprado

        holder.binding.txtNomeItem.paintFlags =
            if (item.comprado) holder.binding.txtNomeItem.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else holder.binding.txtNomeItem.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        holder.binding.checkComprado.setOnCheckedChangeListener { _, isChecked ->
            item.comprado = isChecked
            onMarcar(item)
        }

        holder.binding.btnEditar.setOnClickListener { onEditar(item) }
        holder.binding.btnExcluir.setOnClickListener { onExcluir(item) }
    }

    fun submitList(novosItens: List<Item>) {
        itens = novosItens.toMutableList()
        notifyDataSetChanged()
    }
}
