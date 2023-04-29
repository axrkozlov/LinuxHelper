package com.ldv.linuxhelper.ui.webinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ldv.linuxhelper.databinding.FragmentWebinfoBinding
import com.ldv.linuxhelper.ui.content.ContentFragmentArgs
import com.ldv.linuxhelper.ui.content.ContentViewModel
import com.ldv.linuxhelper.ui.text.WebInfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class WebInfoFragment : Fragment() {

    private var _binding: FragmentWebinfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WebInfoViewModel by viewModel()
    val args: WebInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWebinfoBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        setupSearchToolbar()
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webSettings: WebSettings = binding.webView.getSettings()
        webSettings.javaScriptEnabled = true
        binding.webView.setWebViewClient(WebViewClient())


        binding.webView.loadUrl(args.page)
    }

    //     fun setupSearchToolbar() {
//         with(binding.toolbar.menu) {
//             val searchItem = findItem(R.id.action_search)
//             val searchView = searchItem?.actionView as SearchView
//             searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
//             searchView.setQueryHint("Поиск")
//             searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//                 override fun onQueryTextSubmit(p0: String?): Boolean {
//                     search(p0)
//                     return false
//                 }
//
//                 override fun onQueryTextChange(p0: String?): Boolean {
//                     search(p0)
//                     return false
//                 }
//             })
//
//
//         }
//    }

//    fun search(searchKey:String?){
//        Log.i("TAG", "search: $searchKey")
//        if (searchKey.isNullOrBlank()) return
//        val baseText = binding.topicContent.text.toString()
//        if (baseText.contains(searchKey,true)){
//            val startIndex = baseText.indexOf(searchKey,0,true)
//            val endIndex = startIndex+searchKey.length
//            val spannable = SpannableString(baseText)
//            spannable.setSpan(UnderlineSpan(),startIndex,endIndex,0)
//            spannable.setSpan(ForegroundColorSpan(Color.RED),startIndex,endIndex,0)
//            binding.topicContent.setText(spannable)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}