package com.example.avtotestapp.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.avtotestapp.R
import com.example.avtotestapp.databinding.FragmentUserBinding
import com.example.avtotestapp.ui.search.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

//import java.util.Observer

//import com.example.avtotestapp.databinding.FragmentSearchBinding

@AndroidEntryPoint
class UserFragment : Fragment() {
    private val args: UserFragmentArgs by navArgs()

    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentUserBinding
                .inflate(inflater, container, false)

        viewModel.getUser(args.login)
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.apply {
                login.text = it.login
                bio.text = it.bio
                if(it.twitter_username != null){
                    twit.text = "@"+it.twitter_username
                }
//                twit.text = "@"+it.twitter_username
                follower.text = "followers: " + it.followers.toString()
                following.text = "following: " + it.following.toString()
            }
            Glide.with(requireContext()).load(it.avatar_url).into(binding.imageView2)
        })

        return binding.root

    }


}