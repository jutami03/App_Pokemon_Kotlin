package br.com.juliana.pokemonstarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.juliana.pokemonstarter.ui.theme.PokemonStarterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PokemonStarterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PokemonStarterScreen()
                }
            }
        }
    }
}

val starters = listOf(
    Pokemon("Bulbassaur", R.drawable.bulbassaur),
    Pokemon("Charmander", R.drawable.charmander),
    Pokemon(imageRes = R.drawable.squirtle, name = "Squirtle")
)

@Composable
fun PokemonStarterScreen(modifier: Modifier = Modifier) {
    var pokemonSelected by remember { mutableStateOf(starters.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PokeHeader("Escolha seu Pokemon inicial")

        PokeCard(pokemonSelected)

        PokemonOptionList(pokemons = starters, pokemonSelected = pokemonSelected) {
            pokemonSelected = it
        }
    }
}

@Composable
fun PokeHeader(label: String) {
    Text(
        label,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

data class Pokemon(
    //val = valores imutáveis (não altera)
    //var = valores mutáveis (possível alterar)
    val name: String,
    //valor default (se nao for passado uma imagem, automaticamente essa imagem que se recebe)
    val imageRes: Int = R.drawable.pokeball_unselected
)

@Composable
fun PokeCard(
    pokemon: Pokemon
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        Image(
            painter = painterResource(pokemon.imageRes),
            //contentDescription = obrigatório por conta de acessibilidade
            contentDescription = "Pokemon selecionado é o ${pokemon.name}",
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = pokemon.name.uppercase(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
fun PokemonOption(
    pokemon: Pokemon,
    selected: Boolean,
    onSelected: (Pokemon) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onSelected(pokemon) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = if (selected) painterResource(R.drawable.pokeball_selected)
            else painterResource(R.drawable.pokeball_unselected),
            contentDescription = pokemon.name,
            modifier = Modifier.size(40.dp),
            colorFilter = if (isSystemInDarkTheme() && !selected) ColorFilter.tint(Color.White)
            else null
        )
        Text(pokemon.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }

}

@Composable
fun PokemonOptionList(
    pokemons: List<Pokemon>,
    pokemonSelected: Pokemon,
    //on selected = ta avisando sobre as mudanças
    onSelected: (Pokemon) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        //pokemon -> = transformou it em pokemon
        pokemons.forEach { pokemon ->
            PokemonOption(
                pokemon = pokemon,
                selected = pokemon == pokemonSelected,
                onSelected = onSelected
            )
        }
    }
}

@Preview
@Composable
private fun PokemonOptionPreview() {
    PokemonOption(starters.first(), true) { }
}

@Preview(showBackground = true)
@Composable
private fun PokemonStarterScreenPreview() {
    PokemonStarterTheme {
        PokemonStarterScreen()
    }
}

