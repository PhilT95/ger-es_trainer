package com.example.geres_trainer.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.geres_trainer.R
import com.example.geres_trainer.databinding.AboutFragmentBinding

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : AboutFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.about_fragment, container, false)


        binding.setLifecycleOwner(this)

        return binding.root

    }
}