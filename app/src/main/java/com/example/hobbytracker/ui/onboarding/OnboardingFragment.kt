package com.example.hobbytracker.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hobbytracker.R
import com.example.hobbytracker.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarding, container, false)

        binding.apply {
            getStartedButton.setOnClickListener {
                findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
            }
        }
        return binding.root
    }
}