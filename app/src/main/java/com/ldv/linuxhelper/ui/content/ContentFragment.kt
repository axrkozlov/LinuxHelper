package com.ldv.linuxhelper.ui.content

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.FragmentContentBinding
import com.ldv.linuxhelper.databinding.FragmentTipsBinding
import com.ldv.linuxhelper.db.Tip
import com.ldv.linuxhelper.ui.tips.TipsAdapter
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
            viewModel.getTopic(args.number).collect {
                binding.toolbar.title = it.title
                listAdapter.submitList(it.topicParts)
                Log.i("TAG", "setupListAdapter: ${it.topicParts}")
            }
        }



    }

    private fun openTip(tip: Tip) {
        findNavController().navigate(R.id.navigation_text)
    }

    fun shareTip(tip: Tip){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, tip.title)
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