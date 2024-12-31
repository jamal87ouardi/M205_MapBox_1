package com.example.m204_mapbox_1

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.Plugin
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialisation de Mapbox
        mapView = findViewById(R.id.mapView)

        // Chargement du style de la carte
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS) {
            // Localisation de l'école avec latitude et longitude
            val latitude = 32.9237  // Exemple de latitude (Los Angeles)
            val longitude = -6.8695  // Exemple de longitude (Los Angeles)

            // Charger une image personnalisée depuis les ressources
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.pin) // Remplacez par le nom de votre image
            val scaledIconBitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false)
            // Créer l'icône à partir du bitmap
            //val icon = Icon.fromBitmap(bitmap)


            // Créer l'option pour le symbole (marqueur)
            val pointAnnotationManager = mapView.annotations.createPointAnnotationManager()
            val pointAnnotationOptions = PointAnnotationOptions()
                .withPoint(Point.fromLngLat(longitude, latitude)) // Position du marqueur
                .withIconImage(scaledIconBitmap)// Vous pouvez ajouter une icône personnalisée si vous avez une image

            // Ajouter le marqueur à la carte
            pointAnnotationManager.create(pointAnnotationOptions)

            // Ajuster la caméra pour zoomer sur la localisation
            val cameraOptions = CameraOptions.Builder()
                .center(Point.fromLngLat(longitude, latitude)) // Centrer sur la localisation
                .zoom(13.0) // Définir un niveau de zoom
                .build()

            // Appliquer la caméra
            mapView.getMapboxMap().setCamera(cameraOptions)
        }


    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }




}