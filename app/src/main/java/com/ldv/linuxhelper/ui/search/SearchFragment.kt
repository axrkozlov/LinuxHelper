package com.ldv.linuxhelper.ui.search

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.FragmentSearchBinding
import com.ldv.linuxhelper.databinding.FragmentTextBinding
import com.ldv.linuxhelper.db.Content
import com.ldv.linuxhelper.db.Topic
import com.ldv.linuxhelper.db.TopicPart
import com.ldv.linuxhelper.ui.content.ContentAdapter
import com.ldv.linuxhelper.ui.content.ContentViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.dsl.koinApplication


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var listAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.coroutineScope.launch {
            viewModel.command.collect {
                when (it){
                    is SearchViewModel.ShareTopic -> shareCommand(it.string)
                    else ->{}
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

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupSearchToolbar()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

    fun setupSearchToolbar() {
         with(binding.toolbar.menu) {
             val searchItem = findItem(R.id.action_search)
             val searchView = searchItem?.actionView as SearchView
             searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
             searchView.setQueryHint("Поиск")
             searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                 override fun onQueryTextSubmit(p0: String?): Boolean {
                     searchKey = p0
                     filter()
                     return false
                 }

                 override fun onQueryTextChange(p0: String?): Boolean {
                     searchKey = p0
                     filter()
                     return false
                 }
             })


         }
    }

    private fun setupListAdapter() {

        listAdapter = SearchAdapter(viewModel)
        binding.tipList.adapter = listAdapter
        lifecycle.coroutineScope.launch {
            viewModel.getList().collect {
                list=it
                filter()

            }
        }



    }

    var list = listOf<Topic>()
    var searchKey:String? = ""

    @SuppressLint("SuspiciousIndentation")
    fun filter(){
        val content = mutableListOf<Content>()
            list.forEach { topic->
            topic.topicParts.filter { topicPart->
                search(searchKey,topicPart)
            }
                .map {
                    content.add(Content(it , topic))
                }
        }

        listAdapter.submitList(content)
    }

    fun search(searchKey:String?,topicPart: TopicPart):Boolean{
        if (searchKey.isNullOrBlank()) return true
        return topicPart.command.contains(searchKey) ||
            topicPart.description.contains(searchKey) ||
            topicPart.example.contains(searchKey)
    }

    fun shareCommand(string: String){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, string)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}