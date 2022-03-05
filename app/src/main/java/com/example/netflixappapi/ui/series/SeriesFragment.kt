package com.example.netflixappapi.ui.series

import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.netflixappapi.api.movies_series.NetflixViewModel
import com.example.netflixappapi.databinding.FragmentSeriesBinding
import com.google.android.flexbox.FlexboxLayout
import com.squareup.picasso.Picasso
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView.OnEditorActionListener
import com.example.netflixappapi.R
import com.example.netflixappapi.data.model.History
import com.example.netflixappapi.data.viewmodel.HistoryViewModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap

class SeriesFragment : Fragment() {

    private val netflixViewModel by activityViewModels<NetflixViewModel>()
    private lateinit var mHistoryViewModel: HistoryViewModel
    private lateinit var searchBar: AutoCompleteTextView

    private var _binding: FragmentSeriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mHistoryViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        searchBar = view.findViewById(R.id.search_series_bar) as AutoCompleteTextView
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

        val parentFlexbox = view.findViewById<FlexboxLayout>(R.id.listSeries)
        netflixViewModel.series.observe(viewLifecycleOwner, Observer {
            //Nous bouclons sur toutes la liste des series
            for(results in it.listResults){
                // Création de la première flexbox contenant l'image et une flexbox contenant le reste
                val flexbox1 = FlexboxLayout(context)
                val params = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.MATCH_PARENT,
                    FlexboxLayout.LayoutParams.MATCH_PARENT
                )
                params.setMargins(0, 0, 0, 10)
                flexbox1.layoutParams = params
                flexbox1.flexDirection = FlexDirection.ROW
                flexbox1.flexWrap = FlexWrap.WRAP

                //Création de l'image
                val imgView = ImageView(context)
                //Utilisation de la librairie Picasso pour générer l'image dans l'ImageView
                Picasso
                    .with(context)
                    .load(results.img)
                    .resize(350,500)
                    .into(imgView)

                //Création de la flexbox n°2 contenant 2 autres flexbox affichant chacune le titre et les pays de diffusion de la série
                val flexbox2 = FlexboxLayout(context)
                val params2 = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(10, 0, 0, 0)
                flexbox2.layoutParams = params2
                flexbox2.flexDirection = FlexDirection.COLUMN
                flexbox2.flexWrap = FlexWrap.WRAP

                //Ajout de l'image et de la flexbox 2 dans la flexbox1
                flexbox1.addView(imgView);
                flexbox1.addView(flexbox2);

                //Création de la flexbox n°3 contenant le titre de la serie
                val flexbox3 = FlexboxLayout(context)
                val params3 = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, 0, 0, 10)
                flexbox3.layoutParams = params3
                flexbox3.flexDirection = FlexDirection.COLUMN
                flexbox3.flexWrap = FlexWrap.WRAP

                //Création de la flexbox n°4 contenant les pays de diffusion de la serie
                val flexbox4 = FlexboxLayout(context)
                val params4 = FlexboxLayout.LayoutParams(
                    FlexboxLayout.LayoutParams.WRAP_CONTENT,
                    FlexboxLayout.LayoutParams.WRAP_CONTENT
                )
                flexbox4.layoutParams = params4
                flexbox4.flexDirection = FlexDirection.COLUMN
                flexbox4.flexWrap = FlexWrap.WRAP

                //Ajout de ces deux flexbox dans la flexbox n°2
                flexbox2.addView(flexbox3)
                flexbox2.addView(flexbox4)

                //Création de 2 TextView représentant le label et le titre de la serie
                val labelTitle = TextView(context)
                val titleMovie = TextView(context)

                labelTitle.text = getString(R.string.title)
                labelTitle.typeface = Typeface.DEFAULT_BOLD
                titleMovie.text = results.title

                //Ajout de ces deux TextView dans la flexbox n°3
                flexbox3.addView(labelTitle)
                flexbox3.addView(titleMovie)

                //Création de 2 TextView représentant le label et la liste des pays diffusant la serie
                val labelCountries = TextView(context)
                val listCountry = TextView(context)

                labelCountries.text = getString(R.string.countries)
                labelCountries.typeface = Typeface.DEFAULT_BOLD
                listCountry.text = results.countryList

                //Ajout de ces deux TextView dans la flexbox n°4
                flexbox4.addView(labelCountries)
                flexbox4.addView(listCountry)

                //Puis nous ajoutons tout dans la flexbox "parent" déjà créée dans le xml
                parentFlexbox.addView(flexbox1)
            }
        })
        netflixViewModel.getSeries()
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

}