package com.handstandsam.shoppingapp.features.poke

data class PokemonInfoState(
    val pokemonId: Int,
    val pokemonAnimatedImageScale: Float,
) {
    val pokemonName: String get() = PokemonNames[pokemonId - 1]
    val pokemonImageUrl: String get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
}