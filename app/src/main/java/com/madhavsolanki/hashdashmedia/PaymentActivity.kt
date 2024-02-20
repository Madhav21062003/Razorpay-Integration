package com.madhavsolanki.hashdashmedia

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.graphics.Color

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.razorpay.*

import org.json.JSONObject
import java.lang.Exception

class PaymentActivity : AppCompatActivity(), PaymentResultListener {

    lateinit var editAmount: EditText

    lateinit var btnPay:Button

    lateinit var paymentStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        editAmount=findViewById(R.id.edit_amount)

        btnPay=findViewById(R.id.btn_pay)

        paymentStatus=findViewById(R.id.payment_status)


        btnPay.setOnClickListener {

            if (editAmount.text.toString().equals(""))
                return@setOnClickListener

            paymentProcess(editAmount.text.toString().trim().toInt())

        }


        Checkout.preload(this@PaymentActivity)


    }

    private fun paymentProcess(amount: Int) {

        val checkout=Checkout()


        checkout.setKeyID("YOUR_KEY_ID")

        try {


            val options=JSONObject()

            options.put("name","Madhav Solanki")

            options.put("description","Make a payment")

            options.put("image","https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color","#528FF0")

            options.put("currency","INR")

            options.put("amount",amount*100)

            val retryObj=JSONObject()


            retryObj.put("enabled",true)

            retryObj.put("max_count",4)


            options.put("retry",retryObj)



            checkout.open(this@PaymentActivity,options)



        }
        catch (e:Exception){

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

        }



    }


    @SuppressLint("SetTextI18n")
    override fun onPaymentSuccess(p0: String?) {


        paymentStatus.text="Payment Sucessful"

        paymentStatus.setTextColor(Color.GREEN)

        editAmount.text.clear()
        Toast.makeText(this@PaymentActivity,"Payment Successfull", Toast.LENGTH_LONG).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onPaymentError(p0: Int, p1: String?) {

        paymentStatus.setText("Payment Unsucessful")

        paymentStatus.setTextColor(Color.RED)

        editAmount.text.clear()
        Toast.makeText(this@PaymentActivity,"Payment Unsuccessfull", Toast.LENGTH_LONG).show()

    }

}
