package com.ldv.linuxhelper.ui.text

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
import androidx.navigation.fragment.findNavController
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.FragmentTextBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class TextFragment : Fragment() {

    private var _binding: FragmentTextBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TextViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTextBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupSearchToolbar()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        return root
    }

     fun setupSearchToolbar() {
         with(binding.toolbar.menu) {
             val searchItem = findItem(R.id.action_search)
             val searchView = searchItem?.actionView as SearchView
             searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
             searchView.setQueryHint("Поиск")
             searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                 override fun onQueryTextSubmit(p0: String?): Boolean {
                     search(p0)
                     return false
                 }

                 override fun onQueryTextChange(p0: String?): Boolean {
                     search(p0)
                     return false
                 }
             })


         }
    }

    fun search(searchKey:String?){
        Log.i("TAG", "search: $searchKey")
        if (searchKey.isNullOrBlank()) return
        val baseText = binding.topicContent.text.toString()
        if (baseText.contains(searchKey,true)){
            val startIndex = baseText.indexOf(searchKey,0,true)
            val endIndex = startIndex+searchKey.length
            val spannable = SpannableString(baseText)
            spannable.setSpan(UnderlineSpan(),startIndex,endIndex,0)
            spannable.setSpan(ForegroundColorSpan(Color.RED),startIndex,endIndex,0)
            binding.topicContent.setText(spannable)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}