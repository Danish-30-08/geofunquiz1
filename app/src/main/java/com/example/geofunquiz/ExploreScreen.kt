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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Country(
    val name: String,
    val code: String,
    val capital: String,
    val continent: String
)

@Composable
fun ExploreScreen() {
    val allCountries = remember {
        listOf(
            // Asia (25)
            Country("Japan", "JP", "Tokyo", "Asia"),
            Country("South Korea", "KR", "Seoul", "Asia"),
            Country("Thailand", "TH", "Bangkok", "Asia"),
            Country("China", "CN", "Beijing", "Asia"),
            Country("India", "IN", "New Delhi", "Asia"),
            Country("Indonesia", "ID", "Jakarta", "Asia"),
            Country("Vietnam", "VN", "Hanoi", "Asia"),
            Country("Malaysia", "MY", "Kuala Lumpur", "Asia"),
            Country("Philippines", "PH", "Manila", "Asia"),
            Country("Singapore", "SG", "Singapore", "Asia"),
            Country("Pakistan", "PK", "Islamabad", "Asia"),
            Country("Bangladesh", "BD", "Dhaka", "Asia"),
            Country("Iran", "IR", "Tehran", "Asia"),
            Country("Turkey", "TR", "Ankara", "Asia"),
            Country("Saudi Arabia", "SA", "Riyadh", "Asia"),
            Country("UAE", "AE", "Abu Dhabi", "Asia"),
            Country("Israel", "IL", "Jerusalem", "Asia"),
            Country("Qatar", "QA", "Doha", "Asia"),
            Country("Nepal", "NP", "Kathmandu", "Asia"),
            Country("Sri Lanka", "LK", "Colombo", "Asia"),
            Country("Uzbekistan", "UZ", "Tashkent", "Asia"),
            Country("Kazakhstan", "KZ", "Astana", "Asia"),
            Country("Myanmar", "MM", "Naypyidaw", "Asia"),
            Country("Iraq", "IQ", "Baghdad", "Asia"),
            Country("Jordan", "JO", "Amman", "Asia"),

            // Europe (25)
            Country("France", "FR", "Paris", "Europe"),
            Country("Germany", "DE", "Berlin", "Europe"),
            Country("Italy", "IT", "Rome", "Europe"),
            Country("Spain", "ES", "Madrid", "Europe"),
            Country("United Kingdom", "GB", "London", "Europe"),
            Country("Netherlands", "NL", "Amsterdam", "Europe"),
            Country("Switzerland", "CH", "Bern", "Europe"),
            Country("Sweden", "SE", "Stockholm", "Europe"),
            Country("Norway", "NO", "Oslo", "Europe"),
            Country("Denmark", "DK", "Copenhagen", "Europe"),
            Country("Finland", "FI", "Helsinki", "Europe"),
            Country("Portugal", "PT", "Lisbon", "Europe"),
            Country("Greece", "GR", "Athens", "Europe"),
            Country("Austria", "AT", "Vienna", "Europe"),
            Country("Belgium", "BE", "Brussels", "Europe"),
            Country("Ireland", "IE", "Dublin", "Europe"),
            Country("Poland", "PL", "Warsaw", "Europe"),
            Country("Czech Republic", "CZ", "Prague", "Europe"),
            Country("Hungary", "HU", "Budapest", "Europe"),
            Country("Romania", "RO", "Bucharest", "Europe"),
            Country("Ukraine", "UA", "Kyiv", "Europe"),
            Country("Croatia", "HR", "Zagreb", "Europe"),
            Country("Slovakia", "SK", "Bratislava", "Europe"),
            Country("Bulgaria", "BG", "Sofia", "Europe"),
            Country("Iceland", "IS", "Reykjavík", "Europe"),

            // Americas (20)
            Country("USA", "US", "Washington D.C.", "Americas"),
            Country("Canada", "CA", "Ottawa", "Americas"),
            Country("Brazil", "BR", "Brasília", "Americas"),
            Country("Mexico", "MX", "Mexico City", "Americas"),
            Country("Argentina", "AR", "Buenos Aires", "Americas"),
            Country("Colombia", "CO", "Bogotá", "Americas"),
            Country("Peru", "PE", "Lima", "Americas"),
            Country("Chile", "CL", "Santiago", "Americas"),
            Country("Venezuela", "VE", "Caracas", "Americas"),
            Country("Ecuador", "EC", "Quito", "Americas"),
            Country("Guatemala", "GT", "Guatemala City", "Americas"),
            Country("Cuba", "CU", "Havana", "Americas"),
            Country("Haiti", "HT", "Port-au-Prince", "Americas"),
            Country("Dominican Republic", "DO", "Santo Domingo", "Americas"),
            Country("Bolivia", "BO", "Sucre", "Americas"),
            Country("Paraguay", "PY", "Asunción", "Americas"),
            Country("Uruguay", "UY", "Montevideo", "Americas"),
            Country("Panama", "PA", "Panama City", "Americas"),
            Country("Costa Rica", "CR", "San José", "Americas"),
            Country("Jamaica", "JM", "Kingston", "Americas"),

            // Africa (20)
            Country("Egypt", "EG", "Cairo", "Africa"),
            Country("South Africa", "ZA", "Pretoria", "Africa"),
            Country("Nigeria", "NG", "Abuja", "Africa"),
            Country("Kenya", "KE", "Nairobi", "Africa"),
            Country("Morocco", "MA", "Rabat", "Africa"),
            Country("Ethiopia", "ET", "Addis Ababa", "Africa"),
            Country("Ghana", "GH", "Accra", "Africa"),
            Country("Algeria", "DZ", "Algiers", "Africa"),
            Country("Tanzania", "TZ", "Dodoma", "Africa"),
            Country("Uganda", "UG", "Kampala", "Africa"),
            Country("Angola", "AO", "Luanda", "Africa"),
            Country("Mozambique", "MZ", "Maputo", "Africa"),
            Country("Ivory Coast", "CI", "Yamoussoukro", "Africa"),
            Country("Madagascar", "MG", "Antananarivo", "Africa"),
            Country("Cameroon", "CM", "Yaoundé", "Africa"),
            Country("Niger", "NE", "Niamey", "Africa"),
            Country("Mali", "ML", "Bamako", "Africa"),
            Country("Senegal", "SN", "Dakar", "Africa"),
            Country("Zimbabwe", "ZW", "Harare", "Africa"),
            Country("Zambia", "ZM", "Lusaka", "Africa"),

            // Oceania (20 - including major islands/territories to reach 20)
            Country("Australia", "AU", "Canberra", "Oceania"),
            Country("New Zealand", "NZ", "Wellington", "Oceania"),
            Country("Fiji", "FJ", "Suva", "Oceania"),
            Country("Papua New Guinea", "PG", "Port Moresby", "Oceania"),
            Country("Samoa", "WS", "Apia", "Oceania"),
            Country("Tonga", "TO", "Nukuʻalofa", "Oceania"),
            Country("Vanuatu", "VU", "Port Vila", "Oceania"),
            Country("Solomon Islands", "SB", "Honiara", "Oceania"),
            Country("Kiribati", "KI", "South Tarawa", "Oceania"),
            Country("Marshall Islands", "MH", "Majuro", "Oceania"),
            Country("Micronesia", "FM", "Palikir", "Oceania"),
            Country("Palau", "PW", "Ngerulmud", "Oceania"),
            Country("Tuvalu", "TV", "Funafuti", "Oceania"),
            Country("Nauru", "NR", "Yaren", "Oceania"),
            Country("Cook Islands", "CK", "Avarua", "Oceania"),
            Country("Niue", "NU", "Alofi", "Oceania"),
            Country("French Polynesia", "PF", "Papeete", "Oceania"),
            Country("New Caledonia", "NC", "Nouméa", "Oceania"),
            Country("Guam", "GU", "Hagåtña", "Oceania"),
            Country("American Samoa", "AS", "Pago Pago", "Oceania")
        ).sortedBy { it.name }
    }

    var searchQuery by remember { mutableStateOf("") }
    var selectedContinent by remember { mutableStateOf("All") }

    val continents = listOf("All", "Asia", "Europe", "Americas", "Africa", "Oceania")

    val filteredCountries = allCountries.filter {
        (selectedContinent == "All" || it.continent == selectedContinent) &&
                (it.name.contains(searchQuery, ignoreCase = true) || it.code.contains(searchQuery, ignoreCase = true))
    }

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
                CountryCard(country)
            }
        }
    }
}

@Composable
fun CountryCard(country: Country) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(24.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Country Code Circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(60.dp)
            ) {
                Text(
                    text = country.code,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF334155)
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
                    .clickable { /* Handle details click */ }
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
