package com.example.my_history;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

public class MainActivity extends AppCompatActivity {

    private Button btnDream, btnPath, btnCity, btnFinal;
    private ImageView imageDream, imagePath, imageCity, imageFinal;
    private ConstraintLayout layoutDream;

    private NotificationManager notificationManager;
    private static final int NOTIFY_ID = 1;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDream = findViewById(R.id.btnDream);
        btnPath = findViewById(R.id.btnPath);
        btnCity = findViewById(R.id.btnCity);
        btnFinal = findViewById(R.id.btnFinal);

        imageDream = findViewById(R.id.imageDream);
        imagePath = findViewById(R.id.imagePath);
        imageCity = findViewById(R.id.imageCity);
        imageFinal = findViewById(R.id.imageFinal);

        layoutDream = findViewById(R.id.layoutDream);

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }
    }

    private void hideAll() {
        layoutDream.setVisibility(View.INVISIBLE);
        imagePath.setVisibility(View.INVISIBLE);
        imageCity.setVisibility(View.INVISIBLE);
        imageFinal.setVisibility(View.INVISIBLE);
    }


    public void btnDreamClick(View view) {
        hideAll();
        layoutDream.setVisibility(View.VISIBLE);
        showNotification(R.drawable.ic5, "Мечта", "Свидание на луне");
    }

    public void btnPathClick(View view) {
        hideAll();
        imagePath.setVisibility(View.VISIBLE);
        showNotification(R.drawable.ic6, "Дэвид", "Этот чум думал, что он особенный");
    }

    public void btnCityClick(View view) {
        hideAll();
        imageCity.setVisibility(View.VISIBLE);
        showNotification(R.drawable.ic7, "Это край", "Назад пути нет");
    }

    public void btnFinalClick(View view) {
        hideAll();
        imageFinal.setVisibility(View.VISIBLE);
        showNotification(R.drawable.ic8, "Финал", "Ты не стал легендой");
    }

    private void showNotification(int iconRes, String title, String text) {
        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setAutoCancel(true)
                        .setSmallIcon(iconRes)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(PRIORITY_HIGH);

        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    public static void createChannelIfNeeded(NotificationManager manager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
    }


    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public void onDavidClick(View view) {
        showToast("Дэвид Мартинес");
    }

    public void onLucyClick(View view) {
        showToast("Люси");
    }

    public void onMoonClick(View view) {
        showToast("Земля");
    }
}