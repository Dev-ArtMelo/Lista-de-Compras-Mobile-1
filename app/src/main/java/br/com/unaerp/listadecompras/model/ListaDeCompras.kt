package br.com.unaerp.listadecompras.model

data class ListaDeCompras(
    val id: Int,
    var titulo: String,
    var imagem: String? = null,
    val itens: MutableList<Item> = mutableListOf()
)
