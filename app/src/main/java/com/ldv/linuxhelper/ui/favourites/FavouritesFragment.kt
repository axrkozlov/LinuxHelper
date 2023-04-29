package com.ldv.linuxhelper.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.ldv.linuxhelper.databinding.FragmentFavouritesBinding
import com.ldv.linuxhelper.db.Content
import com.ldv.linuxhelper.ui.content.ContentAdapter
import com.ldv.linuxhelper.ui.content.ContentViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: ContentViewModel by viewModel()
    private lateinit var listAdapter: ContentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.coroutineScope.launch {
            viewModel.command.collect {
                when (it){
                    is ContentViewModel.ShareTopic -> shareCommand(it.string)
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

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListAdapter()
    }

    private fun setupListAdapter() {

        listAdapter = ContentAdapter(viewModel)
        binding.tipList.adapter = listAdapter
        lifecycle.coroutineScope.launch {
            viewModel.getList().collect { list->
                val content = mutableListOf<Content>()
                list.forEach { topic->
                    topic.topicParts.forEach {part->
                        if (part.isBookmarked) content.add(Content(part,topic))
                    }
                }
                listAdapter.submitList(content)
                if (content.isEmpty())
                    binding.emptyListText.visibility=View.VISIBLE
                else  binding.emptyListText.visibility=View.GONE


            }
        }



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