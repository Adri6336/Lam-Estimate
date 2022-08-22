package com.example.lamestimate


import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.ParseException
import java.util.*


class MainActivity : AppCompatActivity() {

    // ============  Links ============
    fun goTo(url: String){
        // https://stackoverflow.com/questions/5026349/how-to-open-a-website-when-a-button-is-clicked-in-android-application
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)
    }

    // ============  MAIN FUNCTIONS ============

    fun IntRange.random() = Random().nextInt((endInclusive + 1) - start) + start

    fun bling(param1: Float, param2: Int, param3: Int){
        /*
        This function adds special effects to the get estimate button.

        Types:
            - Money. This will randomly appear on screen, then fade out quickly.
            - Sound. A random sound bit from Mr. Krabs will be chosen. Rarely, a Rick Roll presents

        :return: None, plays effects
         */

        // 1. Set up selections
        var mainselection = listOf(R.raw.aha, R.raw.chaching, R.raw.money, R.raw.yougotmoney)
        var rickselection = listOf(R.raw.rick1, R.raw.rick2)
        var rareselection = listOf(R.raw.rick1, R.raw.rick2, R.raw.memehorn, R.raw.johncena)
        var failureselection = listOf(R.raw.fukindonkey, R.raw.no, R.raw.swamp)

        val mainsize = mainselection.size - 1
        val raresize = rareselection.size - 1
        val ricksize = rickselection.size - 1
        val failsize = failureselection.size -1

        // 2. Decide what selection list to choose from, then randomly get something from it
        var selectfrom = (0..99).random()  // This will determine which set to use
        var mainrange = (0..mainsize).random()  // This is the chosen item from main set
        var rarerange = (0..raresize).random()  // This is the chosen item from rare set
        var rickrange = (0..ricksize).random()  // This is the chosen item from rickrolls
        var failrange = (0..failsize).random()  // This is the chosen item from fails

        // 2.1 See if easter egg is triggered
        if (param1 == 8.5f && param2 == 2 && param3 == 15) {  // If the default is entered
            if (selectfrom >= 10) {  // ~90% chance for rick roll
                // This is the rick roll
                val player = MediaPlayer.create(this, rickselection[rickrange])
                player.start()
                return

            } else {
                // This is the rick roll
                val player = MediaPlayer.create(this, rareselection[rarerange])
                player.start()
                return
            }

        } else if (param1 == 69.0f || param2 == 69 || param3 == 69){  // Funny number
            val player = MediaPlayer.create(this, R.raw.nice)
            player.start()
            return

        } else if (param1 == 69.420f){  // Ultra funny number
            val player = MediaPlayer.create(this, R.raw.niceweed)
            player.start()
            return

        } else if (param1 == 420f || param2 == 420 || param3 == 420){  // Weed number
            val player = MediaPlayer.create(this, R.raw.weed)
            player.start()
            return
        } else if (param1 == 999999f && param2 == 999999 && param3 == 999999){
            val player = MediaPlayer.create(this, failureselection[failrange])
            player.start()
            return
        }


        // 2.2 Default mode: play Krabs' sounds for the most part
        if (selectfrom <= 89 ){  // ~10% chance for rick roll
            // This is the normal Mr. Krabs
            val player = MediaPlayer.create(this, mainselection[mainrange])
            player.start()

        } else{
            // This is rick rolls and memes
            val player = MediaPlayer.create(this, rareselection[rarerange])
            player.start()

        }


        return
    }


    fun get_dec(rawfloat: Float): kotlin.Float {
        /*
        This function rounds the float to the hundredths place.

        :param rawfloat: This is an unconverted float
        :return: Float rounded to hundredths place
         */

        Log.d("START GET_DEC", "==============================")

        val df = DecimalFormat("#.##")  // Specify how the float should appear
        df.roundingMode = RoundingMode.UP  // Specify that the cent should be rounded up
        val rounded = df.format(rawfloat).toFloat()  // Get the output and preserve as float

        Log.d("Original", "$rawfloat")
        Log.d("New", "$rounded")

        return rounded
    }


    fun get_rounds(pagecount: Int, perline: Int): kotlin.Int {
        /*
        This function will determine how many rounds will need to be done for a job.

        :param pagecount: This is the number of pages to laminate
        :param perline: This is the number of sheets that can fit in a single run
        :return: Int representing the number of expected rounds
         */

        var division = pagecount.toFloat() / perline.toFloat()  // Divide the two
        var test = ((division) % 1.0)  // If the modulus of the quotient is 1, its a whole num

        Log.d("division results", "$division")
        Log.d("test results", "$test")

        if (test != 0.0){  // Add one to any partial nums to act as ceiling fn
            division += 1f  // This acts as a ceiling function

            Log.d("mod div", "$division")
            Log.d("test outcome", "${division.toInt()}")

            return division.toInt()  // When passed to Int fn, only the whole num is saved
        }

        return division.toInt()  // Do nothing otherwise
    }


    fun get_estimate(width: Float, perline: Int, pagecount: Int, rate: Float, mincharge: Float){
        /*
        This function gets an estimate, returning info as low to high

        :param width: Float representing the width of the page in inches
        :param perline: Int representing the number of sheets that can fit in a single run
        :param pagecount: Int representing the number of sheets to laminate
        :param rate: Float representing the charge rate per foot
        :return: None. This function will only update the displayed text to show the
                 cost of the operation.
         */


        // Connect to estimated text box
        var output = findViewById<TextView>(R.id.estimated_text)

        // 0. Convert in to ft
        var widthft = (width) / 12

        // 1. Plug values into the equation and get result (modified to use a single average)
        var rounds = get_rounds(pagecount, perline)
        var totalft = ((rounds * widthft) + (rounds * 0.2318))  // 0.2318 is the average ft for me
        Log.d("Total ft", "$totalft")

        // 2. Get range
        var minft = 0.0  // Initialize it, but don't add data just yet
        var maxft = (totalft + 3)

        if ((totalft - 3.0) < 0.0){  // Nothing can be less than 1 ft, so 1 must be absolute min
            minft = 1.0
            Log.d("Less than 1", "${minft - 3}")
        } else {
            minft = (totalft - 3)
            Log.d("Greater than 1", "${minft - 3}")
        }

        // 3. Get price
        var pricemin = minft * rate  // ft * rate = estimated charge
        var pricemax = maxft * rate

        if (pricemin.toFloat() < mincharge){  // The min charge
            pricemin = mincharge.toDouble()
        }

        // 4. Output info
        if (pricemin < pricemax){
            output.text = "${'$'}${get_dec(pricemin.toFloat())} - ${'$'}${get_dec(pricemax.toFloat())}"
        }
        else{
            output.text = "${'$'}${get_dec(pricemin.toFloat())}"
        }
    }


    // ============ MAIN APP ============
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Connect items from main app
        var calculate_button = findViewById<Button>(R.id.get_estimate)
        var width_entry = findViewById<EditText>(R.id.width_entry)
        var perline_entry = findViewById<EditText>(R.id.perline_entry)
        var pagecount_entry = findViewById<EditText>(R.id.numpages_entry)
        var rate_entry = findViewById<EditText>(R.id.rate_entry)
        var mincharge_entry = findViewById<EditText>(R.id.lowcharge_entry)
        var donate_button = findViewById<Button>(R.id.donate)
        var github_button = findViewById<Button>(R.id.github)

        // Set up listener for link buttons
        donate_button.setOnClickListener{
            goTo("https://github.com/Adri6336/buymeacoffee/blob/main/README.md")
        }

        github_button.setOnClickListener{
            goTo("https://github.com/Adri6336")
        }

        // Set up listener for button
        calculate_button.setOnClickListener{

            // To-do: convert the entries to their needed data types
            // and send to get_estimate function

            // 1. Get string content from text entries
            var width = width_entry.text.toString()
            var perline = perline_entry.text.toString()
            var pagecount = pagecount_entry.text.toString()
            var rate = rate_entry.text.toString()
            var mincharge = mincharge_entry.text.toString()

            // 2. Try to convert to proper datatype
            var output = findViewById<TextView>(R.id.estimated_text)  // Connect to output
            var success = true  // If this ever goes false, nothing goes to get_estimate fn

            // 2.1 Convert width, rate, and mincharge to float
            var width2 = 1.00f
            var perline2 = 1
            var pagecount2 = 1
            var rate2 = 0.75f
            var mincharge2 = 1.25f

            try{  // Do width ======
                if (width != ""){  // Only try this if the string is not empty
                    width2 = width.toFloat()
                }
                else {  // If empty, tell the user
                    output.text = "ERROR: Add value to 'Paper Width (in)'"
                    success = false  // If this triggers, don't send to function
                }

            } catch(e: ParseException){
                output.text = "ERROR: width conversion"
                success = false  // If this triggers, don't send to function

            }

            try{  // Do rate ======
                if (rate != ""){
                    rate2 = rate.toFloat()
                }
                else {
                    output.text = "ERROR: Add value to 'Rate'"
                    success = false
                }
            } catch(e: ParseException){
                output.text = "ERROR: rate conversion"
                success = false
            }

            try{  // Do min charge ======
                if (mincharge != ""){
                    mincharge2 = mincharge.toFloat()
                }
                else {
                    output.text = "ERROR: Add value to 'Min Charge'"
                    success = false
                }
            } catch(e: ParseException){
                output.text = "ERROR: mincharge conversion"
                success = false
            }

            // 2.2 Convert perline and pagecount to ints
            try{
                if (perline != ""){
                    perline2 = perline.toInt()
                }
                else {
                    output.text = "ERROR: Add value to 'Num Per Line' "
                    success = false
                }

            } catch(e: ParseException){
                output.text = "ERROR: perline conversion"
                success = false

            }

            try{
                if (pagecount != ""){
                    pagecount2 = pagecount.toInt()
                } else{
                    output.text = "ERROR: Add value to 'Page Count'"
                    success = false
                }

            } catch(e: ParseException){
                output.text = "ERROR: pagecount conversion"
                success = false

            }

            // 3. If conversion successful, send info to function
            if (success){
                get_estimate(width2, perline2, pagecount2, rate2, mincharge2)
                bling(width2, perline2, pagecount2)
            } else{
                bling(999999f, 999999, 999999)
            }

        }


    }
}
