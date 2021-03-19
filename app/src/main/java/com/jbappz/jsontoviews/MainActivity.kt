package com.jbappz.jsontoviews

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jbappz.jsontoviews.databinding.ActivityMainBinding
import com.jbappz.jsontoviews.ui.ViewDesigner
import com.jbappz.jsontoviews.util.Status
import com.jbappz.jsontoviews.util.Util
import com.jbappz.jsontoviews.viewmodel.AttractionsIOViewModel
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val viewModel: AttractionsIOViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewDesigner: ViewDesigner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Init View Designer
        viewDesigner = ViewDesigner(this, binding.layoutContainer)

        // Init Network Call
        initObservers()
        viewModel.getAppDescriptionData()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.appDescription.collect {
                when(it.status) {
                    Status.SUCCESS -> {
                        viewDesigner.removeProgressBar()
                        supportActionBar?.title = it.data?.title
                        viewDesigner.updateUI(it.data)
                    }
                    Status.ERROR -> {
                        viewDesigner.removeProgressBar()
                        Util.errorDialog(this@MainActivity)
                    }
                    Status.LOADING -> {
                       viewDesigner.addProgressBar()
                    }
                }
            }
        }
    }
}