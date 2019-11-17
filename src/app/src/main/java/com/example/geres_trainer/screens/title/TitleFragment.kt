package com.example.geres_trainer.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.geres_trainer.R
import com.example.geres_trainer.databinding.TitleFragmentBinding
import kotlinx.android.synthetic.main.title_fragment.*


class TitleFragment : Fragment () {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: TitleFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.title_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = TitleFragmentViewModelFactory(application)

        val titleFragmentViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(TitleFragmentViewModel::class.java)

        binding.titleFragmentViewModel = titleFragmentViewModel

        binding.setLifecycleOwner(this)








        binding.playGameButton.setOnClickListener {
            val toast = Toast.makeText(this.context,"Test",Toast.LENGTH_SHORT)
            toast.show()
        }
        return binding.root
    }


}