package br.com.unaerp.listadecompras.repository

import br.com.unaerp.listadecompras.model.ListaDeCompras

object ListaRepository {
    val listas = mutableListOf<ListaDeCompras>()
    private var contadorId = 1

    fun adicionar(titulo: String, imagem: String?): ListaDeCompras {
        val lista = ListaDeCompras(contadorId++, titulo, imagem)
        listas.add(lista)
        return lista
    }

    fun remover(id: Int) {
        listas.removeIf { it.id == id }
    }

    fun buscar(id: Int): ListaDeCompras? {
        return listas.find { it.id == id }
    }
}
