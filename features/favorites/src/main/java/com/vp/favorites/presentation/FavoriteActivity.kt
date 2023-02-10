package com.vp.favorites.presentation

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.vp.core.extensions.observe
import com.vp.core.extensions.switchVisibility
import com.vp.core.presentation.DEEPLINK_DETAIL
import com.vp.core.presentation.DETAIL_ID
import com.vp.core.presentation.list.ListAdapter
import com.vp.favorites.databinding.ActivityFavoriteBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class FavoriteActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    private lateinit var listAdapter: ListAdapter

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel::class.java)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteMovies()
    }

    private fun initLayout() {
        listAdapter = ListAdapter()
        listAdapter.setOnItemClickListener {
            viewModel.onItemClick(it)
        }

        binding.favorites.adapter = listAdapter
        binding.favorites.setHasFixedSize(true)
        binding.favorites.layoutManager = GridLayoutManager(
                this, if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3
        )
    }

    private fun setObservers() {
        observe(viewModel.state) {
            binding.progressFavorites.switchVisibility(it.loading)
            listAdapter.setItems(it.movies)
        }
        observe(viewModel.navigator) {
            when (it) {
                is FavoritesNavigation.Detail -> goToDetail(it.id)
            }
        }
    }

    private fun goToDetail(id: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DEEPLINK_DETAIL))
        intent.putExtra(DETAIL_ID, id)
        intent.setPackage(packageName)
        startActivity(intent)
    }

}