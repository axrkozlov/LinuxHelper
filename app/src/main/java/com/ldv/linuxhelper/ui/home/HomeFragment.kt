package com.ldv.linuxhelper.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.FragmentHomeBinding
import com.ldv.linuxhelper.db.Topic
import com.ldv.linuxhelper.ui.content.ContentAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var listAdapter: TopicsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.coroutineScope.launch {
            viewModel.command.collect {
                when (it) {
                    is HomeViewModel.OpenTopic -> openTopic(it.topic)
                    is HomeViewModel.ShareTopic -> shareTopic(it.topic)
                }
            }
        }
        super.onCreate(savedInstanceState)
    }

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
        setupToolbar()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupListAdapter() {

        listAdapter = TopicsAdapter(viewModel)
        binding.topicList.adapter = listAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getList().collect {
                checkList(it)
                listAdapter.submitList(it)
//                Log.i("TAG", "setupListAdapter: ${it.size}")
//                if (it[0].topicParts.size>0) Log.i("TAG", "setupListAdapter: ${it[0].topicParts[0].command}")
            }
        }


    }

    fun setupToolbar() {
        binding.toolbar.setOnMenuItemClickListener{
            when(it.itemId){
                R.id.search_button-> navigateSearch()
                R.id.settings_button->showSettings()
            }
            true
        }
    }

    private fun showSettings() {

    }

    private fun navigateSearch() {
        findNavController().navigate(R.id.navigation_search)
    }

    private fun openTopic(topic: Topic) {
        Log.i("TAG", "openTopic: $topic")
//        val bundle = bundleOf("number" to topic.number)
//        findNavController().navigate(R.id.navigation_content,bundle)
        val direction =
            HomeFragmentDirections.actionNavigationHomeToNavigationContent(topic.number)
        findNavController().navigate(direction)
    }

    fun shareTopic(topic: Topic) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, topic.toString())
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