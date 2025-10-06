package br.com.unaerp.listadecompras.repository

import br.com.unaerp.listadecompras.model.Usuario

object UserRepository {
    val usuarios = mutableListOf<Usuario>()

    init {
        usuarios.add(Usuario("Arthur Melo", "arthur@email.com", "123"))
    }

    fun autenticar(email: String, senha: String): Boolean {
        return usuarios.any { it.email == email && it.senha == senha }
    }

    fun cadastrar(usuario: Usuario) {
        usuarios.add(usuario)
    }
}
