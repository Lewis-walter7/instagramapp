package com.licoding.instagramapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.licoding.instagramapp.R

@Preview
@Composable
fun SplashScreen(){
    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ){
               Image(
                   painter = painterResource(id = R.drawable.logo),
                   contentDescription = null,
                   modifier = Modifier.weight(1f)
                       .fillMaxWidth()
                       .height(40.dp),
                   alignment = Alignment.Center,


               )
                Text(
                    text = "from",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 18.sp
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Image(
                    painter = painterResource(R.drawable.metalogo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    alignment = Alignment.Center
                )
            }
        }
    }
}