package com.jbappz.jsontoviews

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jbappz.jsontoviews.databinding.ActivityMainBinding
import com.jbappz.jsontoviews.ui.ViewFactory
import com.jbappz.jsontoviews.util.Status
import com.jbappz.jsontoviews.util.Util
import com.jbappz.jsontoviews.viewmodel.AttractionsIOViewModel
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val viewModel: AttractionsIOViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewFactory: ViewFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Init View Factory
        viewFactory = ViewFactory(this, binding.layoutContainer)

        // Init Network Call
        initObservers()
        viewModel.getAppDescriptionData()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.appDescription.collect {
                when(it.status) {
                    Status.SUCCESS -> {
                        supportActionBar?.title = it.data?.title
                        viewFactory.removeProgressBar()
                        viewFactory.updateUI(it.data)
                    }
                    Status.ERROR -> {
                        viewFactory.removeProgressBar()
                        Util.errorDialog(this@MainActivity)
                    }
                    Status.LOADING -> {
                       viewFactory.addProgressBar()
                    }
                }
            }
        }
    }
}