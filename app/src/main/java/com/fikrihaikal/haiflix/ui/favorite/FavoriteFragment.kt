package com.fikrihaikal.haiflix.ui.favorite

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikrihaikal.haiflix.R
import com.fikrihaikal.haiflix.core.domain.model.Movie
import com.fikrihaikal.haiflix.databinding.FragmentFavoriteBinding
import com.fikrihaikal.haiflix.ui.detail.DetailActivity
import com.fikrihaikal.haiflix.ui.detail.adapter.TrailerAdapter
import com.fikrihaikal.haiflix.ui.favorite.adapter.FavoriteAdapter
import com.fikrihaikal.haiflix.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoriteViewModel by viewModels()

    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(
            itemClick = {data ->
                val bundle = Bundle().apply {
                    putParcelable(DetailActivity.EXTRA_MOVIE_ID,data)
                }
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            },
            itemDelete = { movie ->
                showDeleteConfirmationDialog(movie)
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        initRv()
    }

    private fun showDeleteConfirmationDialog(movie: Movie){
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Favorite")
            setMessage("Are you sure you want to remove this movie from your favorites list?")
            setPositiveButton("Yes"){ dialog, _ ->
                viewModel.deleteMovie(movie)
                dialog.dismiss()
            }
            setNegativeButton("No"){ dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }
    private fun initRv() {
        binding.rvMovie.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
        }
        viewModel.getAllFavorite.observe(viewLifecycleOwner){
            favoriteAdapter.setItems(it)
            if (it.isNotEmpty()){
                binding.rvMovie.visibility = View.VISIBLE
                binding.linearEmpty.visibility = View.GONE
            }else{
                binding.rvMovie.visibility = View.GONE
                binding.linearEmpty.visibility = View.VISIBLE
            }
        }
    }

    private fun onBackPressed() {
        binding.btnBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}