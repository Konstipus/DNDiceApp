package com.example.dndiceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dndiceapp.ui.theme.DNDiceAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DNDiceAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceApp()
                }
            }
        }
    }
}

@Composable
fun DiceApp(){
    //list of dice added for rolling
    var diceList by remember{mutableStateOf(listOf<Int>())}
    var dieButtonList by remember {mutableStateOf(listOf<Int>())}

    //this is the result of separate dices' roll
    var rollResult by remember{mutableStateOf<List<Int>>(emptyList())}
    var total by remember{ mutableStateOf<Int>(0)}

    /*TODO:
        1. Turn the rolled dice into buttons, so they can be removed from the added dice list
           XX done XX
        2. Make the lists visually appealing more. Figure out how to remove the  [ ] brackets
        3. Holding button makes the added die to be rolled with advantage.
        4. Coloring and background
    */

    fun rollDice(){
        var totalRollSum = 0
        for (rolledDie in diceList){
            //define each die
            val roll = Die(rolledDie.toByte())
            val rolledDieValue = roll.roll()
            totalRollSum += rolledDieValue
            //rollResult needs to be replaced because separate index update in lists cannot be 'remembered'
            rollResult = rollResult + rolledDieValue

        }
        total = totalRollSum.toInt()


    }



    /* TODO:
    *   figure out how to call this addDieButton via add die*/

    fun addDieButton(dieSides:Int){
        dieButtonList = dieButtonList + dieSides

    }

    fun addDie(dieSides:Int){
        diceList = diceList + dieSides
        addDieButton(dieSides)
    }



    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row{

            Text(text = "Rolled dice: \n $diceList",
                fontSize = 25.sp,
                textAlign = TextAlign.Center)

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row{
            Text(text = "Result: \n $rollResult",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }
       Row{

            Text(text = "Totalling in: \n $total",
                fontSize = 25.sp,
                textAlign = TextAlign.Center)

        }
        Spacer(modifier = Modifier.height(16.dp))

        /*lambda function: Anonymous function that needs not to be defined with 'fun' etc.
       forEachIndexed expects a function to run in curly thingies, instead we pass lambda
       to only store local variables 'index' and 'dieSides' for each of the buttons

      IMPORTANT: "->" separates list of parameters from the function body

        */
        Row{
            dieButtonList.forEachIndexed{ index , dieSides ->
                Button(onClick = { dieButtonList = dieButtonList - dieSides
                                    diceList = diceList - dieSides },
                    modifier = Modifier
                        .size(40.dp)
                        ,
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(1.dp)




                       ){
                    Text(text = "d$dieSides",
                        fontSize = 16.sp,
                        maxLines = 1,
                        textAlign = TextAlign.Center,

                        modifier = Modifier.fillMaxWidth()
                    )

                }
            }


        }
        Spacer(modifier = Modifier.height(160.dp))

        Text("Pick Your dice:",
            fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        //First Dice Row
        Row {
            Button(onClick = { addDie(4) }) {
                Text(text = "d4",)

            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { addDie(6) }) {
                Text(text = "d6")

            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { addDie(8)}) {
                Text(text = "d8")

            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        //Second Dice Row
        Row {
            Button(onClick = { addDie(10) }) {
                Text(text = "d10")

            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { addDie(12) }) {
                Text(text = "d12")

            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { addDie(20) }) {
                Text(text = "d20")

            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { addDie(100) }) {
                Text(text = "d100")

            }

        }
        Spacer(modifier = Modifier.height(30.dp))
        //Added Dice

        Spacer(modifier = Modifier.height(30.dp))
        //RollDice
        Row{

            Button(onClick = {
                rollResult = emptyList()
                rollDice()},
                modifier = Modifier
                    .size(100.dp)
                    .requiredWidth(250.dp),
                contentPadding = PaddingValues(0.dp)

                ) {
                Text(text = "Roll Dice!",
                    textAlign = TextAlign.Center,
                    fontSize = 45.sp,
                    modifier = Modifier.fillMaxWidth())

            }


        }
        }

    }
@Composable
fun dieButton(dieSides: Int): @Composable () -> Unit {
    return{
    Button(
        onClick = {/*TODO:*/ }

    ){
        Text(text = "d$dieSides")
    }

    }
}


@Preview(showBackground = true)
@Composable
fun DiceAppPreview(){
    DiceApp()

}


