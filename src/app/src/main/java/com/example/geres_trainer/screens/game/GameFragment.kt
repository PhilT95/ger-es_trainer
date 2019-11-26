package com.example.geres_trainer.screens.game

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.geres_trainer.R
import com.example.geres_trainer.database.TranslationDB
import com.example.geres_trainer.databinding.GameFragmentBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GameFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container, false)


        val application = requireNotNull(this.activity).application

        val dataSource = TranslationDB.getInstance(application).translationDBDao

        val viewModelFactory = GameFragmentViewModelFactory(dataSource, application)

        val gameFragmentViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(GameFragmentViewModel::class.java)


        binding.gameFragmentViewModel = gameFragmentViewModel




        binding.comfirmAnswerButton.setOnClickListener {
            gameFragmentViewModel.onConfirmClick(binding.answerTextField.text.toString())
            binding.answerTextField.text.clear()
            binding.answerTextField.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }

        gameFragmentViewModel.gameIsDone.observe(this, Observer {
            this.findNavController().navigate(R.id.action_gameFragment_to_endFragment)
        })

        gameFragmentViewModel.listIsFilled.observe(this, Observer {
            if(gameFragmentViewModel.listIsFilled.value == true) {
                gameFragmentViewModel.startGame()
            }

        })

        gameFragmentViewModel.showSnackBarCorrect.observe(this, Observer {
            if (it == true) {
                val snackbar = Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    getString(R.string.wordCorrectSnackBar_text),
                    Snackbar.LENGTH_SHORT)
                snackbar.view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                snackbar.show()

                gameFragmentViewModel.doneShowCorrectSnackBar()

            }
        })

        gameFragmentViewModel.initRandomGame()


        binding.setLifecycleOwner(this)




        return binding.root

    }



}
