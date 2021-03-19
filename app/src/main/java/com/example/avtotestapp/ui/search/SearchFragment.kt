package com.example.avtotestapp.ui.search

import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.view.animation.Animation
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
//import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avtotestapp.R
import com.example.avtotestapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

import android.view.animation.AnimationUtils.loadAnimation


@AndroidEntryPoint
class SearchFragment : Fragment() {

    var animatedHide = false
    var animatedShow = false

    private val viewModel by viewModels<SearchViewModel>()
    private val rvAdapter: ItemAdapter = ItemAdapter(ItemAdapter.ClickListener{
        viewModel.displayPropertyDetails(it)
    })

    lateinit var binding : FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding
            .inflate(inflater, container, false)

        val rvLayoutManager = GridLayoutManager(context, 1)

        binding.recyclerView.apply {
            layoutManager = rvLayoutManager
            adapter = rvAdapter
        }

        visibleButtonToTop(rvLayoutManager)
        swipeToRefresh()

        viewModel.repositories.observe(viewLifecycleOwner, Observer {
            rvAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToUserFragment(it))
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchRepos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }


    private fun visibleButtonToTop(layoutManager: GridLayoutManager){
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (layoutManager.findLastVisibleItemPosition() >= 11) {
                    if(!animatedShow){
                        binding.toTop.visibility = View.VISIBLE
                        val animArrow = loadAnimation(context, R.anim.arrowup)
                        binding.toTop.startAnimation(animArrow)
                        animatedShow = true
                        animatedHide = false
                    }
                }else {
                    if (!animatedHide){
                        val animArrow2 = loadAnimation(context, R.anim.arrowup2)
                        binding.toTop.startAnimation(animArrow2)
                        binding.toTop.visibility = View.GONE
                        animatedShow = false
                        animatedHide = true
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.toTop.setOnClickListener{
            binding.recyclerView.smoothScrollToPosition(0)
        }
    }

    private fun swipeToRefresh(){
        binding.swipetorefresh.apply {
            setProgressBackgroundColorSchemeColor(ContextCompat
                .getColor(requireContext(), R.color.white))
            setColorSchemeColors(ContextCompat
                .getColor(requireContext(), R.color.design_default_color_primary))

            setOnRefreshListener {
                viewModel.refresh()
                isRefreshing = false
            }
        }
    }



}