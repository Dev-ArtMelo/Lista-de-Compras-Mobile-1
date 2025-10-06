package br.com.unaerp.listadecompras.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.unaerp.listadecompras.databinding.ActivityCadastroBinding
import br.com.unaerp.listadecompras.model.Usuario
import br.com.unaerp.listadecompras.repository.UserRepository

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSalvar.setOnClickListener {
            val nome = binding.edtNome.text.toString()
            val email = binding.edtEmail.text.toString()
            val senha = binding.edtSenha.text.toString()
            val conf = binding.edtConfirmar.text.toString()

            if (nome.isBlank() || email.isBlank() || senha.isBlank() || conf.isBlank()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != conf) {
                Toast.makeText(this, "Senhas não coincidem", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            UserRepository.cadastrar(Usuario(nome, email, senha))
            Toast.makeText(this, "Usuário cadastrado!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
