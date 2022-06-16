package com.yash.geet.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.yash.geet.R
import com.yash.geet.helper.KeyClass
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(permissionGrantedResponse: PermissionGrantedResponse?) {
                    val mySongs : ArrayList<File> = fetchSongs(Environment.getExternalStorageDirectory())
                    val items = arrayOfNulls<String>(mySongs.size)
                    for (i in 0 until mySongs.size){
                        items[i] = mySongs[i].name.replace(".mp3","")
                    }

                    val adapter = ArrayAdapter(this@MainActivity,android.R.layout.simple_list_item_1,items)
                    listView.adapter = adapter
                    listView.onItemClickListener = OnItemClickListener{ parent,view,position, id ->
                        val i = Intent(this@MainActivity,PlaySongActivity::class.java)
                        val currentSong = listView.getItemAtPosition(position).toString()
                        i.putExtra(KeyClass.KEY_SONG_LIST,mySongs)
                        i.putExtra(KeyClass.KEY_CURRENT_SONG, currentSong)
                        i.putExtra(KeyClass.KEY_POSITION, position)
                        startActivity(i)
                    }
                }
                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {}
                override fun onPermissionRationaleShouldBeShown(permissionRequest: PermissionRequest?, permissionToken: PermissionToken?) {
                    permissionToken!!.continuePermissionRequest()
                }
            }).check()

    }

    fun fetchSongs(file : File?) : ArrayList<File>{
        val arrayList = ArrayList<File>()
        val songs = file!!.listFiles()
        if (songs != null){
            for (myFile in songs){
                if (!myFile.isHidden && myFile.isDirectory){
                    arrayList.addAll(fetchSongs(myFile))
                }
                else{
                    if (myFile.name.endsWith(".mp3") && !myFile.name.startsWith(".")){
                        arrayList.add(myFile)
                    }
                }
            }
        }
        return arrayList
    }

//    fun fetchSongs(file: File): ArrayList<File?>? {
//        val arrayList = ArrayList<Any>()
//        val songs = file.listFiles()
//        if (songs != null) {
//            for (myFile in songs) {
//                if (!myFile.isHidden && myFile.isDirectory) {
//                    arrayList.addAll(fetchSongs(myFile))
//                } else {
//                    if (myFile.name.endsWith(".mp3") && !myFile.name.startsWith(".")) {
//                        arrayList.add(myFile)
//                    }
//                }
//            }
//        }
//        return arrayList
//    }

}