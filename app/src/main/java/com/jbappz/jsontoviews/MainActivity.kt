package com.jbappz.jsontoviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jbappz.jsontoviews.viewmodel.AttractionsIOViewModel
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    val viewModel: AttractionsIOViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initObservers()
        viewModel.getAppDescriptionData()
    }

    private fun initObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.appDescription.collect {
                when(it.status) {
                    // TODO: Hook up to view classes, yet to be implemented
                }
            }
        }
    }
}