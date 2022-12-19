package com.example.abceria.Activity.quiz

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.abceria.Activity.ModulMateri.QuizResult
import com.example.abceria.Activity.quiz.dialog.EndQuizDialog
import com.example.abceria.R
import com.example.abceria.db.DB
import com.example.abceria.model.quiz.Question
import com.example.abceria.model.quiz.Quiz
import com.example.abceria.utility.IntentModulUtils
import com.example.abceria.utility.IntentQuizUtils

class QuizDetail : AppCompatActivity() {
    private val firestore = DB.getFirestoreInstance()

    private lateinit var quizPosition: TextView
    private lateinit var tvQuizPoint: TextView
    private lateinit var btnOptionA : Button
    private lateinit var btnOptionB : Button
    private lateinit var btnOptionC : Button
    private lateinit var btnOptionD : Button
    //navigator
    private lateinit var leftNavigatorButton: Button
    private  lateinit var rightNavigationButton: Button

    private val questions: ArrayList<Question> = ArrayList()
    private lateinit var answerState: Array<String>
    private lateinit var optionState: Array<String>
    private lateinit var questionDetail: TextView

    companion object{
        private  var questionIndex : Int = 0
        private  var isFinish = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_detail)
        initComponents()
        setButtonOptionListener()
        questionIndex = 0
        isFinish = false
        val quizId: String? = intent.getStringExtra(IntentQuizUtils.QUIZ_ID)
        val modulId: String? = intent.getStringExtra(IntentModulUtils.MODUL_ID)
        loadQuestionData(modulId,quizId)
    }

    private fun processAnswer(): com.example.abceria.model.quiz.QuizResult {
        var point: Long = 0
        var rightAnswer = 0
        for(i in 0 until questions.size){
            if(answerState[i] == questions[i].key){
                point += questions[i].point
                rightAnswer++
            }

        }
        var wrongAnswer = questions.size - rightAnswer
        return com.example.abceria.model.quiz.QuizResult(point,rightAnswer,wrongAnswer)
    }

    @SuppressLint("SetTextI18n")
    private fun quizActionListener(){
        rightNavigationButton.setOnClickListener {
            if(isFinish){
                val answer = answerState
                val questions = questions
                val options = optionState

                val quizResult:com.example.abceria.model.quiz.QuizResult = processAnswer()
                EndQuizDialog(quizResult,this).show(this.supportFragmentManager, EndQuizDialog.TAG)
            }
            resetButtonCheck()
            if((questionIndex+1) <= (questions.size-1)){
                questionIndex++
                quizPosition.text = "Pertanyaan ke ${questionIndex+1}"
                val question = questions[questionIndex]
                tvQuizPoint.text = "${question.point} point"
                questionDetail.text = question.pertanyaan
                btnOptionA.text = question.optionA
                btnOptionB.text = question.optionB
                btnOptionC.text = question.optionC
                btnOptionD.text = question.optionD

            }
            when(optionState[questionIndex]){
                com.example.abceria.utility.Question.OPTION_A -> checkButtonA()
                com.example.abceria.utility.Question.OPTION_B -> checkButtonB()
                com.example.abceria.utility.Question.OPTION_C -> checkButtonC()
                com.example.abceria.utility.Question.OPTION_D -> checkButtonD()
            }
            //check if last question button text set to finish
            if(questionIndex == (questions.size-1) ){
                rightNavigationButton.text = "Selesai"
                isFinish = true
            }else{
                rightNavigationButton.text = "Selanjutnya"
            }

        }
        leftNavigatorButton.setOnClickListener {
            resetButtonCheck()
            if((questionIndex-1) >= 0){
                questionIndex--
                quizPosition.text = "Pertanyaan ke ${questionIndex+1}"
                val question = questions[questionIndex]
                tvQuizPoint.text = "${question.point} point"
                questionDetail.text = question.pertanyaan
                btnOptionA.text = question.optionA
                btnOptionB.text = question.optionB
                btnOptionC.text = question.optionC
                btnOptionD.text = question.optionD
            }
            when(optionState[questionIndex]){
                com.example.abceria.utility.Question.OPTION_A -> checkButtonA()
                com.example.abceria.utility.Question.OPTION_B -> checkButtonB()
                com.example.abceria.utility.Question.OPTION_C -> checkButtonC()
                com.example.abceria.utility.Question.OPTION_D -> checkButtonD()
            }
            //check if last question button text set to finish
            if(questionIndex == (questions.size-1) ){
                rightNavigationButton.text = "Selesai"
            }else{
                rightNavigationButton.text = "Selanjutnya"
                isFinish = false
            }

        }
    }

    private fun loadQuestionData(modulId: String?, quizId: String?) {
        if (modulId != null && quizId != null) {
            firestore.collection("modul").document(modulId).collection("quiz").document(quizId).collection("question").get().addOnSuccessListener {
                it.documents.forEach { document ->
                    val question = Question();
                    question.imageUrl = document.get("imageUrl") as String
                    question.key = document.get("key") as String
                    question.optionA = document.get("optionA") as String
                    question.optionB = document.get("optionB") as String
                    question.optionC = document.get("optionC") as String
                    question.optionD = document.get("optionD") as String
                    question.pertanyaan = document.get("pertanyaan") as String
                    question.point = document.get("point") as Long
                    questions.add(question)
                }

            }.continueWith{
                answerState = Array(questions.size) { "" }
                optionState = Array(questions.size) { "" }
                Log.d("answerstateSize", answerState.size.toString())
                Log.d("optionsStateSize",optionState.size.toString())
            }.continueWith{
                val question = questions[questionIndex]
                tvQuizPoint.text = "${question.point} point"
                quizPosition.text = "Pertanyaan ke ${questionIndex+1}"
                questionDetail.text = question.pertanyaan
                btnOptionA.text = question.optionA
                btnOptionB.text = question.optionB
                btnOptionC.text = question.optionC
                btnOptionD.text = question.optionD
                quizActionListener()
            }
        }

    }

    private fun initComponents(){
        quizPosition = findViewById(R.id.quiz_detail_tv_question_position)
        tvQuizPoint = findViewById(R.id.quiz_detail_tv_point)
        questionDetail = findViewById(R.id.quiz_detail_tv_question)
        btnOptionA = findViewById(R.id.btn_option_A)
        btnOptionB = findViewById(R.id.btn_option_B)
        btnOptionC = findViewById(R.id.btn_option_C)
        btnOptionD = findViewById(R.id.btn_option_D)

        //navigator
        rightNavigationButton = findViewById(R.id.quize_detail_btn_right)
        leftNavigatorButton = findViewById(R.id.quiz_detail_btn_left)

        btnOptionA.setBackgroundColor(Color.WHITE)
        btnOptionB.setBackgroundColor(Color.WHITE)
        btnOptionC.setBackgroundColor(Color.WHITE)
        btnOptionD.setBackgroundColor(Color.WHITE)
    }

    private fun setButtonOptionListener(){
        btnOptionA.setOnClickListener {
           checkButtonA()
        }
        btnOptionB.setOnClickListener {
            checkButtonB()
        }
        btnOptionC.setOnClickListener {
            checkButtonC()
        }
        btnOptionD.setOnClickListener {
            checkButtonD()
        }
    }
    private fun resetButtonCheck(){
        btnOptionA.setBackgroundColor(Color.WHITE)
        btnOptionB.setBackgroundColor(Color.WHITE)
        btnOptionC.setBackgroundColor(Color.WHITE)
        btnOptionD.setBackgroundColor(Color.WHITE)
        btnOptionA.isEnabled = true
        btnOptionB.isEnabled = true
        btnOptionC.isEnabled = true
        btnOptionD.isEnabled = true
    }
    private fun checkButtonA(){
        btnOptionA.setBackgroundColor(Color.GRAY)
        btnOptionA.isEnabled = false
        btnOptionB.setBackgroundColor(Color.WHITE)
        btnOptionC.setBackgroundColor(Color.WHITE)
        btnOptionD.setBackgroundColor(Color.WHITE)
        btnOptionB.isEnabled = true
        btnOptionC.isEnabled = true
        btnOptionD.isEnabled = true
        answerState[questionIndex] = questions[questionIndex].optionA
        optionState[questionIndex] = com.example.abceria.utility.Question.OPTION_A
    }
    private fun checkButtonB(){
        btnOptionB.setBackgroundColor(Color.GRAY)
        btnOptionB.isEnabled = false
        btnOptionA.setBackgroundColor(Color.WHITE)
        btnOptionC.setBackgroundColor(Color.WHITE)
        btnOptionD.setBackgroundColor(Color.WHITE)
        btnOptionA.isEnabled = true
        btnOptionC.isEnabled = true
        btnOptionD.isEnabled = true
        answerState[questionIndex] = questions[questionIndex].optionB
        optionState[questionIndex] = com.example.abceria.utility.Question.OPTION_B
    }
    private fun checkButtonC(){
        btnOptionC.setBackgroundColor(Color.GRAY)
        btnOptionC.isEnabled = false
        btnOptionA.setBackgroundColor(Color.WHITE)
        btnOptionB.setBackgroundColor(Color.WHITE)
        btnOptionD.setBackgroundColor(Color.WHITE)
        btnOptionA.isEnabled = true
        btnOptionB.isEnabled = true
        btnOptionD.isEnabled = true
        answerState[questionIndex] = questions[questionIndex].optionC
        optionState[questionIndex] = com.example.abceria.utility.Question.OPTION_C
    }
    private fun checkButtonD(){
        btnOptionD.setBackgroundColor(Color.GRAY)
        btnOptionD.isEnabled = false
        btnOptionA.setBackgroundColor(Color.WHITE)
        btnOptionB.setBackgroundColor(Color.WHITE)
        btnOptionC.setBackgroundColor(Color.WHITE)
        btnOptionA.isEnabled = true
        btnOptionB.isEnabled = true
        btnOptionC.isEnabled = true
        answerState[questionIndex] = questions[questionIndex].optionD
        optionState[questionIndex] = com.example.abceria.utility.Question.OPTION_D
    }


}