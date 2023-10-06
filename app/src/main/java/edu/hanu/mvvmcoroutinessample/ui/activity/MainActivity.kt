package edu.hanu.mvvmcoroutinessample.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import edu.hanu.mvvmcoroutinessample.databinding.ActivityMainBinding
import edu.hanu.mvvmcoroutinessample.repository.EventRepository
import edu.hanu.mvvmcoroutinessample.ui.adapter.EventAdapter
import edu.hanu.mvvmcoroutinessample.util.Resource
import edu.hanu.mvvmcoroutinessample.viewmodel.EventViewModel
import edu.hanu.mvvmcoroutinessample.viewmodel.ViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var eventViewModel: EventViewModel
    private lateinit var eventAdapter: EventAdapter
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter()
        binding.rvEvent.adapter = eventAdapter

        setupViewModel()
        eventViewModel.getEvents("")

    }

    private fun setupViewModel() {
        val repository = EventRepository()
        val factory = ViewModelProviderFactory(application, repository)
        eventViewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]

        eventViewModel.eventsData.observe(this) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { data ->
                        eventAdapter.differ.submitList(data)
                        eventAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.d("APILOG", "request error: ${message}")
                    }
                }
                is Resource.Loading -> {
                    Log.d("APILOG", "loading")
                }
            }
        }
    }
}