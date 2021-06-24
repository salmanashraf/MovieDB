package com.news.myapplication.ui.movies.adapters.viewHolders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val context: Context = itemView.context

    protected fun getContext(): Context {
        return context
    }
}