package org.d3if3103.assesmant2.ui.screen

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3103.assesmant2.R
import org.d3if3103.assesmant2.database.MotorDb
import org.d3if3103.assesmant2.ui.theme.Assesmant2Theme
import org.d3if3103.assesmant2.util.ViewModelFactory

const val KEY_ID_motor = "idmotor"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController,id: Long?= null) {
    val context = LocalContext.current
    val db = MotorDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var nama by remember { mutableStateOf("") }
    var stok by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }


    val radioOptions = listOf(
        stringResource(id = R.string.yamaha),
        stringResource(id = R.string.honda),
        stringResource(id = R.string.suzuki),
        stringResource(id = R.string.kawasaki),
        stringResource(id = R.string.vespa)
    )

    var selectedMerek by rememberSaveable { mutableStateOf(radioOptions[0]) }

    LaunchedEffect(true) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getmotor(id) ?: return@LaunchedEffect
        nama = data.nama
        stok = data.stok
        selectedMerek = data.merek
    }
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    if (id == null)
                        Text(text = stringResource(id = R.string.tambah_motor))
                    else
                        Text(text = stringResource(id = R.string.edit_barang))

                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (nama =="" || stok == "" ){
                            Toast.makeText(context,R.string.invalid, Toast.LENGTH_LONG).show()
                            return@IconButton
                        }
                        if (id == null){
                            viewModel.insert(nama,stok, selectedMerek)
                        } else{
                            viewModel.update(id,nama,stok, selectedMerek)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(id = R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null){
                        DeleteAction { showDialog = true }
                        DisplayAlertDialog(
                            openDialog = showDialog,
                            onDismissRequest = { showDialog = false }) {
                            showDialog = false
                            viewModel.delete(id)
                            navController.popBackStack()
                        }
                    }

                }
            )
        }
    ) { padding ->
        Formmotor(
            title = nama,
            onTitleChange = {nama = it} ,
            desc = stok,
            onDescChange = {stok = it},
            pilihanKelas = selectedMerek,
            kelasBerubah = {selectedMerek = it},
            radioOpsi = radioOptions,
            modifier = Modifier.padding(padding)
        )
//        ScreenContent(Modifier.padding(padding))
    }
}

@Composable
fun DeleteAction(delete:()->Unit ){
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = {expanded = true}) {
        Icon(
            imageVector = Icons.Filled.MoreVert ,
            contentDescription = stringResource(R.string.opsi_lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded ,
            onDismissRequest = { expanded = false}
        ) {
            DropdownMenuItem(text = { Text(text = stringResource(R.string.hapus))},
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun Formmotor(

    title:String,onTitleChange:(String)-> Unit,
    desc:String,onDescChange:(String)->Unit,
    pilihanKelas: String,kelasBerubah:(String)->Unit,
    radioOpsi:List<String>,
    modifier: Modifier
){



    Column(
        modifier= modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        //nama motor
        OutlinedTextField(
            value = title,
            onValueChange = {onTitleChange(it)},
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.nama))},
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            modifier=Modifier.fillMaxWidth()
        )
        //nim motor
        OutlinedTextField(
            value = desc,
            onValueChange = {onDescChange(it)},
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.stok))},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier=Modifier.fillMaxWidth()
        )
        //RadioOption kelas
        Column(
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
        ){
            radioOpsi.forEach { text -> KelasOption(
                label = text, isSelected = pilihanKelas == text, modifier = Modifier
                    .selectable(
                        selected = pilihanKelas == text,
                        onClick = { kelasBerubah(text) },
                        role = Role.RadioButton
                    )

                    .padding(16.dp)
                    .fillMaxWidth()
            )
            }
        }
    }
}


@Composable
fun KelasOption (label:String, isSelected: Boolean, modifier: Modifier ){
    Row (
        modifier = modifier,

        ){
        RadioButton(selected = isSelected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailScreenPreview(){
   Assesmant2Theme {

   }
        DetailScreen(rememberNavController())
    }
