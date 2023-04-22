package com.ldv.linuxhelper.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ldv.linuxhelper.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var listAdapter: TopicsAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel= homeViewModel
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.title
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListAdapter()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun setupListAdapter() {




            listAdapter = TopicsAdapter(viewModel)
            binding.topicList.adapter = listAdapter
        listAdapter.submitList(list)
    }

    val list = listOf (
        Topic("asdf","asdf",null),
        Topic("zxcvc","zcv",null)

    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}