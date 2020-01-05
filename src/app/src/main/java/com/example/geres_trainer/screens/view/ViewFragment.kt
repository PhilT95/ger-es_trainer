package com.example.geres_trainer.screens.view


import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.geres_trainer.R
import com.example.geres_trainer.database.translation.TranslationDB
import com.example.geres_trainer.databinding.ViewFragmentBinding
import com.example.geres_trainer.util.adapter.TranslationAdapter
import com.example.geres_trainer.util.adapter.TranslationListener


/**
 * This class also implements the SearchView.OnQueryTextListener class.
 * Through this implementation this class itself can be added as an OnQueryTextListener for the search.
 */
class ViewFragment : Fragment (), SearchView.OnQueryTextListener{


    private var searchText = MutableLiveData<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ViewFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.view_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TranslationDB.getInstance(application).translationDBDao

        val viewModelFactory = ViewFragmentViewModelFactory(dataSource)

        val viewFragmentViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(ViewFragmentViewModel::class.java)

        binding.viewFragmentViewModel = viewFragmentViewModel

        //Tells the fragment that it needs to enable the options menu on the top.
        setHasOptionsMenu(true)
        binding.setLifecycleOwner(this)




        viewFragmentViewModel.navigateToEdit.observe(this, Observer {translation ->
            translation?.let {
                this.findNavController().navigate(
                    ViewFragmentDirections
                        .actionViewFragmentToEditFragment(translation)
                )
                viewFragmentViewModel.onEditNavigated()
            }
        })

        val adapter = TranslationAdapter(TranslationListener { translationID ->
            viewFragmentViewModel.onTranslationClicked(translationID)
        })
        binding.translationList.adapter = adapter

        viewFragmentViewModel.translations.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        /**
         * This Observer submits a list to the RecyclerViewAdapter.
         * It submits either the original list with all translations or a filtered list based on the search string entered by the user.
         */
        searchText.observe(this, Observer {searchString ->
            searchString?.let {
                if(searchString == ""){
                    adapter.submitList(viewFragmentViewModel.translations.value)
                }
                else{
                    val list = viewFragmentViewModel.searchTranslations(searchString)
                    adapter.submitList(list)
                }

            }
        })
        return binding.root
    }


    /**
     * This option is called when the setHasOptionsMenu option of the Fragment is set to true,
     * through the setHasOptionsMenu(true) function. It inflates an menu that is designed in an layout xml file.
     * Since since Fragment class implements the SearchView.OnQueryListener it can be added to the SearchView object itself.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.search_menu, menu)

        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        searchView.setOnCloseListener{
            searchView.setQuery("",true)
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * This function is called each time the search text is changed.
     * Since I want to use the search text in the ViewModel I need to pass it to the onCreateView() function of the Fragment.
     * There I have implemented an observer for the searchText variable so I can react on the change that this function is providing.
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        searchText.value = newText
        return true
    }

    /**
     * This function needs to be implemented, but I personally think its more intuitive if the app
     * updates the search with every letter entered by the user instead of him hitting the submit button.
     * That is the reason this function doesn't do anything more.
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }










}