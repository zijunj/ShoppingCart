package com.example.shoppingcart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingCartScreen()
        }
    }
}

@Composable
fun ShoppingCartScreen() {
    val cartItems = listOf(
        CartItem("Laptop", 1599.99, 1),
        CartItem("Smartphone", 599.99, 2),
        CartItem("Headphones", 199.99, 1)
    )

    var showSnackbar by remember { mutableStateOf(false) }
    val totalCost = cartItems.sumOf { it.price * it.quantity }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    )
    {
        // Title with Shopping Cart Icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping Cart",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text

            Text(
                text = "Shopping Cart",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Cart Items
        cartItems.forEach { item ->
            CartItemRow(item)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Section
        SummarySection(totalCost) { showSnackbar = true }
    }

    // Snackbar when Checkout is clicked
    if (showSnackbar) {
        Snackbar(
            action = {
                TextButton(onClick = { showSnackbar = false }) {
                    Text("Dismiss", color = Color.White)
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Ordered")
        }
    }
}

@Composable
fun CartItemRow(item: CartItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, fontSize = 18.sp)
        Text(text = "$${item.price}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(text = "Qty: ${item.quantity}", fontSize = 16.sp)
    }
}

@Composable
fun SummarySection(totalCost: Double, onCheckoutClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Total: $${"%.2f".format(totalCost)}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onCheckoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Checkout")
        }
    }
}

// Data Class for Cart Item
data class CartItem(val name: String, val price: Double, val quantity: Int)

@Preview(showBackground = true)
@Composable
fun PreviewShoppingCartScreen() {
    ShoppingCartScreen()
}

