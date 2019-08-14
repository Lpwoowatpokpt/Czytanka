package com.alisasadkovska.czytanka.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alisasadkovska.czytanka.R
import com.alisasadkovska.czytanka.common.Common
import com.alisasadkovska.czytanka.common.Utils
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.content_book_detail.*
import pub.devrel.easypermissions.EasyPermissions

class BookDetailActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Toasty.warning(this, getString(R.string.denied), 5).show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
       //create foledr for downloads

    }




    private val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        setSupportActionBar(toolbar)

        askForPermissions()

        val bundle :Bundle ?=intent.extras
        val title = bundle!!.getString("title")
        val cover = bundle.getString("cover")
        val author = bundle.getString("author")
        val age = bundle.getString("age")
        val painter = bundle.getString("painter")
        val description = bundle.getString("description")
        val downloadUrl = bundle.getString("downloadUrl")

        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        Picasso.get()
            .load(cover)
            .error(R.drawable.ic_terrain_red_24dp)
            .placeholder(R.drawable.progress_animation)
            .into(imgCover)

        txtAuthor.text = getString(R.string.author) + " " + author
        txtPainer.text = getString(R.string.painter) + " " + painter
        txtAge.text = age
        txtDescription.text = description


        fabUpload.setOnClickListener { view ->
             Snackbar.make(view, title!!, Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.upload)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if (EasyPermissions.hasPermissions(this, *perms)){
                            //permissions granted
                            uploadBook(downloadUrl)
                        }else{
                            //ask for permissions
                            askForPermissions()
                        }
                    }else{
                        //less then marshmallow, perms not required
                        uploadBook(downloadUrl)
                    }
                }
                .setActionTextColor(resources.getColor(R.color.actionColor))
                .show()
        }
    }

    private fun uploadBook(downloadUrl: String?) {

    }

    private fun askForPermissions() {
        if(!EasyPermissions.hasPermissions(this, *perms)){
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_permissions), 0,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
