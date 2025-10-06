package br.com.unaerp.listadecompras.ui.list

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.unaerp.listadecompras.adapters.ListaAdapter
import br.com.unaerp.listadecompras.databinding.ActivityListaDeComprasBinding
import br.com.unaerp.listadecompras.model.ListaDeCompras
import br.com.unaerp.listadecompras.repository.ListaRepository
import br.com.unaerp.listadecompras.ui.item.ItensActivity
import br.com.unaerp.listadecompras.ui.login.LoginActivity

class ListaDeComprasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaDeComprasBinding
    private lateinit var adapter: ListaAdapter
    private var todasListas: MutableList<ListaDeCompras> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaDeComprasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarRecyclerView()

        binding.btnNovaLista.setOnClickListener {
            startActivity(Intent(this, NovaListaActivity::class.java))
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.edtBuscarListas.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarListas(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        atualizarListas()
    }

    private fun configurarRecyclerView() {
        todasListas = ListaRepository.listas
        adapter = ListaAdapter(todasListas,
            onExcluir = { lista ->
                ListaRepository.remover(lista.id)
                atualizarListas()
            },
            onAbrir = { lista ->
                val intent = Intent(this, ItensActivity::class.java)
                intent.putExtra("listaId", lista.id)
                startActivity(intent)
            }
        )
        binding.recyclerListas.layoutManager = LinearLayoutManager(this)
        binding.recyclerListas.adapter = adapter
    }

    private fun atualizarListas() {
        ListaRepository.listas.sortBy { it.titulo }
        todasListas = ListaRepository.listas
        adapter.submitList(todasListas)
    }

    private fun filtrarListas(query: String) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            adapter.submitList(todasListas)
        } else {
            val filtradas = todasListas.filter { it.titulo.lowercase().contains(q) }
            adapter.submitList(filtradas)
        }
    }
}
