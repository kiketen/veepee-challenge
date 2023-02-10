package com.vp.detail.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vp.core.presentation.DETAIL_ID
import com.vp.detail.R
import com.vp.detail.databinding.ActivityDetailBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity(), QueryProvider {

    private lateinit var detailViewModel: DetailsViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = detailViewModel
        queryProvider = this
        binding.lifecycleOwner = this
        detailViewModel.fetchDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        detailViewModel.details().observe(this) {
            supportActionBar?.title = it.title
            menu?.findItem(R.id.star)?.apply {
                isChecked = it.isFavorite
                icon = ContextCompat.getDrawable(this@DetailActivity, getMenuItemIcon(it.isFavorite))
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.star -> {
                detailViewModel.onFavoriteClick(item.isChecked)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getMovieId(): String {
        return intent?.getStringExtra(DETAIL_ID) ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    private fun getMenuItemIcon(isChecked: Boolean): Int {
        return if (isChecked) {
            R.drawable.ic_star_on
        } else {
            R.drawable.ic_star_off
        }
    }

    companion object {
        lateinit var queryProvider: QueryProvider
    }
}
