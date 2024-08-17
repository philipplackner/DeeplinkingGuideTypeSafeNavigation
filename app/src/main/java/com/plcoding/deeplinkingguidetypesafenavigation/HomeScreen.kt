package com.plcoding.deeplinkingguidetypesafenavigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if(!hasPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Button(onClick = {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }) {
                Text(text = "Request permission")
            }
        } else {
            Text(text = "Home screen")
        }
    }
}