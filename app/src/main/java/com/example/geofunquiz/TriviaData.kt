package com.example.geofunquiz

data class TriviaQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val funFact: String
)

object TriviaRepository {
    val questions = listOf(
        TriviaQuestion(
            "Which country has the most islands in the world?",
            listOf("Indonesia", "Philippines", "Sweden", "Canada"),
            2,
            "Sweden has over 221,800 islands, making it the country with the most islands!"
        ),
        TriviaQuestion(
            "Which city is located on two different continents?",
            listOf("London", "Istanbul", "Cairo", "Tokyo"),
            1,
            "Istanbul (Turkey) is divided into two parts, one in Europe and the other in Asia."
        ),
        TriviaQuestion(
            "Which continent is considered the largest desert on Earth?",
            listOf("Sahara", "Gobi", "Antarctica", "Australia"),
            2,
            "Antarctica is classified as a desert because it receives very little precipitation each year."
        ),
        TriviaQuestion(
            "Which country has the most pyramids in the world?",
            listOf("Egypt", "Sudan", "Mexico", "Peru"),
            1,
            "Sudan has over 200 pyramids, nearly double the amount found in Egypt!"
        ),
        TriviaQuestion(
            "What is the only country that spans across four hemispheres?",
            listOf("Kiribati", "Brazil", "Indonesia", "Ecuador"),
            0,
            "Kiribati is located in the central Pacific Ocean and lies in the Northern, Southern, Eastern, and Western hemispheres."
        )
    )
}