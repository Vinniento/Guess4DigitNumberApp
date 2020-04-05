package com.example.numbercrusher

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

enum class PositionInString {
    RP,
    WP,
    NC;

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var userInput: String
        var randomNumber = ""
        //toasties so umständlich gemacht damit sie einander canceln können und das aktuelle angezeigt wird
        var toast: Toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
        val createUniqueFourDigitNumber = { (0..9).shuffled().take(4).joinToString("") }
        val triedList: MutableList<Pair<List<PositionInString>, String>> = mutableListOf()
        lateinit var currentTry: List<PositionInString>

        //createUniqueFourDigitNumber().forEach {randomNumber +=it }
        randomNumber = createUniqueFourDigitNumber()
        button_guessnumber.setOnClickListener {
            toast.cancel()
            toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)

            userInput = text_inputnumber.text.toString()
            if (checkHowManyUniqueChars(userInput) < 4)
                Toast.makeText(this, "No repeated digits allowed", Toast.LENGTH_LONG).show()
            else {
                currentTry = checkWhichCharsAreInBothStrings(
                    userInput,
                    randomNumber
                )
                triedList.add(Pair(currentTry, userInput))
                text_tries.setText("${text_tries.text} \n $currentTry -> $userInput")

                toast.setText(currentTry.toString())
                toast.show()
            }
        }

        button_defeat.setOnClickListener {
            text_numberResult.text = randomNumber
            toast.cancel()
            toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
            toast.setText(randomNumber)
            toast.show()
        }
        button_reset.setOnClickListener {
            randomNumber = ""
            createUniqueFourDigitNumber().forEach { randomNumber += it }
            text_inputnumber.text.clear()
            text_numberResult.text = ""
            text_tries.setText("")
        }


    }

    /**
     * Returns a list of elementes which state if the
     * RP = Right position, WP = contains but wrong position NC = not contained
     */
    fun checkWhichCharsAreInBothStrings(
        userInput: String,
        randomNumber: String
    ): List<PositionInString> {
        val positionRightList = mutableListOf<PositionInString>()

        userInput.forEachIndexed { index, c ->
            positionRightList.add(
                when {
                    randomNumber[index] == c -> PositionInString.RP
                    randomNumber.contains(c.toString()) -> PositionInString.WP //!randomNumber.reversed().substring(index).contains(c)
                    else -> PositionInString.NC
                }
                //alte version - funktioniert aber da werden bei doppel vorkommenden Zahlen im Input "WP" angegeben und nicht "NC" wenns zwei indizes nach rechts weiter ist
                /* if(randomNumber[index] == c ) "RP"
                 else if(randomNumber.contains(c.toString())) {
                     if(randomNumber.reversed().substring(index).contains(c))
                         "NC"
                     else
                         "WP"
                 }
                 else "NC"*/

            )
        }

        // return positionRightList
        return positionRightList.sortedBy { it.ordinal }

    }

    /**
     * Returns how many unique chars there are in a string
     */

    fun checkHowManyUniqueChars(input: String): Int {
        val list = mutableListOf<Char>()
        input.forEach { if (!list.contains(it)) list.add(it) }
        return list.size
    }

    /**{
     * Creates 4 digit number
     * Deprecated version
     */
    fun createFourDigitNumber(): String {
        var fourDigitNumber = ""
        // val rangeList = {(0..9).random()}

        while (fourDigitNumber.length < 4) {
            val num = (0..9).random().toString()
            if (!fourDigitNumber.contains(num)) fourDigitNumber += num
        }

        return fourDigitNumber
    }
}
