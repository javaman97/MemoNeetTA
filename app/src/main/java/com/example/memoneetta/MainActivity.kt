package com.example.memoneetta

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.memoneetta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val channelId = "channelID"
    private val channelName="channelName"
    private val notificationId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            createNotificationChannel()
            binding.btnNotification.setOnClickListener {
                showNotification()
            }
        }

    private fun createNotificationChannel() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val descriptionText = "This Notification Channel Description"
                val channel = NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH).apply {
                    description = descriptionText
                    lightColor= Color.GREEN
                    enableLights(true)
                }
                val manager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(channel)
            }
        }

        private fun showNotification() {

            val deepLinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse("myapp://"))
            val pendingDeepLinkIntent = PendingIntent.getActivity(this, 0, deepLinkIntent,
                PendingIntent.FLAG_IMMUTABLE)

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("MEMONEET")
                .setContentText("Ace NEET 2024 By Memorizing every line of NCERT")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(
                    R.drawable.ic_launcher_foreground,
                    "Open Link",
                    pendingDeepLinkIntent
                )

            with(NotificationManagerCompat.from(this)) {
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this@MainActivity,"Please On the MemoNeet Notification",Toast.LENGTH_LONG).show()
                }
                notify(notificationId, builder.build())
            }
        }
}