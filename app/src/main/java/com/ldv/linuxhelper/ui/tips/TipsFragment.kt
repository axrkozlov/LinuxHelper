package com.ldv.linuxhelper.ui.tips

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.FragmentTipsBinding
import com.ldv.linuxhelper.db.Tip
import com.ldv.linuxhelper.db.Topic
import com.ldv.linuxhelper.ui.home.HomeFragmentDirections
import com.ldv.linuxhelper.ui.home.HomeViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class TipsFragment : Fragment() {

    private var _binding: FragmentTipsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: TipsViewModel by viewModel()

    private lateinit var listAdapter: TipsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        lifecycle.coroutineScope.launch {
            viewModel.command.collect {
                when (it){
                    is TipsViewModel.OpenTip -> openTip(it.tip)
                    is TipsViewModel.ShareTip -> shareTip(it.tip)
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

        _binding = FragmentTipsBinding.inflate(inflater, container, false)

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

        listAdapter = TipsAdapter(viewModel)
        binding.tipList.adapter = listAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getList().collect {
                checkList(it)
                listAdapter.submitList(it)
                Log.i("TAG", "setupListAdapter: ${it.size}")
            }
        }



    }

    private fun openTip(tip: Tip) {
        val direction = TipsFragmentDirections.actionNavigationTipsToNavigationWebInfo(tip.content)
        findNavController().navigate(direction)
    }


    fun shareTip(tip: Tip){
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, tip.content)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun checkList(list: List<Tip>) {
        if (list.isEmpty()) viewModel.seedTip()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}