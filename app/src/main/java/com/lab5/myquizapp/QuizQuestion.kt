package com.lab5.myquizapp

class QuizQuestion {

    data class Question(
        val id: Int,
        val question: String,
        val image: Int,
        val optionOne: String,
        val optionTwo: String,
        val optionThree: String,
        val optionFour: String,
        val correctAnswer: Int
    )

    fun getQuestions(): ArrayList<Question> {
        val questionList = ArrayList<Question>()

        val q1 = Question(
            1,
            "What Logo is this?",
            R.drawable.postgresql,
            "PHP",
            "PostgreSQL",
            "Java",
            "C++",
            2
        )
        questionList.add(q1)

        val q2 = Question(
            2,
            "What logo is this?",
            R.drawable.php,
            "C++",
            "C#",
            "PHP",
            "Java",
            3
        )
        questionList.add(q2)

        val q3 = Question(
            3,
            "What logo is this?",
            R.drawable.gradle,
            "PHP",
            "PostgreSQL",
            "Gradle",
            "Elephant",
            3
        )
        questionList.add(q3)

        val q4 = Question(
            4,
            "What logo is this?",
            R.drawable.dart,
            "Flutter",
            "Dart",
            "Kotlin",
            "Swift",
            2
        )
        questionList.add(q4)

        val q5 = Question(
            5,
            "What logo is this?",
            R.drawable.elm,
            "Elm",
            "Matlab",
            "Elasticsearch",
            "C++",
            1
        )
        questionList.add(q5)

        return questionList
    }
}
