package com.example.geofunquiz

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class Country(
    val name: String,
    val code: String, // ISO 2-letter code
    val capital: String,
    val continent: String,
    val population: String = "Unknown",
    val currency: String = "Unknown",
    val funFact: String = "No fun fact available yet!"
)

@Composable
fun ExploreScreen() {
    val allCountries = remember {
        listOf(
            // Asia (24 - Israel removed)
            Country("Japan", "JP", "Tokyo", "Asia", "126M", "JPY", "Has the most vending machines in the world."),
            Country("South Korea", "KR", "Seoul", "Asia", "51M", "KRW", "World's capital of plastic surgery."),
            Country("Thailand", "TH", "Bangkok", "Asia", "70M", "THB", "It is illegal to step on money."),
            Country("China", "CN", "Beijing", "Asia", "1.4B", "CNY", "Table tennis is the national sport."),
            Country("India", "IN", "New Delhi", "Asia", "1.3B", "INR", "Cows are considered sacred."),
            Country("Indonesia", "ID", "Jakarta", "Asia", "273M", "IDR", "Has more than 17,000 islands."),
            Country("Vietnam", "VN", "Hanoi", "Asia", "97M", "VND", "World's second-largest coffee producer."),
            Country("Malaysia", "MY", "Kuala Lumpur", "Asia", "32M", "MYR", "Has the largest cave chamber in the world."),
            Country("Philippines", "PH", "Manila", "Asia", "109M", "PHP", "Is the world's leading exporter of coconut products."),
            Country("Singapore", "SG", "Singapore", "Asia", "5.7M", "SGD", "Is a city-state and an island."),
            Country("Pakistan", "PK", "Islamabad", "Asia", "220M", "PKR", "Produces over 50% of the world's hand-sewn footballs."),
            Country("Bangladesh", "BD", "Dhaka", "Asia", "164M", "BDT", "Is known as the 'Land of Rivers'."),
            Country("Iran", "IR", "Tehran", "Asia", "83M", "IRR", "Is the world's largest producer of saffron."),
            Country("Turkey", "TR", "Ankara", "Asia", "84M", "TRY", "The city of Istanbul spans across two continents."),
            Country("Saudi Arabia", "SA", "Riyadh", "Asia", "34M", "SAR", "Is home to the world's largest sand desert."),
            Country("UAE", "AE", "Abu Dhabi", "Asia", "9.8M", "AED", "Is home to Burj Khalifa, the world's tallest building."),
            Country("Qatar", "QA", "Doha", "Asia", "2.8M", "QAR", "The first Arab nation to host the FIFA World Cup."),
            Country("Nepal", "NP", "Kathmandu", "Asia", "29M", "NPR", "Has the only non-rectangular national flag."),
            Country("Sri Lanka", "LK", "Colombo", "Asia", "21M", "LKR", "Was the first country to have a female Prime Minister."),
            Country("Uzbekistan", "UZ", "Tashkent", "Asia", "33M", "UZS", "Is one of only two doubly landlocked countries."),
            Country("Kazakhstan", "KZ", "Astana", "Asia", "18M", "KZT", "Is the largest landlocked country in the world."),
            Country("Myanmar", "MM", "Naypyidaw", "Asia", "54M", "MMK", "Men and women wear sarong-like skirts called longyi."),
            Country("Iraq", "IQ", "Baghdad", "Asia", "40M", "IQD", "Is known as the cradle of civilization."),
            Country("Jordan", "JO", "Amman", "Asia", "10M", "JOD", "Home to the Dead Sea, the lowest point on earth."),

            // Europe (25)
            Country("France", "FR", "Paris", "Europe", "67M", "EUR", "Is the most visited country in the world."),
            Country("Germany", "DE", "Berlin", "Europe", "83M", "EUR", "Has over 1,500 different types of beer."),
            Country("Italy", "IT", "Rome", "Europe", "60M", "EUR", "Has more UNESCO World Heritage sites than any other country."),
            Country("Spain", "ES", "Madrid", "Europe", "47M", "EUR", "Produces almost half of the world's olive oil."),
            Country("United Kingdom", "GB", "London", "Europe", "67M", "GBP", "The royal family has no last name."),
            Country("Netherlands", "NL", "Amsterdam", "Europe", "17M", "EUR", "Has more bicycles than people."),
            Country("Switzerland", "CH", "Bern", "Europe", "8.6M", "CHF", "Has 4 official languages."),
            Country("Sweden", "SE", "Stockholm", "Europe", "10M", "SEK", "Has more islands than any other country (267,570)."),
            Country("Norway", "NO", "Oslo", "Europe", "5.4M", "NOK", "The Nobel Peace Prize is awarded in Oslo."),
            Country("Denmark", "DK", "Copenhagen", "Europe", "5.8M", "DKK", "Lego was invented here."),
            Country("Finland", "FI", "Helsinki", "Europe", "5.5M", "EUR", "Has the most saunas per capita."),
            Country("Portugal", "PT", "Lisbon", "Europe", "10M", "EUR", "Is the oldest country in Europe with the same borders."),
            Country("Greece", "GR", "Athens", "Europe", "10M", "EUR", "Is considered the birthplace of Western democracy."),
            Country("Austria", "AT", "Vienna", "Europe", "8.9M", "EUR", "The sewing machine was invented by an Austrian."),
            Country("Belgium", "BE", "Brussels", "Europe", "11M", "EUR", "Has three official languages."),
            Country("Ireland", "IE", "Dublin", "Europe", "4.9M", "EUR", "Is known as the 'Emerald Isle'."),
            Country("Poland", "PL", "Warsaw", "Europe", "38M", "PLN", "Marie Curie was born in Poland."),
            Country("Czech Republic", "CZ", "Prague", "Europe", "10M", "CZK", "Has the most castles per square kilometer."),
            Country("Hungary", "HU", "Budapest", "Europe", "9.6M", "HUF", "The Rubik's Cube was invented here."),
            Country("Romania", "RO", "Bucharest", "Europe", "19M", "RON", "Home to the largest population of brown bears in Europe."),
            Country("Ukraine", "UA", "Kyiv", "Europe", "43M", "UAH", "Is the largest country entirely in Europe."),
            Country("Croatia", "HR", "Zagreb", "Europe", "4M", "EUR", "The necktie (cravat) originated here."),
            Country("Slovakia", "SK", "Bratislava", "Europe", "5.4M", "EUR", "Has the highest concentration of caves in the world."),
            Country("Bulgaria", "BG", "Sofia", "Europe", "6.9M", "BGN", "Is one of the world's leading producers of rose oil."),
            Country("Iceland", "IS", "Reykjavík", "Europe", "366K", "ISK", "Does not have a public railway system."),

            // Americas (20)
            Country("USA", "US", "Washington D.C.", "Americas", "331M", "USD", "Does not have an official language."),
            Country("Canada", "CA", "Ottawa", "Americas", "38M", "CAD", "Has more lakes than the rest of the world combined."),
            Country("Brazil", "BR", "Brasília", "Americas", "212M", "BRL", "Largest producer of coffee in the world."),
            Country("Mexico", "MX", "Mexico City", "Americas", "128M", "MXN", "The Great Pyramid of Cholula is the world's largest."),
            Country("Argentina", "AR", "Buenos Aires", "Americas", "45M", "ARS", "Invented the radio in 1920."),
            Country("Colombia", "CO", "Bogotá", "Americas", "50M", "COP", "Has the second highest biodiversity in the world."),
            Country("Peru", "PE", "Lima", "Americas", "32M", "PEN", "Potatoes originated in the Peruvian Andes."),
            Country("Chile", "CL", "Santiago", "Americas", "19M", "CLP", "Is the longest country from north to south."),
            Country("Venezuela", "VE", "Caracas", "Americas", "28M", "VES", "Home to Angel Falls, the world's highest waterfall."),
            Country("Ecuador", "EC", "Quito", "Americas", "17M", "USD", "Named after the equator."),
            Country("Guatemala", "GT", "Guatemala City", "Americas", "17M", "GTQ", "Invented chocolate (Mayans)."),
            Country("Cuba", "CU", "Havana", "Americas", "11M", "CUP", "Famous for its classic cars."),
            Country("Haiti", "HT", "Port-au-Prince", "Americas", "11M", "HTG", "First independent nation of Latin America."),
            Country("Dominican Republic", "DO", "Santo Domingo", "Americas", "10M", "DOP", "First place settled by Europeans in the Americas."),
            Country("Bolivia", "BO", "Sucre", "Americas", "11M", "BOB", "Has the world's largest salt flat, Salar de Uyuni."),
            Country("Paraguay", "PY", "Asunción", "Americas", "7M", "PYG", "Dueling is legal if both parties are blood donors."),
            Country("Uruguay", "UY", "Montevideo", "Americas", "3.4M", "UYU", "Hosted the first FIFA World Cup in 1930."),
            Country("Panama", "PA", "Panama City", "Americas", "4.3M", "PAB", "Is the only place to see the sun rise over the Pacific."),
            Country("Costa Rica", "CR", "San José", "Americas", "5M", "CRC", "Has no standing army."),
            Country("Jamaica", "JM", "Kingston", "Americas", "2.9M", "JMD", "Has the fastest runners in the world."),

            // Africa (20)
            Country("Egypt", "EG", "Cairo", "Africa", "102M", "EGP", "The Great Pyramid was the tallest structure for 3,800 years."),
            Country("South Africa", "ZA", "Pretoria", "Africa", "59M", "ZAR", "Is the only country with three capital cities."),
            Country("Nigeria", "NG", "Abuja", "Africa", "206M", "NGN", "Is the largest producer of yams in the world."),
            Country("Kenya", "KE", "Nairobi", "Africa", "53M", "KES", "Is home to the 'Big Five' safari animals."),
            Country("Morocco", "MA", "Rabat", "Africa", "36M", "MAD", "Is the world's largest producer of sardines."),
            Country("Ethiopia", "ET", "Addis Ababa", "Africa", "114M", "ETB", "Is the only African country never to be colonized."),
            Country("Ghana", "GH", "Accra", "Africa", "31M", "GHS", "Was the first sub-Saharan African country to gain independence."),
            Country("Algeria", "DZ", "Algiers", "Africa", "43M", "DZD", "Is the largest country in Africa by land area."),
            Country("Tanzania", "TZ", "Dodoma", "Africa", "59M", "TZS", "Home to Mount Kilimanjaro, Africa's highest peak."),
            Country("Uganda", "UG", "Kampala", "Africa", "45M", "UGX", "Is one of the most ethnically diverse countries."),
            Country("Angola", "AO", "Luanda", "Africa", "32M", "AOA", "Is rich in diamonds and oil."),
            Country("Mozambique", "MZ", "Maputo", "Africa", "31M", "MZN", "Is the only country with a modern weapon on its flag."),
            Country("Ivory Coast", "CI", "Yamoussoukro", "Africa", "26M", "XOF", "Is the world's largest producer of cocoa."),
            Country("Madagascar", "MG", "Antananarivo", "Africa", "27M", "MGA", "90% of its wildlife is found nowhere else."),
            Country("Cameroon", "CM", "Yaoundé", "Africa", "26M", "XAF", "Is known as 'Africa in Miniature'."),
            Country("Niger", "NE", "Niamey", "Africa", "24M", "XOF", "One of the hottest countries in the world."),
            Country("Mali", "ML", "Bamako", "Africa", "20M", "XOF", "Home to Timbuktu, an ancient center of learning."),
            Country("Senegal", "SN", "Dakar", "Africa", "16M", "XOF", "Has a pink lake called Lake Retba."),
            Country("Zimbabwe", "ZW", "Harare", "Africa", "14M", "ZWL", "Home to Victoria Falls, one of the seven natural wonders."),
            Country("Zambia", "ZM", "Lusaka", "Africa", "18M", "ZMW", "Named after the Zambezi River."),

            // Oceania (20)
            Country("Australia", "AU", "Canberra", "Oceania", "25M", "AUD", "Has 10 times more sheep than people."),
            Country("New Zealand", "NZ", "Wellington", "Oceania", "5M", "NZD", "Has more sheep than people."),
            Country("Fiji", "FJ", "Suva", "Oceania", "896K", "FJD", "Has 333 islands, only 110 are inhabited."),
            Country("Papua New Guinea", "PG", "Port Moresby", "Oceania", "8.9M", "PGK", "Has over 800 indigenous languages."),
            Country("Samoa", "WS", "Apia", "Oceania", "198K", "WST", "The first island country in the world to move into 2012."),
            Country("Tonga", "TO", "Nukuʻalofa", "Oceania", "105K", "TOP", "Is the only Pacific monarchy."),
            Country("Vanuatu", "VU", "Port Vila", "Oceania", "307K", "VUV", "Invented land diving (origin of bungee jumping)."),
            Country("Solomon Islands", "SB", "Honiara", "Oceania", "686K", "SBD", "Has more than 900 islands."),
            Country("Kiribati", "KI", "South Tarawa", "Oceania", "119K", "AUD", "The only country located in all four hemispheres."),
            Country("Marshall Islands", "MH", "Majuro", "Oceania", "59K", "USD", "Home to the world's largest shark sanctuary."),
            Country("Micronesia", "FM", "Palikir", "Oceania", "115K", "USD", "Has a stone money bank."),
            Country("Palau", "PW", "Ngerulmud", "Oceania", "18K", "USD", "First country in the world to change its immigration laws for environment."),
            Country("Tuvalu", "TV", "Funafuti", "Oceania", "11K", "AUD", "One of the smallest and most remote countries."),
            Country("Nauru", "NR", "Yaren", "Oceania", "10K", "AUD", "The world's smallest island nation."),
            Country("Cook Islands", "CK", "Avarua", "Oceania", "17K", "NZD", "Uses both NZD and Cook Islands dollars."),
            Country("Niue", "NU", "Alofi", "Oceania", "1.6K", "NZD", "The world's first 'WiFi nation'."),
            Country("French Polynesia", "PF", "Papeete", "Oceania", "280K", "XPF", "Consists of 118 geographically dispersed islands."),
            Country("New Caledonia", "NC", "Nouméa", "Oceania", "271K", "XPF", "Has the second largest double barrier reef."),
            Country("Guam", "GU", "Hagåtña", "Oceania", "168K", "USD", "Is the westernmost territory of the US."),
            Country("American Samoa", "AS", "Pago Pago", "Oceania", "55K", "USD", "Tuna canning is the main industry.")
        ).sortedBy { it.name }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedContinent by remember { mutableStateOf("All") }
    var selectedCountry by remember { mutableStateOf<Country?>(null) }

    val continents = listOf("All", "Asia", "Europe", "Americas", "Africa", "Oceania")

    val filteredCountries = allCountries.filter {
        (selectedContinent == "All" || it.continent == selectedContinent) &&
                (it.name.contains(searchQuery, ignoreCase = true) || it.code.contains(searchQuery, ignoreCase = true))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F7FF))
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Explore World 🔎",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search for a Country...", color = Color.Gray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .background(Color.White, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFF3B82F6),
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Continent Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(continents) { continent ->
                    val isSelected = selectedContinent == continent
                    Surface(
                        onClick = { selectedContinent = continent },
                        color = if (isSelected) Color(0xFF3B82F6) else Color.White,
                        shape = RoundedCornerShape(20.dp),
                        shadowElevation = 4.dp,
                        modifier = Modifier.height(40.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        ) {
                            Text(
                                text = continent,
                                color = if (isSelected) Color.White else Color(0xFF1E293B),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Country List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredCountries) { country ->
                    CountryCard(country = country, onClick = { selectedCountry = country })
                }
            }
        }

        // Country Detail Overlay
        if (selectedCountry != null) {
            CountryDetailOverlay(
                country = selectedCountry!!,
                onClose = { selectedCountry = null }
            )
        }
    }
}

