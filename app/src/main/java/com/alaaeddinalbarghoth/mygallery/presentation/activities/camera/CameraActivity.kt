package com.alaaeddinalbarghoth.mygallery.presentation.activities.camera

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem
import com.alaaeddinalbarghoth.mygallery.databinding.ActivityCameraBinding
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.MainActivity
import com.alaaeddinalbarghoth.mygallery.presentation.dialogs.AddFeedDialog
import com.alaaeddinalbarghoth.mygallery.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraActivity : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private val viewModel: CameraViewModel by viewModels()
    private lateinit var binding: ActivityCameraBinding

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    // region Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
    // endregion

    // region Helper
    private fun init() {
        if (allPermissionsGranted()) {
            startCamera()
        } else ActivityCompat.requestPermissions(
            this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
        )

        setupListeners()
        setupCameraUtils()
    }

    private fun setupListeners() {
        binding.btnCameraCapture.setOnClickListener {
            takePhoto()
        }
    }

    /* region Permission */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted())
                startCamera()
            else {
                toast("Permissions not granted by the user.")
            }
        }
    }
    // endregion

    /* region Camera */
    private fun setupCameraUtils() {
        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            imageCapture = ImageCapture.Builder()
                .build()

            try {
                cameraProvider.unbindAll()// Unbind use cases before rebinding
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA, // Select back camera as a default
                    Preview.Builder()
                        .build()
                        .also {
                            it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                        },
                    imageCapture
                )// Bind use cases to camera
            } catch (exc: Exception) {
                Log.e(CameraActivity::class.java.simpleName, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun takePhoto() {

        binding.layoutCapturing.visibility = View.VISIBLE

        Looper.myLooper()?.let {
            Handler(it).postDelayed({
                binding.layoutCapturing.visibility = View.GONE
            }, 10)
        }

        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(
                        CameraActivity::class.java.simpleName,
                        "Photo capture failed: ${exc.message}",
                        exc
                    )
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    Uri.fromFile(photoFile).let { savedUri ->

                        AddFeedDialog { isAdded, title, description ->
                            if (isAdded) {

                                viewModel.addFeedItem(
                                    FeedItem(
                                        title = title,
                                        description = description,
                                        imageUri = savedUri.path.orEmpty()
                                    )
                                )

                                showLocalNotification()
                                finish()

                            } else {
                                savedUri.path?.let { path ->
                                    File(path).apply {
                                        if (this.delete())
                                            finish()
                                        else
                                            toast("Error Deleting File")
                                    }
                                }
                            }
                        }.show(
                            this@CameraActivity.supportFragmentManager,
                            AddFeedDialog::class.java.simpleName
                        )
                    }
                }
            })
    }
    // endregion

    // region Notification
    private fun showLocalNotification() {

        val contentIntent =
            PendingIntent.getActivity(
                this,
                0,
                Intent(this, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        val notificationManager =
            this.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    getString(R.string.channel_id),
                    getString(R.string.channel_title),
                    NotificationManager.IMPORTANCE_HIGH
                )
            )

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, getString(R.string.channel_id))

        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setContentTitle(getString(R.string.app_name))
            .setContentText("Photo capture succeeded")
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.ic_launcher_foreground)

        notificationManager.notify(1, notificationBuilder.build())
    }
    // endregion

    // endregion

}