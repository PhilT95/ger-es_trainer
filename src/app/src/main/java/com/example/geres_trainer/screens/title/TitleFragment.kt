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

        titleFragmentViewModel.showSnackbarEvent.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    titleFragmentViewModel.searchedTranslation.value.toString(),
                    Snackbar.LENGTH_SHORT
                ).show()



            }
        })
        return binding.root
    }


}