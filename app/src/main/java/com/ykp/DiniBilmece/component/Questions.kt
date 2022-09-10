package com.ykp.DiniBilmece.component

import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

    val activity = (LocalLifecycleOwner.current as ComponentActivity)

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
            if (correctAnswerState.value == true && questionIndex.value == questionsSize - 1) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = CenterHorizontally
                ) {
                    Text(
                        text = "tebrikler", color = AppColors.mLightGray,
                        modifier = Modifier.padding(10.dp),
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 4.sp,
                        fontSize = 30.sp
                    )
                    Icon(Icons.Filled.Celebration, "celebration", modifier = Modifier.size(170.dp))
                    buttonBehavior(
                        "yeniden başlat", Modifier
                            .padding(top = 25.dp), Color.Red.copy(0.3f)
                    ) {
                        questionIndex.value = 0

                    }
                    buttonBehavior(
                        buttonText = "kapat", Modifier
                            .padding(top = 25.dp), Color.Red.copy(0.3f),
                        Icons.Filled.Close
                    ) {
                        activity.finish()
                    }
                }


            } else {
                Column(horizontalAlignment = CenterHorizontally) {
                    Text(
                        text = question.question, //the question element of the question object
                        modifier = Modifier
                            .padding(top = 17.dp, bottom = 9.dp)
                            .heightIn(29.dp, 91.dp),
                        fontSize = 19.sp,
                        color = AppColors.mOffWhite,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    //for each choice
                    choicesState.forEachIndexed { index, answerText ->
                        Row(
                            modifier = Modifier
                                .padding(7.dp)
                                .fillMaxWidth()
                                .heightIn(
                                    39.dp,
                                    121.dp
                                )
                                .border(
                                    width = if (index == selectedAnswerState.value) {
                                        0.dp
                                    } else {
                                        2.dp
                                    },
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            AppColors.mLightGray.copy(0.7f),
                                            AppColors.mLightGray.copy(0.3f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(
                                        topStartPercent = 21, bottomStartPercent = 21,
                                        topEndPercent = 21, bottomEndPercent = 21
                                    )
                                )
                                .clip(
                                    RoundedCornerShape(
                                        topStartPercent = 21,
                                        topEndPercent = 21,
                                        bottomEndPercent = 21,
                                        bottomStartPercent = 21
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
                                )
                                .clickable {

                                    updateAnswer(index)
                                },
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            val annotatedString = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontWeight = FontWeight.Light,
                                        color = if (correctAnswerState.value == true && index == selectedAnswerState.value) {
                                            Color.Green.copy(0.6f)
                                        } else if (correctAnswerState.value == false && index == selectedAnswerState.value) {
                                            Color.LightGray.copy(alpha = 0.5f)
                                        } else {
                                            AppColors.mOffWhite
                                        },
                                        fontSize = 17.sp,
                                    )
                                ) {

                                    append(answerText)
                                }
                            }
                            Text(
                                text = annotatedString,
                                modifier = Modifier.padding(5.dp),
                                fontFamily = if (index == selectedAnswerState.value && correctAnswerState.value == true) {
                                    FontFamily.Monospace
                                } else {
                                    FontFamily.Default
                                },
                                textDecoration = if (index == selectedAnswerState.value && correctAnswerState.value == false) {
                                    TextDecoration.LineThrough
                                } else if (index == selectedAnswerState.value && correctAnswerState.value == true) {
                                    TextDecoration.Underline
                                } else {
                                    TextDecoration.None
                                },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
//--------------------BUTTON--------------------------------------------------------------------


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        if (questionIndex.value != questionsSize) {
                            buttonBehavior(
                                "devam", Modifier
                                    .padding(top = 15.dp)
                            ) {
                                if (questionIndex.value < questionsSize - 1) {
                                    questionIndex.value += 1
                                }
                            }

                            buttonBehavior(
                                "test", Modifier
                                    .padding(top = 15.dp)
                            ) {
                                if (questionIndex.value < questionsSize) {
                                    questionIndex.value += 120
                                }
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
fun buttonBehavior(
    buttonText: String,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color.Green.copy(0.18f),
    icon: ImageVector = Icons.Default.ArrowForward,
    onClicked: () -> Unit
) {
    Button(
        onClick = { onClicked.invoke() },
        modifier = modifier,
        shape = RoundedCornerShape(
            topStartPercent = 40,
            bottomStartPercent = 40,
            bottomEndPercent = 40,
            topEndPercent = 40
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        ),
        border = BorderStroke(
            2.dp, brush = Brush.linearGradient(
                colors = listOf(
                    AppColors.mLightGray,
                    AppColors.mLightGray,
                    AppColors.mOffWhite,
                    AppColors.mLightGray
                )
            )
        )
    ) {
        Text(
            text = buttonText,
            modifier = Modifier.padding(5.dp),
            color = AppColors.mOffWhite,
            fontSize = 17.sp
        )
        Icon(
            icon,
            contentDescription = null
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

        }, modifier = Modifier.padding(11.dp)
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
fun CloseWindow(activity: ComponentActivity) {
    activity.finish()
}

@Composable
fun ShowProgress(totalQuestions: Int, currentQuestionNo: Int) {

    val gradient = Brush.linearGradient(
        listOf(
            Color(0xF27FCA63), Color(0xE943B043)
        )
    )

    Row(
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(45.dp)
            .border(
                width = 3.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.LightGray.copy(0.7f),
                        AppColors.mBlack.copy(0.7f),
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

