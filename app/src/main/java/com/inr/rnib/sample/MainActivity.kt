package com.inr.rnib.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.inr.rnib.sample.extensions.hide
import com.inr.rnib.sample.extensions.show
import com.inr.rnib.sample.ui.RNIItemAdapter
import com.inr.rnib.sample.ui.ScopedActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ScopedActivity() {

    private var infoDialog: AlertDialog? = null
    private val rniItemAdapter = RNIItemAdapter()
    private val checkForRNI = CheckForRNIWorker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        resetView()
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { checkForRNI() }
        rniResultsRecycler.layoutManager = LinearLayoutManager(this)
        rniResultsRecycler.adapter = rniItemAdapter
    }

    private fun resetView() {
        progressView.max = 100
        progressView.beerProgress = 0
        progressView.show()
        isDRNITextView.hide()
        rniItemAdapter.clear()
    }

    private fun checkForRNI() {
        resetView()
        fab.hide()

        launch {
            val results = checkForRNI.invoke()
            animateResults(results)
        }
    }

    /**
     * There's probably a much easier way of doing this using View Property animators? :S
     */
    private fun animateResults(results: List<RNIItemResult>) {
        val isDRNI = results.any { it.result }
        // this allows us to increment the progress bar for x number of times for each of the results
        // all in the effort to smooth the animation
        val multiplier = 10
        progressView.max = results.size * multiplier

        launch {
            withContext(Dispatchers.IO) {
                results.forEachIndexed { index, rniItemResult ->
                    for (i in 1..multiplier) {
                        // arbitrary delay, 50 millis seems to look ok when testing with 12 results
                        delay(50)
                        // post the UI updates in the UI thread
                        withContext(Dispatchers.Main) {
                            progressView.beerProgress = progressView.beerProgress + 1

                            // only add to the once we hit the multiplier
                            if (i == multiplier) {
                                rniItemAdapter.add(rniItemResult)
                            }
                            //is it the end of the results
                            if (index == results.size - 1) {
                                onRNICheckFinished(isDRNI = isDRNI)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_github -> {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(GITHUB_LINK)
                startActivity(i)
                true
            }
            R.id.action_info -> {
                showInfoDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showInfoDialog() {
        //do nothing if already showing
        if (infoDialog?.isShowing != true) {
            infoDialog = AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage(R.string.info_details)
                .setCancelable(true)
                .setPositiveButton("ok") { dialog, _ -> dialog.dismiss() }
                .setNegativeButton("More info") { dialog, _ ->
                    dialog.dismiss()
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(GITHUB_LINK)
                        )
                    )
                }
                .create()
            infoDialog?.show()
        }
    }

    private fun onRNICheckFinished(isDRNI: Boolean) {
        fab.show()
        isDRNITextView.update(isDRNI = isDRNI)
        isDRNITextView.show()
    }

    companion object {
        private const val GITHUB_LINK = "https://github.com/inr/rnib"
    }
}

