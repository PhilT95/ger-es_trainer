package com.example.geres_trainer.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.geres_trainer.R
import com.example.geres_trainer.database.TranslationDB
import com.example.geres_trainer.databinding.TitleFragmentBinding
import com.google.android.material.snackbar.Snackbar


/**
 * This class provides the Fragment for the Title screen. This is the first screen the user can see.
 * The only thing this Fragment does  itself besides being the navigation hub, is providing the database reset function.
 */
class TitleFragment : Fragment () {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.title_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TranslationDB.getInstance(application).translationDBDao

        val viewModelFactory = TitleFragmentViewModelFactory(dataSource, application)

        val titleFragmentViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(TitleFragmentViewModel::class.java)

        binding.titleFragmentViewModel = titleFragmentViewModel

        binding.setLifecycleOwner(this)


        binding.playGameButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)

        }

        binding.viewWordsButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_titleFragment_to_viewFragment)
        }

        binding.newWordButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_titleFragment_to_addFragment)
        }


        /**
         * Observes the showSnackbarEvent variable and displays the corresponding SnackBar when changed to true.
         */
        titleFragmentViewModel.showSnackbarEvent.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.dataBaseReset_text),
                    Snackbar.LENGTH_SHORT
                ).show()

                titleFragmentViewModel.doneShowingSnackbar()

            }
        })

        /**
         * Observes the databaseReset variable that gets changed when the resetDatabase button is clicked.
         * Triggers the deleteDatabase function. The database gets populated through its own onCreate() function.
         *
         */
        titleFragmentViewModel.databaseReset.observe(this, Observer {
            if(it == true){
                titleFragmentViewModel.deleteDatabase(this.context!!)
            }
        })


        return binding.root
    }


}