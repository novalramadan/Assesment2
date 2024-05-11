package org.d3if3103.assesmant2.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3103.assesmant2.R
import org.d3if3103.assesmant2.ui.theme.Assesmant2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.profile_name)) // Replace with your name
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxWidth()) {
            // Add your profile image here (optional)
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {


                CircularImageWithText()

            }
        }
    }
}

@Composable
fun CircularImageWithText(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter // Mengatur konten ke bagian atas tengah
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Spacer untuk memberikan jarak atas
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.foto), // Ganti dengan ID sumber gambar Anda
                contentDescription = "Profile Picture",
                modifier = modifier
                    .clip(CircleShape) // Membuat gambar menjadi bulat
                    .size(200.dp),
                contentScale = ContentScale.Crop // Skala konten untuk memastikan gambar memenuhi frame dengan proporsi aslinya
            )
            Text(
                text = stringResource(id = R.string.profile_bio), // Replace with your short bio
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PersonScreenPreview() {
    Assesmant2Theme {
        PersonScreen(rememberNavController())
    }
}
