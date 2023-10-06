package br.com.fgomes.cgd.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fgomes.cgd.activities.ui.theme.CGDTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CGDTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true,
    showSystemUi = true
)

//@Preview(
//    name = "Dark Mode",
//    showBackground = true,
//    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
//    heightDp = 200,
//    widthDp = 300
//)


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun Greeting( modifier: Modifier = Modifier) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Adicione os componentes da tela de login, como campos de texto, botões, etc.
//        TextField(
//            value = "teste",
//            onValueChange = { },
//            label = { Text("Usuário") },
//        )
//    }
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val (email, setEmail) = remember { mutableStateOf(TextFieldValue("")) }
    val (password, setPassword) = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Login")
        OutlinedTextField(
            value = email,
            onValueChange = setEmail,
            label = { Text(text = "E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = password,
            onValueChange = setPassword,
            label = { Text(text = "Senha") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = {
                // This is where you would call your login function.
            },
            modifier = Modifier.padding(bottom = 8.dp),
        ) {
            Text(text = "Login")
        }
    }
}
