package com.example.geres_trainer.screens.end

import android.app.Application
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
import com.example.geres_trainer.databinding.EndFragmentBinding
import com.example.geres_trainer.util.keyToListDecoder
import kotlinx.android.synthetic.main.activity_main.*

class EndFragment () : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : EndFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.end_fragment,container,false)

        val application = requireNotNull(this.activity).application
        val dataSource = TranslationDB.getInstance(application).translationDBDao

        val points = arguments?.get("points").toString().toInt()
        val pointsPercent : Float = (points.toFloat()/resources.getInteger(R.integer.defaultGameSize).toFloat())





        val viewModelFactory = EndFragmentViewModelFactory(dataSource, keyToListDecoder(arguments?.get("keys").toString()), application)

        val endFragmentViewModel =
            ViewModelProviders.of(
                this,viewModelFactory).get(EndFragmentViewModel::class.java)

        binding.endFragmentViewModel = endFragmentViewModel


        binding.setLifecycleOwner(this)


        binding.GameStatusTextView.text = getString(R.string.gameStatus_text, (pointsPercent*100))


        binding.playAgainButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_endFragment_to_gameFragment)
        }

        binding.goToTitleButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_endFragment_to_titleFragment)
        }

        val adapter = TranslationAdapter()
        binding.translationList.adapter = adapter

        endFragmentViewModel.translations.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })










        return binding.root
    }

}