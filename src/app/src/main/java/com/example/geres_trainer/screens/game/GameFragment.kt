package com.example.geres_trainer.screens.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.geres_trainer.R
import com.example.geres_trainer.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: GameFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.game_fragment, container, false)


        val application = requireNotNull(this.activity).application

        val viewModelFactory = GameFragmentViewModelFactory(application)

        val gameFragmentViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(GameFragmentViewModel::class.java)


        binding.gamerFragmentViewModel = gameFragmentViewModel

        binding.setLifecycleOwner(this)

        return binding.root

    }



}