@Composable
fun CountryCard(country: Country, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Flag Display
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(60.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                AsyncImage(
                    model = "https://flagcdn.com/w320/${country.code.lowercase()}.png",
                    contentDescription = country.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Country Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = country.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1E293B)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${country.capital} • ${country.continent}",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Arrow Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF3B82F6))
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Details",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun CountryDetailOverlay(country: Country, onClose: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = country.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF1E293B)
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .background(Color(0xFFF87171), CircleShape)
                        .size(36.dp)
                ) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Large Flag Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .shadow(8.dp, RoundedCornerShape(24.dp))
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = "https://flagcdn.com/w640/${country.code.lowercase()}.png",
                        contentDescription = country.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                    
                    // Code & Continent Overlay
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.4f))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = country.code, color = Color.White, fontWeight = FontWeight.Black)
                            Text(text = country.continent, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Details List Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(8.dp, RoundedCornerShape(24.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    DetailRow(icon = Icons.Default.LocationOn, label = "Capital", value = country.capital, iconColor = Color(0xFF3B82F6))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF1F5F9))
                    DetailRow(icon = Icons.Default.Person, label = "Population", value = country.population, iconColor = Color(0xFF3B82F6))
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF1F5F9))
                    DetailRow(icon = Icons.Default.AttachMoney, label = "Currency", value = country.currency, iconColor = Color(0xFF3B82F6))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Fun Fact Card
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBBF7D0)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(24.dp))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Fun Fact!",
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = Color(0xFF22C55E)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = country.funFact,
                        fontSize = 16.sp,
                        color = Color(0xFF475569),
                        fontStyle = FontStyle.Italic,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, label: String, value: String, iconColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = label, color = Color(0xFF64748B), fontWeight = FontWeight.Medium)
        }
        Text(text = value, fontWeight = FontWeight.Black, color = Color(0xFF1E293B))
    }
}
