package br.com.unaerp.listadecompras.ui.item

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.unaerp.listadecompras.databinding.ActivityNovoItemBinding
import br.com.unaerp.listadecompras.model.Item
import br.com.unaerp.listadecompras.repository.ListaRepository

class NovoItemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovoItemBinding
    private var listaId = 0
    private var itemId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovoItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listaId = intent.getIntExtra("listaId", 0)
        itemId = intent.getIntExtra("itemId", -1).takeIf { it != -1 }

        val lista = ListaRepository.buscar(listaId) ?: return
        val itemExistente = lista.itens.find { it.id == itemId }

        if (itemExistente != null) {
            binding.edtNome.setText(itemExistente.nome)
            binding.edtQuantidade.setText(itemExistente.quantidade.toString())
            binding.edtUnidade.setText(itemExistente.unidade)
            binding.edtCategoria.setText(itemExistente.categoria)
        }

        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val quantidade = binding.edtQuantidade.text.toString().toDoubleOrNull()
            val unidade = binding.edtUnidade.text.toString()
            val categoria = binding.edtCategoria.text.toString()

            if (nome.isBlank() || quantidade == null || unidade.isBlank() || categoria.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (itemExistente != null) {
                itemExistente.nome = nome
                itemExistente.quantidade = quantidade
                itemExistente.unidade = unidade
                itemExistente.categoria = categoria
                Toast.makeText(this, "Item atualizado!", Toast.LENGTH_SHORT).show()
            } else {
                val novoItem = Item(
                    id = lista.itens.size + 1,
                    nome = nome,
                    quantidade = quantidade,
                    unidade = unidade,
                    categoria = categoria
                )
                lista.itens.add(novoItem)
                Toast.makeText(this, "Item adicionado!", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}
