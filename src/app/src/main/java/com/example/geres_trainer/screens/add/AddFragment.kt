package com.example.geres_trainer.screens.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.geres_trainer.R
import com.example.geres_trainer.database.translation.TranslationDB
import com.example.geres_trainer.databinding.AddFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.add_fragment.*

class AddFragment :  Fragment () {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AddFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.add_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = TranslationDB.getInstance(application).translationDBDao

        val viewModelFactory = AddFragmentViewModelFactory(dataSource, application)

        val addFragmentViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(AddFragmentViewModel::class.java)


        /**
         * Clears the text of all text fields in the add fragment.
         */
        fun clearTextFields() {
            binding.gerTextField.setText("")
            binding.esTextField.setText("")
            binding.infoTextField.setText("")
        }

        binding.addButton.setOnClickListener {
            addFragmentViewModel.onAdd(gerTextField.text.toString(), esTextField.text.toString(), infoTextField.text.toString())
            clearTextFields()
        }


        /**
         * Creates listener to every change of the gerTextField through an TextWatcher.
         * Executes the ViewModels checkState() function when change appears.
         */
        binding.gerTextField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addFragmentViewModel.checkState(gerTextField.text.toString(), esTextField.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        /**
         * Creates listener to every change of the esTextField through an TextWatcher.
         * Executes the ViewModels checkState() function when change appears.
         */
        binding.esTextField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                addFragmentViewModel.checkState(gerTextField.text.toString(), esTextField.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })



        addFragmentViewModel.canAddTranslation.observe(this, Observer {
            binding.addButton.isEnabled = it
        })


        /**
         * Displays a Snackbar if the word was added successfully.
         */
        addFragmentViewModel.showSnackbarEventSuccess.observe(this, Observer {
            if(it == true) {
                val snackbar = Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.translationAddedSuccess_message),
                    Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(application.getColor(R.color.colorCorrectWord))
                snackbar.show()


                addFragmentViewModel.doneShowingSnackbarSuccess()
                clearTextFields()

            }
        })

        /**
         * Displays a Snackbar if an error occurs.
         */
        addFragmentViewModel.showSnackbarEventFail.observe(this, Observer {
            if(it == true) {
                val snackbar = Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.translationAddedFail_message),
                    Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(application.getColor(R.color.colorFalseWord))
                snackbar.show()

                addFragmentViewModel.doneShowingSnackbarFail()

            }
        })


        /**
         * Displays a Snackbar that states that either of the translations of a word contain a illegal symbol.
         */
        addFragmentViewModel.showSnackBarEventIllegalSymbol.observe(this, Observer {
            if(it == true){
                val snackbar = Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.translationAddedIllegalSymbol_message),
                    Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(application.getColor(R.color.colorFalseWord))
                snackbar.show()

                addFragmentViewModel.doneShowingSnackbarIllegalSymbol()
            }

        })



        binding.addFragmentViewModel = addFragmentViewModel







        return binding.root
    }


}