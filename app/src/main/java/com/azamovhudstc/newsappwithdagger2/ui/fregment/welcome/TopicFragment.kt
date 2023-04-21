package com.azamovhudstc.newsappwithdagger2.ui.fregment.welcome

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azamovhudstc.newsappwithdagger2.databinding.FragmentTopicBinding
import com.azamovhudstc.newsappwithdagger2.App
import com.azamovhudstc.newsappwithdagger2.ui.adapters.TopicFavoriteAdapter
import com.azamovhudstc.newsappwithdagger2.ui.activity.MainActivity
import com.azamovhudstc.newsappwithdagger2.ui.activity.WelcomeActivity
import com.azamovhudstc.newsappwithdagger2.utils.Topics.appComponent
import com.azamovhudstc.newsappwithdagger2.utils.Topics.getTopicsList
import com.google.gson.Gson
import javax.inject.Inject

class TopicFragment : Fragment() {

    private var _binding: FragmentTopicBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TopicFavoriteAdapter

    @Inject
    lateinit var sPref: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopicBinding.inflate(layoutInflater, container, false)
        appComponent.inject(this)
        loadData()

        return binding.root
    }

    private fun loadData() {
        val topicsList = getTopicsList(binding.root.context)
        adapter = TopicFavoriteAdapter(topicsList)
        binding.rv.adapter = adapter

        binding.materialButton.setOnClickListener {
            val editor = sPref.edit()
            val selectedTopics = adapter.getSelectedTopics()
            val gson = Gson()
            val jsonText: String = gson.toJson(selectedTopics)

            editor.putString("topic", jsonText)
            editor.putBoolean("isUserRegistered", true)
            editor.apply()
            (activity as WelcomeActivity).finish()
            val intent = Intent(binding.root.context.applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

}