package com.example.nutrichief.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.example.nutrichief.R

class PaymentActivity : AppCompatActivity() {

    private lateinit var totalPaymentTV: TextView
    private lateinit var confirmPaymentBtn: Button
    private lateinit var paymentWalletBtn: Button

    private lateinit var cashPaymentRB: RadioButton
    private lateinit var walletsRB: RadioButton

    private lateinit var walletSection: LinearLayout

    private lateinit var allWalletsLL: LinearLayout

    var totalItemPrice = 0.0F
    var totalTaxPrice = 0.0F
    var subTotalPrice = 0.0F

    var takeAwayTime = ""

    private var selectedWallet = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        totalItemPrice = intent.getFloatExtra("totalItemPrice", 0.0F)
        totalTaxPrice = intent.getFloatExtra("totalTaxPrice", 0.0F)
        subTotalPrice = intent.getFloatExtra("subTotalPrice", 0.0F)

        takeAwayTime = intent?.getStringExtra("takeAwayTime").toString()

        totalPaymentTV = findViewById(R.id.total_payment_tv)
        totalPaymentTV.text = "\$%.2f".format(subTotalPrice)

        cashPaymentRB = findViewById(R.id.cash_payment_radio_btn)
        walletsRB = findViewById(R.id.wallets_radio_btn)

        walletSection = findViewById(R.id.wallets_section_ll)

        setupPaymentButtons()
        setupWallets()

        findViewById<ImageView>(R.id.payment_go_back_iv).setOnClickListener { onBackPressed() }
    }

    //set up 5 wallets
    private fun setupWallets() {
        allWalletsLL = findViewById(R.id.wallets_ll)
        allWalletsLL.getChildAt(0).setOnClickListener {setClickedWallet(0)}
        allWalletsLL.getChildAt(1).setOnClickListener {setClickedWallet(1)}
        allWalletsLL.getChildAt(2).setOnClickListener {setClickedWallet(2)}
        allWalletsLL.getChildAt(3).setOnClickListener {setClickedWallet(3)}
        allWalletsLL.getChildAt(4).setOnClickListener {setClickedWallet(4)}
        setClickedWallet(0) //by default selected wallet
    }

    //when click one wallet
    private fun setClickedWallet(selectedWalletPos: Int) {
        for(i in 0..4) {
            allWalletsLL.getChildAt(i).setBackgroundDrawable(resources.getDrawable(R.drawable.border_unselected_option))
        }
        allWalletsLL.getChildAt(selectedWalletPos).setBackgroundDrawable(resources.getDrawable(R.drawable.border_selected_option))

        when(selectedWalletPos) {
            0 -> selectedWallet = "Momo"
            1 -> selectedWallet = "ZaloPay"
            2 -> selectedWallet = "ShopeePay"
            3 -> selectedWallet = "VNPay"
            4 -> selectedWallet = "GrabPay"
        }
    }

    //payment button handler
    private fun setupPaymentButtons() {
        confirmPaymentBtn = findViewById(R.id.confirm_payment_btn)
        paymentWalletBtn = findViewById(R.id.payment_wallet_btn)

        paymentWalletBtn.text = "Pay Securely \$%.2f".format(subTotalPrice)

        confirmPaymentBtn.setOnClickListener { orderDone() }
        paymentWalletBtn.setOnClickListener { orderDone() }
    }

    //when user choose payment method: cash or wallets
    fun chooseModeOfPayment(view: View) {
        var payMethod = ""
        payMethod = if(view is RadioButton) {
            ((view.parent as LinearLayout).getChildAt(1) as TextView).text.toString()
        } else {
            (((view as CardView).getChildAt(0) as LinearLayout).getChildAt(1) as TextView).text.toString()
        }

        cashPaymentRB.isChecked = false
        walletsRB.isChecked = false

        walletSection.visibility = ViewGroup.GONE

        confirmPaymentBtn.visibility = ViewGroup.INVISIBLE

        when(payMethod) {
            getString(R.string.cash_payment) -> {
                cashPaymentRB.isChecked = true
                confirmPaymentBtn.visibility = ViewGroup.VISIBLE
            }
            getString(R.string.wallets) -> {
                walletsRB.isChecked = true
                walletSection.visibility = ViewGroup.VISIBLE
            }

        }
    }

    // start intent to open and put data to orderDone activity
    private fun orderDone() {
        var paymentMethod = ""
        when {
            cashPaymentRB.isChecked -> paymentMethod = "Pending: Cash Payment"
            walletsRB.isChecked -> paymentMethod = "Paid: $selectedWallet Wallet"
        }

        val intent = Intent(this, OrderDoneActivity::class.java)
        intent.putExtra("totalItemPrice", totalItemPrice)
        intent.putExtra("totalTaxPrice", totalTaxPrice)
        intent.putExtra("subTotalPrice", subTotalPrice)
        intent.putExtra("takeAwayTime", takeAwayTime)
        intent.putExtra("paymentMethod", paymentMethod)

        startActivity(intent)
        finish()
    }

    //on back pressed
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(R.drawable.ic_alert)
            .setTitle("Alert!")
            .setMessage("Do you want to cancel the payment?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                super.onBackPressed()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, _ ->
                dialogInterface.dismiss()
            })
            .create().show()
    }
}