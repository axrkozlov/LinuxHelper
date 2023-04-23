package com.ldv.linuxhelper.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.ldv.linuxhelper.databinding.FragmentHomeBinding
import com.ldv.linuxhelper.db.Topic
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var listAdapter: TopicsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupListAdapter() {

        listAdapter = TopicsAdapter(viewModel)
        binding.topicList.adapter = listAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getList().collect {
                checkList(it)
                listAdapter.submitList(it)
                Log.i("TAG", "setupListAdapter: ${it.size}")
            }
        }
        lifecycle.coroutineScope.launch {
            viewModel.command.collect {
                when (it){
                    is HomeViewModel.OpenTopic -> shareTopic(it.topic)
                }
            }
        }


    }

    fun shareTopic(topic: Topic){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, topic.title)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun checkList(list: List<Topic>) {
        if (list.isEmpty()) viewModel.seedTopics()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}