package com.ldv.linuxhelper.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.ldv.linuxhelper.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var listAdapter: TopicsAdapter
//    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//        viewModel= homeViewModel
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


        viewModel.seedTopics()

            listAdapter = TopicsAdapter(viewModel)
            binding.topicList.adapter = listAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getList().collect{
                listAdapter.submitList(it)
                Log.i("TAG", "setupListAdapter: ${it.size}")
            }
        }

    }

//    val list = listOf (
//        Topic(1,"Что такое Linux?","История создания",null),
//        Topic(2,"Дистрибутивы Linux","Из чего состоят дистрибутивы Linux",null)
//
//    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}