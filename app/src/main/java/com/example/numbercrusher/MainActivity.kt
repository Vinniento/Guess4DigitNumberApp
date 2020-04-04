package com.example.numbercrusher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var userInput: String
        var randomNumber = ""
        //toasties so umständlich gemacht damit sie einander canceln können und das aktuelle angezeigt wird
        var toast: Toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
        val createUniqueFourDigitNumber = {(0..9).shuffled().take(4).joinToString("")}

        //createUniqueFourDigitNumber().forEach {randomNumber +=it }
        randomNumber = createUniqueFourDigitNumber()
       button_guessnumber.setOnClickListener {
           toast.cancel()
           toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)

           userInput = text_inputnumber.text.toString()
            if(checkHowManyUniqueChars(userInput) < 4)
               Toast.makeText(this, "No repeated digits allowed", Toast.LENGTH_LONG).show()
           else {
                val correctPositionsList = compareNumbers(userInput, randomNumber)
                toast.setText(correctPositionsList.toString())
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
        button_reset.setOnClickListener{
            randomNumber =""
            createUniqueFourDigitNumber().forEach {randomNumber +=it }
            text_inputnumber.text.clear()
            text_numberResult.text = ""
        }

        

    }

    /**
     * Returns a list of elementes which state if the
     * RP = Right position, WP = contains but wrong position NC = not contained
     */
    fun compareNumbers (userInput: String, randomNumber: String): MutableList<String> {
        val positionRightList = mutableListOf<String>()

        userInput.forEachIndexed { index, c ->
            positionRightList.add(
                when {
                    randomNumber[index] == c -> "RP"
                    randomNumber.contains(c.toString())  -> "WP" //!randomNumber.reversed().substring(index).contains(c)
                    else -> "NC"
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

        return positionRightList
    }

    /**
     * Returns how many unique chars there are in a string
     */

    fun checkHowManyUniqueChars(input: String): Int{
       val list = mutableListOf<Char>()
        input.forEach{if(!list.contains(it)) list.add(it)}
        return list.size
    }
    /**{
     * Creates 4 digit number
     * Deprecated version
     */
    fun createFourDigitNumber(): String {
        var fourDigitNumber  = ""
        // val rangeList = {(0..9).random()}

        while(fourDigitNumber.length < 4){
            val num = (0..9).random().toString()
            if (!fourDigitNumber.contains(num)) fourDigitNumber +=num
        }

        return fourDigitNumber
    }
}
