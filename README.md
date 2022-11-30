# BioPass ID Face Capture SDK Demo

```Kotlin
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import com.biopassid.facesdk.Face
import com.biopassid.facesdk.FaceCallback
import com.biopassid.facesdk.FaceConfigPreset
import com.biopassid.facesdk.enums.FaceConfigType
import com.example.facesdkdemo.model.EnrollPersonRequest
import com.example.facesdkdemo.model.EnrollPersonResponse
import com.example.facesdkdemo.model.FacePersonRequest
import com.example.facesdkdemo.model.PersonRequest
import com.example.facesdkdemo.network.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    private val TAG = "FaceDemo"
    private lateinit var btnFaceCapture: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnFaceCapture = findViewById(R.id.btnFaceCapture)

        // Instantiate Face config by passing your license key
        val config = FaceConfigPreset.getConfig(FaceConfigType.FACE_CAPTURE)
            .setLicenseKey("your-license-key")

        // Handle Face callback
        val callback = FaceCallback { event ->
            // Encode Bitmap to base64 string
            val imageData = bitmapToBas64(event.photo)

            // Instantiate Enroll request
            val enrollPersonRequest =
                EnrollPersonRequest(
                    PersonRequest(
                        "your-customID",
                        listOf(FacePersonRequest(imageData))
                    )
                )

            // Get retrofit
            val retrofit = Network.getRetrofitInstance()

            // Execute request to the BioPass ID API
            val callback = retrofit.enrollPerson(enrollPersonRequest)

            // Handle API response
            callback.enqueue(object : Callback<EnrollPersonResponse> {
                override fun onFailure(call: Call<EnrollPersonResponse>, t: Throwable) {
                    Log.e(TAG, "Error trying to call enroll person. ${t.message}")
                }

                override fun onResponse(
                    call: Call<EnrollPersonResponse>,
                    response: Response<EnrollPersonResponse>
                ) {
                    Log.d(TAG, "EnrollPersonResponse: ${response.body()}")
                }
            })
        }

        // Build Face camera view
        btnFaceCapture.setOnClickListener {
            Face.buildCameraView(this, config, callback)
        }
    }

    private fun bitmapToBas64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        stream.close()
        return Base64.encodeToString(byteArray, Base64.NO_WRAP)
    }
}
```
