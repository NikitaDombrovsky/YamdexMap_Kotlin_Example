package com.example.yamdemxmmapkt


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polygon
import com.yandex.mapkit.geometry.Polyline

import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.MapWindow
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManager
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SearchType
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.Session.SearchListener
import com.yandex.mapkit.search.SuggestSession
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.Error


class MainActivity : AppCompatActivity() {
    private lateinit var mapView: MapView

    private val placemarkTapListener = MapObjectTapListener { _, point ->
        Toast.makeText(
            this@MainActivity,
            "Tapped the point (${point.longitude}, ${point.latitude}) Верим?",
            Toast.LENGTH_SHORT
        ).show()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Инициализация должна быть до setContentView
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)

        mapView = findViewById(R.id.mapview)

        val map = mapView.mapWindow.map
        map.move(POSITION)

        val imageProvider = ImageProvider.fromBitmap(this.getBitmapFromVectorDrawable(R.drawable.test_pin))

        // Добавление метки с картинкой
        // Работают только декларативные методы и только с bitmap
        val placemark1 = mapView.map.mapObjects.addPlacemark(
            POINT,
            // Работает только с форматом bitmap
            ImageProvider.fromBitmap(this.getBitmapFromVectorDrawable(R.drawable.test_pin)));
        placemark1.addTapListener(placemarkTapListener)

        // Добавление нескольких меток
        val pinsCollection = mapView.map.mapObjects.addCollection()
        val points = listOf(
            Point(59.935493, 30.327392),
            Point(59.938185, 30.32808),
            Point(59.937376, 30.33621),
            Point(59.934517, 30.335059),
        )
        points.forEach { point ->
            pinsCollection.addPlacemark().apply {
                geometry = point
                setIcon(imageProvider)
            }

        }

        val polygon = Polygon(LinearRing(points), emptyList())
        val polygonMapObject = map.mapObjects.addPolygon(polygon)


        val polypoints = listOf(
            POINT,
            Point(59.938185, 30.32808),
            Point(59.937376, 30.33621),
            POINT2,
        )
        val polyline = Polyline(polypoints)

        val polylineObject = map.mapObjects.addPolyline(polyline)

        polylineObject.apply {
            strokeWidth = 5f
            setStrokeColor(ContextCompat.getColor(this@MainActivity, R.color.grey))
            outlineWidth = 1f
            outlineColor = ContextCompat.getColor(this@MainActivity, R.color.black)
        }

 /*       val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)

        val searchOptions = SearchOptions().apply {
            searchTypes = SearchType.BIZ.value
            resultPageSize = 32
            geometry
        }
        val suggestSession: SuggestSession = searchManager.createSuggestSession()
        val searchSessionListener = object : SearchListener {//SearchSessionListener
            override fun onSearchResponse(response: Response) {
               // map.move(response.collection.boundingBox.)
                map.cameraPosition(Geometry.fromBoundingBox(response.collection.boundingBox!!))
                // Handle search response.
            }

            override fun onSearchError(error: Error) {
                // Handle search error.
            }
        }
        val session = searchManager.submit(
            "where to eat",
            VisibleRegionUtils.toPolygon(map.visibleRegion),
            searchOptions,
            searchSessionListener,
        )*/





    }

    // Метод для конвертации drawable в bitmap
    fun Context.getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
        var drawable = ContextCompat.getDrawable(this, drawableId) ?: return null

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }

        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
/*    fun Search(map : Map){
        val searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        val searchOptions = SearchOptions().apply {
            searchTypes = SearchType.Biz
            resultPageSize = 32
        }

        val session = searchManager.submit(
            "where to eat",
            VisibleRegionUtils.toPolygon(map.visibleRegion),
            searchOptions,
            searchSessionListener,
        )
    }*/





    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {
        private val POINT = Point(55.751280, 37.629720)
        private val POINT2 = Point(59.935493, 30.327392)
        private val POSITION = CameraPosition(POINT2, 17.0f, 150.0f, 30.0f)
        private val POSITION2 = CameraPosition(POINT, 17.0f, 150.0f, 30.0f)
    }
}