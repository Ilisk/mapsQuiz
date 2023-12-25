package com.example.mapsquiz


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mapsquiz.common.QUIZ_DATA
import com.example.mapsquiz.models.QuizData

class QuizActivity : AppCompatActivity() {

    @SuppressLint("CutPasteId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // Получаем данные из Intent
        val markerTitle = intent.getStringExtra("marker_title")
        // Теперь у вас есть заголовок маркера, который вы можете использовать в QuizActivity
        val value = findQuizDataByCity(markerTitle)

        val title = findViewById<TextView>(R.id.textCity)
        title.text = "${value?.city}"
        val image = findViewById<ImageView>(R.id.imageCity)
        image.setImageResource(value?.image ?:0)
        val question = findViewById<TextView>(R.id.textQuestion)
        question.text = value?.question
        // Предполагается, что у вас есть список ответов в вашем объекте QuizData
        val answers = value?.answers?.keys?.toList()
        val btnAnswer1 = findViewById<Button>(R.id.btnAnswer1)
        val btnAnswer2 = findViewById<Button>(R.id.btnAnswer2)
        val btnAnswer3 = findViewById<Button>(R.id.btnAnswer3)
        val btnAnswer4 = findViewById<Button>(R.id.btnAnswer4)
        val buttons = listOf(btnAnswer1, btnAnswer2, btnAnswer3, btnAnswer4)


// Проверяем, что список ответов не пуст
        if (answers != null && answers.isNotEmpty()) {

            // Проходим по списку кнопок и устанавливаем текст
            val answersMap = value.answers

            answersMap.entries.forEachIndexed { index, (answerText, _) ->
                if (index < buttons.size) {
                    buttons[index].text = answerText
                    buttons[index].visibility = View.VISIBLE

                    buttons[index].setOnClickListener {
                        updateButtonColors(buttons, value)
                    }
                } else {
                    // Если кнопок больше, чем ответов, скрываем лишние кнопки
                    buttons[index].visibility = View.GONE
                }

            }
        }


    }
    private fun showReturnDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Внимание")
        builder.setMessage("Желаете вернуться обратно?")
        builder.setPositiveButton("Да") { dialog, which ->
            // Пользователь нажал "Да", возвращаемся обратно
            finish()
        }
        builder.setNegativeButton("Нет") { dialog, which ->
            // Пользователь нажал "Нет", закрываем диалог
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun updateButtonColors(buttons: List<Button>, quizData: QuizData) {
        buttons.forEach { button ->
            val answerText = button.text.toString()
            button.setBackgroundColor(
                when {
                    quizData.answers[answerText] == true -> Color.parseColor("#03DAC6") // правильный ответ
                    quizData.answers[answerText] == false -> Color.parseColor("#CF6679") // неправильный ответ
                    else -> Color.LTGRAY // остальные кнопки
                }
            )

            // Запрещаем дальнейшие нажатия
            button.isEnabled = false

        }
        showReturnDialog()
    }


    //    private fun checkAnswer(markerTitle : String, button: Button) {
//        val value = findQuizDataByCity(markerTitle)
//        if (button.text == value?.answers?.entries?.firstOrNull { it.value }?.key) {
//
//        } else {
//            // Текст на кнопке не совпадает с правильным ответом
//        }
//
//
//    }
        private fun findQuizDataByCity(cityName: String?): QuizData? {
        val quizData = QUIZ_DATA.find { it.city == cityName }
        val correctAnswer = quizData?.answers?.entries?.firstOrNull { it.value }?.key.orEmpty()
        return quizData
    }
}
