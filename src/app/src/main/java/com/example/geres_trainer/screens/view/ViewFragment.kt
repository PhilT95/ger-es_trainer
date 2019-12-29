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
import com.example.geres_trainer.database.TranslationDB
import com.example.geres_trainer.databinding.ViewFragmentBinding
import com.example.geres_trainer.util.adapter.TranslationAdapter
import com.example.geres_trainer.util.adapter.TranslationListener


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

    override fun onQueryTextChange(newText: String?): Boolean {
        searchText.value = newText
        return true

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }










}