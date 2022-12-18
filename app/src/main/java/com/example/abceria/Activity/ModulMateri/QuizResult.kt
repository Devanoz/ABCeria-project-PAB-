package com.example.abceria.Activity.ModulMateri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.abceria.R
import com.example.abceria.model.quiz.QuizResult

class QuizResult : AppCompatActivity() {
    private lateinit var tvQuizPoint:TextView
    private lateinit var tvRightAnswer: TextView
    private lateinit var tvWrongAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val quizResult = intent.getSerializableExtra("answer") as QuizResult
        setContentView(R.layout.activity_quiz_result)
        initComponents()
        setQuizDataResult(quizResult)
    }

    private fun initComponents(){
        tvQuizPoint = findViewById(R.id.quiz_result_tv_total_point)
        tvRightAnswer = findViewById(R.id.quiz_result_tv_right_answer)
        tvWrongAnswer = findViewById(R.id.quiz_result_tv_wrong_answer)
    }
    private fun setQuizDataResult(quizResult: QuizResult) {
        tvQuizPoint.text = quizResult.point.toString()
        tvRightAnswer.text = quizResult.rightAnswer.toString()
        tvWrongAnswer.text = quizResult.wrongAnswer.toString()
    }
}