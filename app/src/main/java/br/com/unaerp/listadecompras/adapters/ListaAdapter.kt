package br.com.unaerp.listadecompras.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.unaerp.listadecompras.databinding.ItemListaBinding
import br.com.unaerp.listadecompras.model.ListaDeCompras

class ListaAdapter(
    private var listas: MutableList<ListaDeCompras>,
    private val onExcluir: (ListaDeCompras) -> Unit,
    private val onAbrir: (ListaDeCompras) -> Unit
) : RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {

    inner class ListaViewHolder(val binding: ItemListaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val binding = ItemListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListaViewHolder(binding)
    }

    override fun getItemCount(): Int = listas.size

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val lista = listas[position]
        holder.binding.txtTituloLista.text = lista.titulo

        if (lista.imagem == null) {
            holder.binding.imgLista.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.binding.root.setOnClickListener { onAbrir(lista) }
        holder.binding.btnExcluir.setOnClickListener { onExcluir(lista) }
    }

    fun submitList(novasListas: List<ListaDeCompras>) {
        listas = novasListas.toMutableList()
        notifyDataSetChanged()
    }
}
