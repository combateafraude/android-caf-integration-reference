package io.caf.sdk.integration.reference

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.combateafraude.documentdetector.DocumentDetectorActivity
import com.combateafraude.documentdetector.input.CafStage
import com.combateafraude.documentdetector.input.Document
import com.combateafraude.documentdetector.input.DocumentDetector
import com.combateafraude.documentdetector.input.DocumentDetectorStep
import com.combateafraude.documentdetector.output.DocumentDetectorResult
import io.caf.sdk.caffaceliveness.CafFaceLivenessError
import io.caf.sdk.caffaceliveness.CafFaceLivenessResult
import io.caf.sdk.caffaceliveness.CafFaceLivenessSDK
import io.caf.sdk.caffaceliveness.CafFaceLivenessStage
import io.caf.sdk.caffaceliveness.CafLivenessListener

/**
 * The main activity of the application that manages interactions with the Caf Face Liveness SDK and Document Detector SDK.
 *
 * @constructor Creates a new instance of [MainActivity].
 */
class MainActivity : AppCompatActivity() {

    /**
     * Instance of the Caf Face Liveness SDK, initialized lazily.
     *
     * The SDK is configured through the [setupCafFaceLiveness] function.
     */
    private val cafFaceLivenessSDK: CafFaceLivenessSDK by lazy { setupCafFaceLiveness() }

    /**
     * Instance of the Caf Document Detector, initialized lazily.
     *
     * The detector is configured through the [setupCafDocumentDetector] function.
     */
    private val cafDocumentDetectorSdk: DocumentDetector by lazy { setupCafDocumentDetector() }

    /**
     * Reference to the button that starts the Caf Face Liveness SDK, found by the ID `btCafFaceliveness`.
     */
    private val btCafFaceLiveness by lazy { findViewById<Button>(R.id.btCafFaceliveness) }

    /**
     * Reference to the button that starts the Document Detector, found by the ID `btCafDocumentDetector`.
     */
    private val btCafDocumentDetector by lazy { findViewById<Button>(R.id.btCafDocumentDetector) }

    /**
     * Called when the activity is created.
     *
     * Sets up the user interface, adjusts padding based on window insets, and defines listeners for the buttons.
     *
     * @param savedInstanceState The saved state of the previous instance, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Define the listener for the Caf Face Liveness button
        btCafFaceLiveness.setOnClickListener {
            cafFaceLivenessSDK.startSDK(
                this,
                MOBILE_TOKEN,
                PERSON_ID
            )
        }

        // Define the listener for the Caf Document Detector button
        btCafDocumentDetector.setOnClickListener {
            val mIntent = Intent(this, DocumentDetectorActivity::class.java)
            mIntent.putExtra(DocumentDetector.PARAMETER_NAME, cafDocumentDetectorSdk)
            startActivityForResult(mIntent, REQUEST_CODE)
        }
    }

    /**
     * Configures and returns an instance of [CafFaceLivenessSDK].
     *
     * @return Configured instance of [CafFaceLivenessSDK].
     */
    private fun setupCafFaceLiveness(): CafFaceLivenessSDK {
        return CafFaceLivenessSDK.CafBuilder()
            .setStage(CafFaceLivenessStage.PROD)
            .setScreenCaptureEnabled(true)
            .setLoadingScreen(true)
            .setListener(
                setupCafLivenessListener()
            ).build()
    }

    /**
     * Configures and returns an instance of [DocumentDetector].
     *
     * @return Configured instance of [DocumentDetector].
     */
    private fun setupCafDocumentDetector(): DocumentDetector {
        return DocumentDetector.Builder(MOBILE_TOKEN)
            .setDocumentCaptureFlow(
                arrayOf(
                    DocumentDetectorStep(Document.RG_FRONT),
                    DocumentDetectorStep(Document.RG_BACK)
                )
            )
            .setPersonId(PERSON_ID)
            .setStage(CafStage.PROD)
            .setUseDeveloperMode(true)
            .setUseAdb(true)
            .setUseDebug(true)
            .build()
    }

    /**
     * Configures and returns a listener for events from the Caf Face Liveness SDK.
     *
     * @return Configured instance of [CafLivenessListener].
     */
    private fun setupCafLivenessListener() = object : CafLivenessListener {
        /**
         * Called when the liveness operation is canceled by the user.
         */
        override fun onCancel() {
            log("onCancel")
        }

        /**
         * Called when an error occurs in the Caf Face Liveness SDK.
         *
         * @param sdkFailure Details of the error that occurred.
         */
        override fun onError(sdkFailure: CafFaceLivenessError) {
            log("onError: [${sdkFailure.message}][${sdkFailure.type.errorCode}]")
        }

        /**
         * Called when the Caf Face Liveness SDK is loaded.
         *
         * **Note:** This event is only called when the loading screen is not enabled.
         */
        override fun onLoaded() {
            log("onLoaded")
        }

        /**
         * Called when the Caf Face Liveness SDK is loading.
         *
         * **Note:** This event is only called when the loading screen is not enabled.
         */
        override fun onLoading() {
            log("onLoading")
        }

        /**
         * Called when the liveness operation is successful.
         *
         * @param livenessResult Result of the liveness operation.
         */
        override fun onSuccess(livenessResult: CafFaceLivenessResult) {
            log("onSuccess: ${livenessResult.signedResponse}")
        }
    }

    /**
     * Handles results returned by activities started for a result.
     *
     * @param requestCode The request code with which the activity was started.
     * @param resultCode The result code returned by the activity.
     * @param data Additional data returned by the activity.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                val mDocumentDetectorResult =
                    data.getSerializableExtra(DocumentDetectorResult.PARAMETER_NAME) as DocumentDetectorResult?
                if (mDocumentDetectorResult != null) {
                    if (mDocumentDetectorResult.wasSuccessful()) {
                        log("DD - SUCCESS")
                    } else {
                        log("DD - ERROR")
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Displays a log message in the console and a Toast in the user interface.
     *
     * @param message The message to be displayed.
     */
    private fun log(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Log.d("SDK_IMPL", message)
    }

    /**
     * Companion object containing constants used within the [MainActivity] class.
     */
    private companion object {
        /** Request code used to start activities for a result. */
        const val REQUEST_CODE = 1000

        /** Identifier for the person used in the SDKs. */
        const val PERSON_ID = "..."

        /** Mobile token used for authentication in the SDKs. */
        const val MOBILE_TOKEN = "..."
    }
}
