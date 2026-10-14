package in.tholuwa.hat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Gamusa = Color(0xFFB92832)
private val Muga = Color(0xFFD5A640)
private val Ivory = Color(0xFFFFFAEF)
private val Leaf = Color(0xFF2F6B45)
private val Navy = Color(0xFF172A3A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TholuwaApp() }
    }
}

data class Product(val name: String, val assamese: String, val price: String, val unit: String, val seller: String, val icon: String, val category: String)

@Composable
fun TholuwaApp() {
    var isAssamese by remember { mutableStateOf(true) }
    var tab by remember { mutableIntStateOf(0) }
    var query by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("All Assam") }
    var selectedDistrict by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf("") }
    val products = listOf(
        Product("Handwoven Gamusa", "হাতেবোৱা গামোচা", "₹250", "piece", "Bihu Handloom", "🧣", "Handloom"),
        Product("Fresh Assam Lemons", "টেঙা নেমু", "₹60", "dozen", "Village Fresh", "🍋", "Fruits"),
        Product("Joha Rice", "জহা চাউল", "₹95", "kg", "Brahmaputra Farms", "🌾", "Rice & Grains"),
        Product("Traditional Pitha", "অসমীয়া পিঠা", "₹180", "box", "Aai's Kitchen", "🥮", "Traditional Food"),
        Product("Bamboo Craft Basket", "বাঁহৰ পাচি", "₹320", "piece", "Karbi Craft", "🧺", "Handicrafts"),
        Product("Fresh Garden Vegetables", "সতেজ শাক-পাচলি", "₹40", "bundle", "Local Farmer", "🥬", "Vegetables")
    )
    val categories = listOf("All", "Handloom", "Vegetables", "Fruits", "Rice & Grains", "Traditional Food", "Handicrafts", "Groceries", "Services")
    var category by remember { mutableStateOf("All") }
    val filtered = products.filter { (it.name.contains(query, true) || it.assamese.contains(query) || query.isBlank()) && (category == "All" || it.category == category) }
    MaterialTheme(colorScheme = lightColorScheme(primary = Gamusa, secondary = Muga, background = Ivory, surface = Color.White, onPrimary = Color.White, onBackground = Navy)) {
        Scaffold(containerColor = Ivory, topBar = {
            Column(Modifier.background(Gamusa).padding(horizontal = 18.dp, vertical = 12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(46.dp).background(Muga, CircleShape), contentAlignment = Alignment.Center) { Text("🎋", fontSize = 25.sp) }
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(if (isAssamese) "থলুৱা হাট" else "Tholuwa Hat", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.Bold)
                        Text(if (isAssamese) "আপোনাৰ নিজৰ ডিজিটেল বজাৰ" else "Your Local Marketplace", color = Color.White.copy(alpha=.9f), fontSize = 12.sp)
                    }
                    TextButton(onClick = { isAssamese = !isAssamese }) { Text(if (isAssamese) "EN" else "অ আ", color = Color.White, fontWeight = FontWeight.Bold) }
                }
                Spacer(Modifier.height(14.dp))
                OutlinedTextField(value = query, onValueChange = { query = it }, modifier = Modifier.fillMaxWidth(), placeholder = { Text(if (isAssamese) "সামগ্ৰী বা দোকান বিচাৰক..." else "Search products or shops...") }, leadingIcon = { Icon(Icons.Default.Search, null) }, singleLine = true, shape = RoundedCornerShape(14.dp), colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = Color.White, unfocusedContainerColor = Color.White, focusedBorderColor = Muga, unfocusedBorderColor = Color.White))
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = Color.White, modifier = Modifier.size(18.dp))
                    Text(if (isAssamese) "অঞ্চল: $district" else "Area: $district", color = Color.White, modifier = Modifier.weight(1f), fontSize = 13.sp)
                    TextButton(onClick = { selectedDistrict = true }) { Text(if (isAssamese) "সলনি কৰক ▾" else "Change ▾", color = Muga) }
                }
            }
        }, bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val labels = if (isAssamese) listOf("বজাৰ", "ওচৰত", "সমাজ", "মোৰ দোকান", "মোৰ প্ৰফাইল") else listOf("Home", "Nearby", "Community", "My Shop", "Profile")
                val icons = listOf(Icons.Default.Home, Icons.Default.LocationOn, Icons.Default.Diversity3, Icons.Default.Store, Icons.Default.Person)
                labels.forEachIndexed { i, label -> NavigationBarItem(selected = tab == i, onClick = { tab = i }, icon = { Icon(icons[i], contentDescription = label) }, label = { Text(label, fontSize = 9.sp, maxLines = 1, overflow = TextOverflow.Ellipsis) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = Gamusa, selectedTextColor = Gamusa, indicatorColor = Color(0xFFF9E8E3))) }
            }
        }, snackbarHost = { SnackbarHost(hostState = remember { SnackbarHostState() }) }) { pad ->
            LazyColumn(Modifier.fillMaxSize().padding(pad).padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(top = 14.dp, bottom = 20.dp)) {
                if (tab == 0 || tab == 1) {
                    item {
                        Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Navy), modifier = Modifier.fillMaxWidth()) {
                            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Column(Modifier.weight(1f)) {
                                    Text(if (isAssamese) "অসমৰ নিজৰ বজাৰ" else "Made for Assam. Built for local.", color = Muga, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                                    Text(if (isAssamese) "কৃষক, শিল্পী আৰু স্থানীয় দোকানীৰ পৰা পোনপটীয়াকৈ কিনক।" else "Shop directly from farmers, artisans and local sellers.", color = Color.White, fontSize = 13.sp, lineHeight = 19.sp)
                                    Spacer(Modifier.height(8.dp))
                                    Button(onClick = { tab = 3 }, colors = ButtonDefaults.buttonColors(containerColor = Gamusa)) { Text(if (isAssamese) "দোকান খোলক" else "Open your shop") }
                                }
                                Text("🛍️", fontSize = 48.sp)
                            }
                        }
                    }
                    item { Text(if (isAssamese) "বিভাগ বাছনি কৰক" else "Explore categories", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Navy) }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            categories.chunked(3).forEach { row -> Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { row.forEach { c -> FilterChip(selected = category == c, onClick = { category = c }, label = { Text(if (c == "All") { if (isAssamese) "সকলো" else "All" } else c, fontSize = 11.sp) }, leadingIcon = if (category == c) ({ Icon(Icons.Default.Check, null, Modifier.size(14.dp)) }) else null) } } }
                        }
                    }
                    item { Row(verticalAlignment = Alignment.CenterVertically) { Text(if (tab == 1) { if (isAssamese) "ওচৰৰ সামগ্ৰী" else "Nearby products" } else { if (isAssamese) "জনপ্ৰিয় স্থানীয় সামগ্ৰী" else "Popular local products" }, modifier = Modifier.weight(1f), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Navy); Text(if (isAssamese) "${filtered.size}টা" else "${filtered.size} items", color = Leaf, fontSize = 12.sp) } }
                    items(filtered) { product ->
                        Card(Modifier.fillMaxWidth().clickable { notice = "${product.name} · ${product.price}" }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(Modifier.size(76.dp).background(Color(0xFFF7F0E1), RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) { Text(product.icon, fontSize = 37.sp) }
                                Spacer(Modifier.width(12.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(if (isAssamese) product.assamese else product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Navy)
                                    Text(product.seller, color = Leaf, fontSize = 12.sp)
                                    Text("${product.price} / ${product.unit}", color = Gamusa, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text(if (isAssamese) "নমুনা সামগ্ৰী · বাস্তৱ দোকান সংযোগ বাকী" else "Sample listing · live seller connection pending", color = Color.Gray, fontSize = 10.sp)
                                }
                                IconButton(onClick = { notice = if (isAssamese) "সামগ্ৰী নিৰ্বাচিত" else "Product selected" }) { Icon(Icons.Default.FavoriteBorder, contentDescription = "Wishlist", tint = Gamusa) }
                            }
                        }
                    }
                } else {
                    item {
                        Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(20.dp)) {
                            Column(Modifier.padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(listOf("🏡", "📍", "🎭", "🏪", "👤")[tab], fontSize = 50.sp)
                                Spacer(Modifier.height(8.dp))
                                Text(listOf("", if (isAssamese) "ওচৰৰ বজাৰ" else "Nearby marketplace", if (isAssamese) "থলুৱা সমাজ" else "Tholuwa Community", if (isAssamese) "আপোনাৰ ডিজিটেল দোকান" else "Your digital shop", if (isAssamese) "আপোনাৰ প্ৰফাইল" else "Your profile")[tab], fontSize = 21.sp, fontWeight = FontWeight.Bold, color = Navy)
                                Text(when(tab) { 1 -> if (isAssamese) "৫ কিমি বা ১০ কিমিৰ ভিতৰত বিচাৰিবলৈ স্থান অনুমতি আৰু API সংযোগ লাগিব। এতিয়া জিলাভিত্তিক বজাৰ বাছনি কৰিব পাৰে।" else "Location permission and geospatial API are required for 5/10 km search. Choose a district for now."; 2 -> if (isAssamese) "অসমৰ সংস্কৃতি, বিহু, কৃষি আৰু স্থানীয় প্ৰতিভাৰ বাবে কমিউনিটি ফিড। Backend আৰু media storage সংযোগ বাকী।" else "A feed for Assamese culture, Bihu, farming and local talent. Backend and media storage setup pending."; 3 -> if (isAssamese) "আপোনাৰ দোকানৰ নাম, স্থান আৰু বিৱৰণ দি আৰম্ভ কৰক। Shop সক্ৰিয় কৰিবলৈ verified ₹120 payment integration লাগিব।" else "Start with shop name, location and details. Verified ₹120 payment integration is required to activate a shop."; else -> if (isAssamese) "ভাষা সলনি, জিলা, সহায় আৰু গোপনীয়তা ছেটিংছ।" else "Language, district, help and privacy settings." }, color = Color.DarkGray, fontSize = 14.sp, lineHeight = 21.sp)
                                Spacer(Modifier.height(16.dp))
                                if (tab == 3) Button(onClick = { notice = if (isAssamese) "দোকান সৃষ্টি কৰিবলৈ Backend, OTP আৰু Payment setup সম্পূৰ্ণ কৰক।" else "Configure backend, OTP and payments to create a live shop." }) { Text(if (isAssamese) "দোকান সৃষ্টি কৰক" else "Create your shop") }
                                if (tab == 4) OutlinedButton(onClick = { isAssamese = !isAssamese }) { Text(if (isAssamese) "Switch to English" else "অসমীয়ালৈ সলনি কৰক") }
                                if (notice.isNotBlank()) Text(notice, color = Gamusa, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
        if (selectedDistrict) {
            val districts = listOf("All Assam", "Baksa", "Bajali", "Barpeta", "Biswanath", "Bongaigaon", "Cachar", "Charaideo", "Chirang", "Darrang", "Dhemaji", "Dhubri", "Dibrugarh", "Dima Hasao", "Goalpara", "Golaghat", "Hailakandi", "Hojai", "Jorhat", "Kamrup", "Kamrup Metropolitan", "Karbi Anglong", "Kokrajhar", "Lakhimpur", "Majuli", "Morigaon", "Nagaon", "Nalbari", "Sivasagar", "Sonitpur", "South Salmara-Mankachar", "Sribhumi", "Tamulpur", "Tinsukia", "Udalguri", "West Karbi Anglong")
            AlertDialog(onDismissRequest = { selectedDistrict = false }, title = { Text(if (isAssamese) "জিলা বাছনি কৰক" else "Choose district") }, text = { LazyColumn(Modifier.heightIn(max = 430.dp)) { items(districts) { d -> Text(d, Modifier.fillMaxWidth().clickable { district = d; selectedDistrict = false }.padding(12.dp), color = if (district == d) Gamusa else Navy) } } }, confirmButton = { TextButton(onClick = { selectedDistrict = false }) { Text(if (isAssamese) "বন্ধ কৰক" else "Close") } })
        }
        if (notice.isNotBlank() && tab < 3) AlertDialog(onDismissRequest = { notice = "" }, title = { Text(if (isAssamese) "থলুৱা হাট" else "Tholuwa Hat") }, text = { Text(notice) }, confirmButton = { TextButton(onClick = { notice = "" }) { Text("OK") } })
    }
}
