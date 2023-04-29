package com.ldv.linuxhelper.ui.content

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.FragmentContentBinding
import com.ldv.linuxhelper.db.Content
import com.ldv.linuxhelper.db.Tip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContentFragment : Fragment() {

    private var _binding: FragmentContentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ContentViewModel by viewModel()
    val args: ContentFragmentArgs by navArgs()

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

        _binding = FragmentContentBinding.inflate(inflater, container, false)
        savedInstanceState?.getLong("number")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListAdapter()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupListAdapter() {

        listAdapter = ContentAdapter(viewModel)
        binding.tipList.adapter = listAdapter
        lifecycle.coroutineScope.launch {
            viewModel.getTopic(args.number).collect { topic->
                binding.toolbar.title = topic.title
                val content = topic.topicParts.map {
                    Content(it,topic)
                }
                listAdapter.submitList(content)
            }
        }



    }

    private fun openTip(tip: Tip) {
        findNavController().navigate(R.id.navigation_text)
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