package com.example.geres_trainer.screens.edit

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
import com.example.geres_trainer.databinding.EditFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.edit_fragment.*


class EditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : EditFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.edit_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = EditFragmentArgs.fromBundle(arguments!!)


        val dataSource = TranslationDB.getInstance(application).translationDBDao
        val viewModelFactory = EditFragmentViewModelFactory(arguments.translationKey, dataSource)

        val editViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(EditFragmentViewModel::class.java)

        binding.editFragmentViewModel = editViewModel

        binding.setLifecycleOwner(this)

        binding.updateButton.setOnClickListener {
            editViewModel.updateClicked(binding.gerTextField.text.toString(), binding.esTextField.text.toString(), binding.infoTextField.text.toString())
        }

        editViewModel.updateIsClicked.observe(this, Observer {
            binding.updateButton.isEnabled = editViewModel.updateIsClicked.value!!.not()
        })

        editViewModel.showSnackbarEventSuccess.observe(this, Observer {
            if(it == true) {
                val snackbar = Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.translationUpdatedSuccess_message),
                    Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(application.getColor(R.color.colorCorrectWord))
                snackbar.show()


                editViewModel.doneShowingSnackbarSuccess()


            }
        })





        return binding.root

    }


}