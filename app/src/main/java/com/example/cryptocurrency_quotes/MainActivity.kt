package com.example.cryptocurrency_quotes

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val url =
        "https://min-api.cryptocompare.com/data/pricemulti?fsyms=SOL,LTC&tsyms=USD,EUR,BTC"
    private lateinit var cryptoTable: TableLayout
    private lateinit var refreshButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cryptoTable = findViewById(R.id.cryptoTable)
        refreshButton = findViewById(R.id.refreshButton)

        refreshButton.setOnClickListener {
            fetchCryptoQuotes()
        }
    }

    private fun fetchCryptoQuotes() {
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                displayCryptoQuotes(response)
            },
            { error ->
                Toast.makeText(applicationContext, "Error in receiving data!", Toast.LENGTH_SHORT)
                    .show()
                Log.i("mytag", "Error in receiving data")
            })
        queue.add(request)
    }

    private fun displayCryptoQuotes(quotes: JSONObject) {
        cryptoTable.removeAllViews()

        val tableHeader = TableRow(this)
        val currencyHeader = TextView(this)
        currencyHeader.text = "Currency"
        tableHeader.addView(currencyHeader)
        val dogeHeader = TextView(this)
        dogeHeader.text = "SOL"
        tableHeader.addView(dogeHeader)
        val ltcHeader = TextView(this)
        ltcHeader.text = "LTC"
        tableHeader.addView(ltcHeader)
        cryptoTable.addView(tableHeader)

        val currencies = arrayOf("USD", "EUR", "BTC")

        for (currency in currencies) {
            val row = TableRow(this)
            val currencyTextView = TextView(this)
            currencyTextView.text = currency
            row.addView(currencyTextView)

            val dogeValue = TextView(this)
            dogeValue.text = quotes.getJSONObject("SOL").getString(currency)
            row.addView(dogeValue)

            val ltcValue = TextView(this)
            ltcValue.text = quotes.getJSONObject("LTC").getString(currency)
            row.addView(ltcValue)

            cryptoTable.addView(row)
        }
    }
}

