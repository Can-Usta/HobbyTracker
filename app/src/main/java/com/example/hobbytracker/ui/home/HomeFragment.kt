package com.example.hobbytracker.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.hobbytracker.R
import com.example.hobbytracker.databinding.FragmentHomeBinding
import kotlin.math.roundToInt

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.apply {
            addHobbyFAB.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToHobbyAddFragment()
                findNavController().navigate(action)
            }
            ViewCompat.setOnApplyWindowInsetsListener(addHobbyFAB) { view, insets ->
                val navigationBarHeight = insets.getInsets(WindowInsets.Type.systemBars()).bottom

                val extraMarginInPx = (6 * resources.displayMetrics.density).roundToInt()

                val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = navigationBarHeight + extraMarginInPx
                view.layoutParams = layoutParams

                insets
            }
        }
        return binding.root
    }
}