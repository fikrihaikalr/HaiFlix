package com.fikrihaikal.haiflix.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fikrihaikal.haiflix.R
import com.fikrihaikal.haiflix.core.data.source.Resource
import com.fikrihaikal.haiflix.databinding.FragmentHomeBinding
import com.fikrihaikal.haiflix.ui.detail.DetailActivity
import com.fikrihaikal.haiflix.ui.home.adapter.MovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter{ data ->
            val bundle = Bundle().apply {
                putParcelable(DetailActivity.EXTRA_MOVIE_ID,data)
            }
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            Log.d("item id","item clicked -> ID : ${data.id}, Name : ${data.name}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initRv()
        toFav()
    }

    private fun toFav() {
        binding.ivFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun initRv() {
        binding.rvMovie.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun observeData() {
        viewModel.getMovie.observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading ->{
                    binding.apply {
                        rvMovie.visibility = View.GONE
                        pbHome.visibility = View.VISIBLE
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        rvMovie.visibility = View.VISIBLE
                        pbHome.visibility = View.GONE
                        it.data?.let { movies ->
                            movieAdapter.setItems(movies.shuffled())
                        }
                    }

                }
                is Resource.Error -> {
                    binding.apply {
                        rvMovie.visibility = View.GONE
                        pbHome.visibility = View.GONE
                    }
                }
            }
        }
    }
//    private fun observeData() {
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.discoverMovies.collect{
//                    when(it){
//                        is Resource.Success ->{
//                            binding.rvMovie.visibility = View.VISIBLE
//                            binding.pbHome.visibility = View.GONE
//                            it.data?.let {movies ->
//                                movieAdapter.setItems(movies.shuffled())
//                            }
//                        }
//                        is Resource.Error ->{
//                        }
//                        is Resource.Loading ->{
//                            binding.rvMovie.visibility = View.GONE
//                            binding.pbHome.visibility = View.VISIBLE
//                        }
//                    }
//                }
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}