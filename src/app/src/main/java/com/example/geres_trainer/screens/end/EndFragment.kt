package com.example.geres_trainer.screens.end

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.geres_trainer.R
import com.example.geres_trainer.databinding.EndFragmentBinding

class EndFragment () : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : EndFragmentBinding = DataBindingUtil.inflate(inflater,
            R.layout.end_fragment,container,false)

        val application = requireNotNull(this.activity).application




    



        binding.setLifecycleOwner(this)




        return binding.root
    }
}