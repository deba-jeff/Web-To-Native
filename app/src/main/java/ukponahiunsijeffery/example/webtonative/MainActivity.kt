package ukponahiunsijeffery.example.webtonative

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById<WebView>(R.id.webview)

        // Gets the WebSettings attached to the WebView
        val webSettings: WebSettings = webView.settings

        // Tells the WebView to enable JavaScript execution
        webSettings.javaScriptEnabled = true

        // Enables the CustomWebClient to determine how the Url is loaded
        webView.webViewClient = CustomWebClient(this)

        // Loads the Home webpage
        loadWebPage("https://www.webtonative.com/")

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.home -> {
                    // Loads the Home webpage
                    loadWebPage("https://www.webtonative.com")
                    true
                }
                R.id.showcase -> {
                    // Loads the Showcase webpage
                    loadWebPage("https://www.webtonative.com/showcase")
                    true
                }
                R.id.features -> {
                    // This loads the Features webpage
                    loadWebPage("https://www.webtonative.com/features")
                    true
                }
                R.id.faq -> {
                    // Loads the FAQ webpage
                    loadWebPage("https://www.webtonative.com/faq")
                    true
                }
                R.id.pricing -> {
                    // Loads the Pricing webpage
                    loadWebPage("https://www.webtonative.com/pricing")
                    true
                }

                else -> true
            }

        }

    }


    /**
     *  Helper function to load the webpage
     */
    private fun loadWebPage(url: String){
        webView.loadUrl(url)
    }


    /**
     *  Handles navigation when the back button is clicked
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        // Checks if the event was the back key and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && (this.webView.canGoBack())){

            // Navigates to the previous webpage
            this.webView.goBack()
            return true
        }

        else{

            // Checks if the backPressedTime plus 2 seconds is greater than the currentTime
            if (backPressedTime + 2000 > System.currentTimeMillis()){

                // Carry our the default system behaviour of the back key
                return super.onKeyDown(keyCode, event)
            }
            else{
                Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show()
            }

            backPressedTime = System.currentTimeMillis()
            return true
        }

    }


    /**
     *  Handles where the webpage is loaded
     */
    private class CustomWebClient(var activity: Activity): WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

            // Uses the Application WebView to load the webpage
            if (Uri.parse(url).host == "webtonative.com/" ){
                return false
            }

            // Uses the default App browser to load the webpage
            else{
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                activity.startActivity(intent)
                return true
            }
        }

    }


}
