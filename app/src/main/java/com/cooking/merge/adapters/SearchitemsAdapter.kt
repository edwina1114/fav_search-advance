package com.cooking.merge.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.cooking.merge.R
import com.cooking.merge.SearchDetailsActivity
import com.cooking.merge.models.HotitemsModel
import kotlinx.android.synthetic.main.cardview_layout.view.*
import kotlinx.android.synthetic.main.cardview_layout.view.title
import kotlinx.android.synthetic.main.cardview_layout_search.view.*
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchitemsAdapter(private var searchList: ArrayList<HotitemsModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    //此處加上Filterable的extensions就可以自行定義filter（getFilter()）

    //var searchList = ArrayList<String>()

    lateinit var mcontext: Context

//    init {
//        searchList = itemList
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemListView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_layout_search, parent, false)
        val source = ListHolder(itemListView)
        mcontext = parent.context
        return source
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.IV_hotlist.setImageResource(searchList[position].hot_image)
        holder.itemView.TV_hotlist.text = searchList[position].hot_title
        holder.itemView.TV_hotlist.setTextColor(Color.BLACK)

        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, SearchDetailsActivity::class.java)
            intent.putExtra("passhot", searchList[position].hot_image)
            mcontext.startActivity(intent)
            Log.d("Selected:", searchList[position].hot_title)
        }
    }

   /* override fun onBindViewHolder(holder: ListHolder, position: Int) {

        holder.sear.setTextColor(Color.BLACK)
        holder.itemView.title.text = searchList[position].hot_title


        holder.itemView.setOnClickListener {
            val intent = Intent(mcontext, SearchDetailsActivity::class.java)
            intent.putExtra("passhot", searchList[position].hot_image)
            mcontext.startActivity(intent)
            Log.d("Selected:", searchList[position].hot_title)
        }
    }*/

    inner class ListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var searchIV: ImageView = itemView.IV_hotlist
        var searchTV: TextView = itemView.TV_hotlist

    }


    override fun getFilter(): Filter {  //使用getFilter()時有兩個必須有的method: performFiltering 及 publishResults
        return object : Filter() {

            //The performFiltering method checks if we have typed a text in the SearchView.
            //If there is not any text, will return all items.
            //If there is a text, then we check if the characters match the items from the list and return the results in a FilterResults type.

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                } else {
                    val resultList = ArrayList<HotitemsModel>()
                    for (row in  ArrayList<HotitemsModel>()) {
                        if (row.hot_title.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    searchList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            //The publishResults get these results, passes it to the countryFilterList array and updates the RecyclerView.
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                searchList = results?.values as ArrayList<HotitemsModel>
                notifyDataSetChanged()
            }

        }
    }



}