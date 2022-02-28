package com.example.netflixappapi.ui.series

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.netflixappapi.R
import com.example.netflixappapi.data.model.History
import com.example.netflixappapi.data.viewmodel.HistoryViewModel
import com.example.netflixappapi.databinding.FragmentSeriesBinding

class SeriesFragment : Fragment() {

    private lateinit var seriesViewModel: SeriesViewModel
    private lateinit var mHistoryViewModel: HistoryViewModel
    private lateinit var root: View
    private lateinit var searchBar: AutoCompleteTextView

    private var _binding: FragmentSeriesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        seriesViewModel =
                ViewModelProvider(this).get(SeriesViewModel::class.java)

        mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentSeriesBinding.inflate(inflater, container, false)
        root = binding.root

        searchBar = root.findViewById(R.id.search_series_bar) as AutoCompleteTextView

        //Affiche l'historique quand le champ de recherche est pressé
        searchBar.setOnClickListener {
            val historyList = getHistories()
            val historySearchedList = getSearchedHistoryList(historyList)
            searchBar.setAdapter(activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, historySearchedList) })
        }

        //Envoie la recherche à l'historique si le bouton "entrer" est pressé
        searchBar.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                insertSearchedFilmToDatabase()
                searchBar.clearFocus()
            }
            false
        })

        //val textView: TextView = binding.textSeries
        seriesViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })
        return root
    }

    fun getHistories(): List<History> {
        return mHistoryViewModel.getUserHistories(2, "series")
    }

    private fun getSearchedHistoryList(historyList: List<History>): MutableList<String> {
        val historySearchedList = mutableListOf<String>()
        for (history : History in historyList) {
            historySearchedList.add(history.research)
        }
        return historySearchedList
    }

    private fun insertSearchedFilmToDatabase() {
        val searchedFilm = searchBar.text.toString()
        if (!TextUtils.isEmpty(searchedFilm)) {
            val existingHistories = getHistories()
            for (history : History in existingHistories) {
                if (history.research == searchedFilm) {
                    return
                }
            }
            val history = History(0, searchedFilm, "series", 2)
            mHistoryViewModel.addHistory(history)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}