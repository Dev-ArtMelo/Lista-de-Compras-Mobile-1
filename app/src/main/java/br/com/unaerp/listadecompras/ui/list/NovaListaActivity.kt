package br.com.unaerp.listadecompras.ui.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.unaerp.listadecompras.databinding.ActivityNovaListaBinding
import br.com.unaerp.listadecompras.repository.ListaRepository

class NovaListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovaListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalvar.setOnClickListener {
            val titulo = binding.edtTitulo.text.toString()

            if (titulo.isBlank()) {
                Toast.makeText(this, "Informe o título da lista", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ListaRepository.adicionar(titulo, null)
            Toast.makeText(this, "Lista criada!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
