package br.com.unaerp.listadecompras.model

data class Item(
    val id: Int,
    var nome: String,
    var quantidade: Double,
    var unidade: String,
    var categoria: String,
    var comprado: Boolean = false
)
