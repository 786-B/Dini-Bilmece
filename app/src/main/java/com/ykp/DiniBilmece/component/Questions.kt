package com.ykp.DiniBilmece.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykp.DiniBilmece.model.QuestionItem
import com.ykp.DiniBilmece.screens.QuestionsViewModel
import com.ykp.DiniBilmece.util.AppColors
import kotlin.math.roundToInt

@Composable
fun Question(viewModel: QuestionsViewModel) {

    //declaration----------------------------------------------
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }
    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator(modifier = Modifier.size(50.dp))
    } else {
        val question = try {
            questions?.get(questionIndex.value)
        } catch (e: Exception) {
            null
        }
        //end of declaration-----------------------------------

        if (questions != null) {
            QuestionDisplay(
                question = question!!,
                questionIndex = questionIndex,
                viewModel = viewModel,
                questionsSize = questions.size
            )
        }

    }
}

//@Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    questionsSize: Int,
    // onNextClicked: (Int) -> Unit = {}
) {
    //declation--------------------------------------------------

    //list of choices
    val choicesState = remember(question) {
        question.choices.toMutableList()
    }

    //for 'selected' marking of the Radiobutton
    val selectedAnswerState = remember(question) {
        mutableStateOf<Int?>(value = null)
    }

    //sets after String comparison
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(value = null)
    }

    //end of declaration-------------------------------------------------


    //the 'Int' is the index from the forEach-loop
    val updateAnswer: (Int) -> Unit =
        remember(question) {
            {
                //for 'selected' Radiobutton
                selectedAnswerState.value = it
                //compare the Strings
                correctAnswerState.value = (choicesState[it] == question.answer)
            }
        }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), phase = 0f)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = AppColors.mLightPurple
    ) {

        //GUI content starts here------------------------------------------------------
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = CenterHorizontally
        ) {
            ShowProgress(questionsSize, questionIndex.value)
            QuestionTracker(questionIndex.value, questionsSize)
            DrawDottedLine(pathEffect = pathEffect)

            Column {
                Text(
                    text = question.question, //the question element of the question object
                    modifier = Modifier
                        .padding(6.dp)
                        .heightIn(30.dp, 90.dp),
                    fontSize = 17.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                //for each choice
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(7.dp)
                            .fillMaxWidth()
                            .heightIn(
                                40.dp,
                                120.dp
                            )
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(15.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(
                                if (correctAnswerState.value == true
                                    && index == selectedAnswerState.value
                                ) {
                                    Color.Green.copy(alpha = 0.2f)
                                } else if (correctAnswerState.value == false
                                    && index == selectedAnswerState.value
                                ) {
                                    Color.Red.copy(alpha = 0.2f)

                                } else {
                                    Color.Transparent
                                }
                            ),
                        verticalAlignment = CenterVertically
                    )
                    {
                        RadioButton(
                            //marked as selected after click in Button
                            selected = (selectedAnswerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(

                                //if correct, then green, otherwise red
                                selectedColor = if (correctAnswerState.value == true
                                    && index == selectedAnswerState.value
                                ) {
                                    Color.Green.copy(alpha = 0.2f)
                                } else {
                                    Color.Red.copy(alpha = 0.2f)

                                }
                            )
                        )//end Radiobutton

                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true && index == selectedAnswerState.value) {
                                        Color.Green.copy(alpha = 0.7f)
                                    } else if (correctAnswerState.value == false && index == selectedAnswerState.value) {
                                        Color.LightGray.copy(alpha = 0.5f)
                                    } else {
                                        AppColors.mOffWhite
                                    },
                                    fontSize = 17.sp
                                )
                            ) {

                                append(answerText)
                            }
                        }
                        Text(text = annotatedString, modifier = Modifier.padding(6.dp))
                    }
                }
//--------------------BUTTON--------------------------------------------------------------------


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    if (questionIndex.value == 122) {
                        buttonBehavior(
                            "yeniden başlat", Modifier
                                .padding(top = 15.dp)
                        ) {
                            questionIndex.value = 0


                        }
                    }
                    if (questionIndex.value != 122) {
                        buttonBehavior(
                            "Devam", Modifier
                                .padding(top = 15.dp)
                        ) {
                            if (questionIndex.value in 0 until questionsSize - 1) {
                                questionIndex.value += 1
                            }
                        }
                    }
                }


            }
        }
        //END of GUI content---------------------------------------------------------
    }
}

@Composable
fun buttonBehavior(buttonText: String, modifier: Modifier = Modifier, onClicked: () -> Unit) {
    Button(
        onClick = { onClicked.invoke() },
        modifier = modifier,
        // padding(top = 15.dp).align(alignment = Alignment.CenterHorizontally),
        // enabled = (correctAnswerState.value == true),
        shape = RoundedCornerShape(35.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.mLightBlue
        )
    ) {
        Text(
            text = buttonText,
            modifier = Modifier.padding(5.dp),
            color = AppColors.mOffWhite,
            fontSize = 17.sp
        )

    }
}


@Composable
fun QuestionTracker(
    counter: Int = 10, outOf: Int = 100
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp
                    )
                ) {
                    append("Soru ${counter + 1}/")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.mLightGray,
                            fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    ) {
                        append("$outOf")
                    }
                }
            }

        }, modifier = Modifier.padding(20.dp)
    )
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun ShowProgress(totalQuestions: Int, currentQuestionNo: Int) {

    val gradient = Brush.linearGradient(
        listOf(
            Color(0xFFF95075), Color(0xFFBE6BE5)
        )
    )

    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 4.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        AppColors.mLightGray,
                        AppColors.mBlack,
                    )
                ), shape = RoundedCornerShape(34.dp)
            )
            .clip(
                RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50,
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
            )
            .background(Color.Transparent),
        verticalAlignment = CenterVertically
    ) {
        Button(
            contentPadding = PaddingValues(start = 1.dp),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(((currentQuestionNo.toFloat() / totalQuestions)))
                .background(brush = gradient),
            enabled = false,
            elevation = null,
            colors = buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent

            )
        ) {
            if (currentQuestionNo > 13) {
                Text(
                    "${(((currentQuestionNo.toFloat() / totalQuestions) * 100) + 1).roundToInt()} %",
                    color = Color.Black, textAlign = TextAlign.Start
                )
            }
        }
    }

}
//TODO 1: son soru hemen 100% oluyor
//TODO 2: devam sadece dogruysa
