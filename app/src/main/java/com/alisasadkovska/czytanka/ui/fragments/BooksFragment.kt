package com.alisasadkovska.czytanka.ui.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alisasadkovska.czytanka.R
import com.alisasadkovska.czytanka.common.Common
import com.alisasadkovska.czytanka.model.Book
import com.alisasadkovska.czytanka.ui.BookDetailActivity
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_books.*
import java.util.*


class BooksFragment : Fragment() {

    lateinit var booksRecycler: RecyclerView
    lateinit var ref: DatabaseReference
    lateinit var progressBar: ProgressBar

    private var mAdapter: FirebaseRecyclerAdapter<Book, MyViewHolder>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_books, container, false)

        ref = FirebaseDatabase.getInstance().reference.child(Common.BOOKS_REFERENCE)
        ref.keepSynced(true)

        booksRecycler = view.findViewById(R.id.recyclerBooks)

        if (context!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            booksRecycler.layoutManager = GridLayoutManager(context,3)
        else
            booksRecycler.layoutManager = GridLayoutManager(context,5)

        progressBar = view.findViewById(R.id.progressBar)


        initData()

        if (savedInstanceState!=null){
            val positionIndex = savedInstanceState.run { getInt(Common.RV_POS_INDEX) }
            mAdapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    (booksRecycler.layoutManager as GridLayoutManager).scrollToPositionWithOffset(positionIndex,0)
                }
            })
        }

        return view
    }


    private fun initData() {

        val option = FirebaseRecyclerOptions.Builder<Book>()
            .setQuery(ref, Book::class.java)
            .build()

         mAdapter = object : FirebaseRecyclerAdapter<Book,
                MyViewHolder>(option){

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Book) {

                ref.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        context?.let { Toasty.error(it, p0.message, Toasty.LENGTH_SHORT).show() }
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        progressBar.visibility = if (itemCount == 0)View.VISIBLE else View.GONE

                        Picasso.get().load(model.cover)
                            .error(R.drawable.ic_terrain_red_24dp)
                            .placeholder(R.drawable.progress_animation)
                            .into(holder.coverImg)

                        holder.bookItem.setOnClickListener {
                            val intent = Intent(context, BookDetailActivity::class.java)
                            intent.putExtra("title", model.title.toString())
                            intent.putExtra("age", model.age)
                            intent.putExtra("author", model.author)
                            intent.putExtra("cover", model.cover)
                            intent.putExtra("description", model.description)
                            intent.putExtra("downloadUrl", model.downloadUrl)
                            intent.putExtra("painter", model.painter)
                            startActivity(intent)
                        }
                    }
                })
            }
        }
        booksRecycler.adapter = mAdapter
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter!!.startListening()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val positionIndex = (booksRecycler.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
        outState.putInt(Common.RV_POS_INDEX, positionIndex)
    }

    override fun onDestroyView() {
        mAdapter!!.stopListening()
        super.onDestroyView()
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var coverImg: ImageView = itemView.findViewById(R.id.imgCover)
        internal var bookItem: RelativeLayout = itemView.findViewById(R.id.bookItem)
    }
}



