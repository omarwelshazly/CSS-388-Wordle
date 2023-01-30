package com.example.wordle

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager

import android.widget.Button
import android.widget.TextView
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
    object FourLetterWordList {
        // List of most common 4 letter words from: https://7esl.com/4-letter-words/
        val fourLetterWords =
            "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"

        // Returns a list of four letter words as a list
        fun getAllFourLetterWords(): List<String> {
            return fourLetterWords.split(",")
        }

        // Returns a random four letter word from the list in all caps
        fun getRandomFourLetterWord(): String {
            val allWords = getAllFourLetterWords()
            val randomNumber = (0..allWords.size).shuffled().last()
            return allWords[randomNumber].uppercase()
        }
    }
    var word_to_guess = FourLetterWordList.getRandomFourLetterWord()

    private fun checkGuess(guess: String,wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }
    var chance = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val guess_button = findViewById(R.id.guess_button) as Button

        val reset_button = findViewById(R.id.reset_button) as Button


        var word_to_guess_text = findViewById(R.id.word_to_guess) as TextView
        var guessed_text = findViewById(R.id.guessed_text) as EditText

        val guess_1_text = findViewById(R.id.guess_1_text) as TextView
        val guess_1 = findViewById(R.id.guess_1) as TextView
        val guess_1_check_text = findViewById(R.id.guess_1_check_text) as TextView
        val guess_1_check = findViewById(R.id.guess_1_check) as TextView

        val guess_2_text = findViewById(R.id.guess_2_text) as TextView
        val guess_2 = findViewById(R.id.guess_2) as TextView
        val guess_2_check_text = findViewById(R.id.guess_2_check_text) as TextView
        val guess_2_check = findViewById(R.id.guess_2_check) as TextView

        val guess_3_text = findViewById(R.id.guess_3_text) as TextView
        val guess_3 = findViewById(R.id.guess_3) as TextView
        val guess_3_check_text = findViewById(R.id.guess_3_check_text) as TextView
        val guess_3_check = findViewById(R.id.guess_3_check) as TextView

        guess_button.setOnClickListener {
            hideKeyboard()

            if (chance == 2){
                guess_3_text.isVisible = true
                guess_3.text = guessed_text.text.toString().uppercase()
                guess_3_check.text = checkGuess(guessed_text.text.toString().uppercase(),word_to_guess)
                guess_3_check.isVisible = true
                guess_3_check_text.isVisible = true
                guess_3.isVisible = true
                word_to_guess_text.text = word_to_guess
                word_to_guess_text.isVisible = true
                guess_button.isVisible = false
                reset_button.isVisible = true

            }
            if (chance == 1){
                guess_2_text.isVisible = true
                guess_2.text = guessed_text.text.toString().uppercase()
                guess_2_check.text = checkGuess(guessed_text.text.toString().uppercase(),word_to_guess)
                guess_2_check.isVisible = true
                guess_2_check_text.isVisible = true
                guess_2.isVisible = true
                chance++
            }
            if (chance == 0){
                guess_1_text.isVisible = true
                guess_1.text = guessed_text.text.toString().uppercase()
                guess_1_check.text = checkGuess(guessed_text.text.toString().uppercase(),word_to_guess)
                guess_1_check.isVisible = true
                guess_1_check_text.isVisible = true
                guess_1.isVisible = true
                chance++
            }

            guessed_text.text.clear()

        }

        reset_button.setOnClickListener{
            guessed_text.text.clear()
            guess_button.isVisible = true
            reset_button.isVisible= false
            chance = 0
            word_to_guess_text.isVisible = false
            word_to_guess = FourLetterWordList.getRandomFourLetterWord()
            word_to_guess_text.text = word_to_guess
            guess_1_text.isVisible = false
            guess_1_check.isVisible = false
            guess_1_check_text.isVisible = false
            guess_1.isVisible = false

            guess_2_text.isVisible = false
            guess_2_check.isVisible = false
            guess_2_check_text.isVisible = false
            guess_2.isVisible = false

            guess_3_text.isVisible = false
            guess_3_check.isVisible = false
            guess_3_check_text.isVisible = false
            guess_3.isVisible = false

        }
    }
}