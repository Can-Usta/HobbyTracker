package com.example.hobbytracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hobbytracker.R
import com.example.hobbytracker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()
    private lateinit var hobbyAdapter : HomePageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.apply {
            addHobbyFAB.setOnClickListener{
                val action = HomeFragmentDirections.actionHomeFragmentToHobbyAddFragment()
                findNavController().navigate(action)
            }
            ViewCompat.setOnApplyWindowInsetsListener(addHobbyFAB) { view, insets ->
                val navigationBarHeight = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom

                val extraMarginInPx = (6 * resources.displayMetrics.density).roundToInt()

                val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.bottomMargin = navigationBarHeight + extraMarginInPx
                view.layoutParams = layoutParams

                insets
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hobbyAdapter = HomePageAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = hobbyAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.hobby.collect { hobbies ->
                hobbyAdapter = HomePageAdapter(hobbies)
                binding.recyclerView.adapter = hobbyAdapter
            }
        }
    }
}