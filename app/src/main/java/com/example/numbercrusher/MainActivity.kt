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
        var givenUp: String? = null
        
        val createUniqueFourDigitNumber = (0..9).shuffled().take(4)
        createUniqueFourDigitNumber.forEach {randomNumber +=it }
        
       button_guessnumber.setOnClickListener {
          
           userInput = text_inputnumber.text.toString()
           var correctPositionsList = compareNumbers(userInput, randomNumber)
           Toast.makeText(this, correctPositionsList.toString(), Toast.LENGTH_LONG).show()
       }
        button_defeat.setOnClickListener { 
            Toast.makeText(this, randomNumber, Toast.LENGTH_LONG).show()
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
                if(randomNumber[index] == c ) "RP"
                else if(randomNumber.contains(c.toString())) {
                    if(randomNumber.reversed().substring(index).contains(c))
                        "NC"
                    else
                        "WP"
                }
                else "NC"
                //alte version - funktioniert aber da werden bei doppel vorkommenden Zahlen im Input "WP" angegeben und nicht "NC"
                /* when {
                     randomNumber[index] == c -> "RP"
                     randomNumber.contains(c.toString())  -> "WP" //!randomNumber.reversed().substring(index).contains(c)
                     else -> "NC"
                 }*/
            )
        }

        return positionRightList
    }

    /**
     * Returns true if there are repeating Chars in a given String
     * NOT FINISHED -> Problem: Overwrites itself if previous digit was true, next could make repeatedDigit false again
     */
    fun checkIfStringContainsRepeatedChar(input: String): Boolean  {
        var repeatedDigit = false

        input.forEachIndexed {
                index, c -> repeatedDigit = (input.count {input[index] == c } > 2)
        }
        return repeatedDigit
    }

    /**
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
