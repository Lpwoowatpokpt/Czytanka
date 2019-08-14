package com.alisasadkovska.czytanka.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.alisasadkovska.czytanka.R
import com.alisasadkovska.czytanka.ui.fragments.DownloadsFragment
import com.alisasadkovska.czytanka.ui.fragments.BooksFragment
import com.alisasadkovska.czytanka.ui.fragments.PopularFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> {
                return BooksFragment()
            }
            1 ->{
                return PopularFragment()
            }
            2 ->{
                return DownloadsFragment()
            }else -> return null
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {

        when(position){
            0 -> return context.getString(R.string.title_books)
            1 -> return context.getString(R.string.title_popular)
            2 -> return context.getString(R.string.title_downloads)
        }
        return null
    }

    override fun getCount(): Int {
        return 3
    }
}