package com.zaki.movieapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.zaki.movieapp.MovieApplication
import com.zaki.movieapp.R
import com.zaki.movieapp.data.remote.response.MovieTrending
import com.zaki.movieapp.databinding.ActivityDetailMovieBinding
import com.zaki.movieapp.util.DateUtil
import com.zaki.movieapp.viewmodel.DetailMovieViewModel
import javax.inject.Inject

class DetailMovieActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_EXTRA = "movie_extra"
    }

    private lateinit var binding: ActivityDetailMovieBinding

    private val movie: MovieTrending?
        get() = intent.getParcelableExtra(MOVIE_EXTRA)

    @Inject lateinit var viewModel: DetailMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as MovieApplication).appComponent.mainComponent().create().inject(this)

        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initClickListener()
        observeViewModel()
    }

    private fun initView() = with(binding) {
        setUp()

        Glide.with(this@DetailMovieActivity)
            .load("https://image.tmdb.org/t/p/w500/${movie?.posterPath}")
            .into(binding.ivMovie)

        binding.collapsingToolbar.title = movie?.title
        binding.tvOverviewValue.text = movie?.overview
        binding.tvReleaseDate.text = DateUtil.convertDate(movie?.releaseDate.orEmpty())
        binding.tvVoteAverage.text = String.format("%.1f", movie?.voteAverage)
    }

    private fun setUp() {
        setSupportActionBar(binding.detailToolbar)

        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Expanded)
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Collapsed)
    }

    private fun initClickListener() {
        binding.fabFavorite.setOnClickListener {
            movie?.let { viewModel.updateFavoriteMovie(it) }
        }
    }

    private fun observeViewModel() {
        movie?.let { viewModel.getFavoriteMovie(it) }

        viewModel.favoriteState.observe(this) {
            if (it) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmark_primary))
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_bookmark_border_24))
            }
        }
    }
}