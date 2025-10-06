package br.com.unaerp.listadecompras.ui.item

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.unaerp.listadecompras.adapters.ItemAdapter
import br.com.unaerp.listadecompras.databinding.ActivityItensBinding
import br.com.unaerp.listadecompras.model.Item
import br.com.unaerp.listadecompras.repository.ListaRepository

class ItensActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItensBinding
    private lateinit var adapter: ItemAdapter
    private var listaId: Int = 0
    private var todosItens: MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItensBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaId = intent.getIntExtra("listaId", 0)
        val lista = ListaRepository.buscar(listaId) ?: return

        binding.txtTituloLista.text = lista.titulo

        configurarRecyclerView(lista)

        binding.btnNovoItem.setOnClickListener {
            val intent = Intent(this, NovoItemActivity::class.java)
            intent.putExtra("listaId", listaId)
            startActivity(intent)
        }

        binding.edtBuscarItens.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarItens(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        atualizarItens()
    }

    private fun configurarRecyclerView(lista: br.com.unaerp.listadecompras.model.ListaDeCompras) {
        todosItens = lista.itens
        adapter = ItemAdapter(
            todosItens,
            onExcluir = { item ->
                lista.itens.remove(item)
                atualizarItens()
            },
            onEditar = { item ->
                val intent = Intent(this, NovoItemActivity::class.java)
                intent.putExtra("listaId", listaId)
                intent.putExtra("itemId", item.id)
                startActivity(intent)
            },
            onMarcar = {
                atualizarItens()
            }
        )
        binding.recyclerItens.layoutManager = LinearLayoutManager(this)
        binding.recyclerItens.adapter = adapter
    }

    private fun atualizarItens() {
        val lista = ListaRepository.buscar(listaId) ?: return
        lista.itens.sortBy { it.nome }
        todosItens = lista.itens
        adapter.submitList(todosItens)
    }

    private fun filtrarItens(query: String) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            adapter.submitList(todosItens)
        } else {
            val filtrados = todosItens.filter { it.nome.lowercase().contains(q) }
                .sortedBy { it.nome }
            adapter.submitList(filtrados)
        }
    }
}
