package com.example.paystackimplementation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.paystackimplementation.ui.theme.PayStackImplementationTheme
import com.paystack.android.core.Paystack
import com.paystack.android.ui.paymentsheet.PaymentSheet
import com.paystack.android.ui.paymentsheet.PaymentSheetResult

class MainActivity : ComponentActivity() {
    private lateinit var paymentSheet: PaymentSheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Paystack.builder()
            .setPublicKey("pk_test_43f054369f4e6d6948ddf43097ab25efbfbb6d80")
            .build()

        paymentSheet = PaymentSheet(this, ::paymentComplete)

        setContent {
            PayStackImplementationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting()
                }
            }
        }
    }

    fun makePayment(accessCode: String) {
        paymentSheet.launch(accessCode)
    }
}

private fun paymentComplete(paymentSheetResult: PaymentSheetResult) {
    when (paymentSheetResult) {
        is PaymentSheetResult.Cancelled -> {
            Log.d("TAG", "paymentComplete: casncelled")
        }

        is PaymentSheetResult.Completed -> {
            Log.d("TAG", "paymentComplete: completed")
        }

        is PaymentSheetResult.Failed -> {
            Log.d("TAG", "paymentComplete: failed")
        }
    }
}

@Composable
fun Greeting() {
    val context = LocalContext.current

    val activity = context as? MainActivity
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier.height(56.dp),
            onClick = {
                activity?.makePayment("accessCode")
            }) {
            Text(text = "Launch paymentSheet")
        }
    }
}

