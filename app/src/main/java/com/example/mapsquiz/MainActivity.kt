package com.example.mapsquiz;
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация OSMDroid
        Configuration.getInstance().load(applicationContext, getPreferences(MODE_PRIVATE))

        // Настройка MapView
        val map = findViewById<MapView>(R.id.map)
        map.tileProvider.tileSource = TileSourceFactory.MAPNIK
        map.controller.setZoom(9.0)

        // Установка центра карты
        val sydney = GeoPoint(-34.0, 151.0)
        val moscow = GeoPoint(55.755814, 37.617635)
        val paris = GeoPoint(48.856651, 2.351691)
        addMarker(map, paris, "Париж")
        addMarker(map, sydney, "Сидней")
        addMarker(map, moscow, "Москва")
        map.controller.setCenter(moscow)
    }
    private fun addMarker(map : MapView, geoPoint: GeoPoint, title: String) {
        val marker = Marker(map)
        marker.position = geoPoint
        marker.title = title
        map.overlays.add(marker)
        marker.setAnchor(0.5f, 0.5f)
        marker.icon = ContextCompat.getDrawable(this, R.drawable.point_black)


    }

    private fun handleMarkerClick(marker: Marker) {
        // Получаем заголовок маркера
        val markerTitle = marker.title
        if (marker.icon != null && marker.icon.constantState == ContextCompat.getDrawable(this, R.drawable.point_red)?.constantState) {

        }
        else {
            // Создаем Intent для перехода на новую активность (QuizActivity)
            val intent = Intent(this, QuizActivity::class.java)
            // Передаем данные (например, заголовок маркера) в новую активность
            intent.putExtra("marker_title", markerTitle)
            marker.icon = ContextCompat.getDrawable(this, R.drawable.point_red)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        // Обновление карты при возобновлении активности
        val map = findViewById<MapView>(R.id.map)
        map.onResume()
        map.overlays.forEach { overlay ->
            if (overlay is Marker) {
                overlay.setOnMarkerClickListener { _, _ ->
                    handleMarkerClick(overlay)
                    true // Возвращаем true, чтобы сообщить, что событие обработано
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Приостановка работы карты при приостановке активности
        val map = findViewById<MapView>(R.id.map)
        map.onPause()
    }

}
