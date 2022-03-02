package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var b:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)


        var arr = setOf<Char>('1','2','3','4','5','6','7','8','9','0')
        var operations = setOf<Char>('+','-','/','*')
        b.btn0.setOnClickListener{
//            if(b.operation.text.isNotEmpty() and !operations.contains(b.operation.text.last())){
//                setText("0")
//            }
            if(b.operation.text.isNotEmpty()){
                setText("0")
            }
        }
        b.btn1.setOnClickListener{ setText("1") }
        b.btn2.setOnClickListener{ setText("2") }
        b.btn3.setOnClickListener{ setText("3") }
        b.btn4.setOnClickListener{ setText("4") }
        b.btn5.setOnClickListener{ setText("5") }
        b.btn6.setOnClickListener{ setText("6") }
        b.btn7.setOnClickListener{ setText("7") }
        b.btn8.setOnClickListener{ setText("8") }
        b.btn9.setOnClickListener{ setText("9") }

        b.btnMinus.setOnClickListener{
            if(!(b.operation.text.endsWith("+") or
                b.operation.text.endsWith("*") or
                b.operation.text.endsWith("/") or
                b.operation.text.endsWith("-")) and b.operation.text.isNotEmpty()){
                    setText("-")
                }
        }
        b.btnPlus.setOnClickListener{
            if(!(b.operation.text.endsWith("-") or
                b.operation.text.endsWith("*") or
                b.operation.text.endsWith("/") or
                b.operation.text.endsWith("+")) and b.operation.text.isNotEmpty()){
                    setText("+")
            }
        }
        b.btnDivide.setOnClickListener{
            if(!(b.operation.text.endsWith("+") or
                 b.operation.text.endsWith("*") or
                 b.operation.text.endsWith("-") or
                 b.operation.text.endsWith("/")) and b.operation.text.isNotEmpty()){
                    setText("/")
            }
        }
        b.btnMultiply.setOnClickListener{
            if(!(b.operation.text.endsWith("+") or
                 b.operation.text.endsWith("-") or
                 b.operation.text.endsWith("/") or
                 b.operation.text.endsWith("*")) and b.operation.text.isNotEmpty()){
                    setText("*")
            }
        }
        b.btnRight.setOnClickListener{
            if(b.operation.text.isNotEmpty() and !b.operation.text.endsWith("(")){
                setText(")")
            }
        }

        b.btnLeft.setOnClickListener{
            if(b.operation.text.isEmpty()){
                setText("(")
            }else{
                if(!b.operation.text.endsWith(")")
                    and !arr.contains(b.operation.text.last())){
                     setText("(")
                }
            }
        }

        b.btnDelete.setOnClickListener{
            val str = b.operation.text.toString()
            if(str.isNotEmpty()){
                b.operation.text = str.substring(0, str.length - 1)
            }
            b.result.text = ""
        }

        b.btnAll.setOnClickListener{
            b.operation.text = ""
            b.result.text = ""
        }

        b.btnEqual.setOnClickListener{
            try{
                val exp = ExpressionBuilder(b.operation.text.toString()).build()
                val res = exp.evaluate()

                val longRes = res.toLong()
                if(res == longRes.toDouble()){
                    b.result.text = longRes.toString()
                }else{
                    b.result.text = res.toString()
                }

            }catch (e:Exception){
                Log.d("Error", "Message: ${e.message}")
            }
        }
    }

    fun setText(str: String){
        if(b.result.text != ""){
            b.operation.text = b.result.text
            b.result.text = ""
        }
        b.operation.append(str)
    }
}