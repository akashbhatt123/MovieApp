package com.example.movieapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.movieapp.R
import com.example.movieapp.ui.movielist.MovieListFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.navigation_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_popular -> {
                        setCategory(POPULAR)
                        true
                    }
                    R.id.action_top_rated -> {
                        setCategory(TOP_RATED)
                        true
                    }
                    else -> false
                }
            }
        })

        if(savedInstanceState == null) {
            val movieListFragment = MovieListFragment()
            loadFragment(movieListFragment)
            loadFragment(movieListFragment)
        }
    }

    fun setCategory(category: String) {
        supportFragmentManager.fragments.let { fragmentList ->
            if(fragmentList.isNotEmpty() && fragmentList.get(fragmentList.size - 1) is MovieListFragment) {
                (fragmentList.get(fragmentList.size - 1) as? MovieListFragment)?.let {
                    it.setCategory(category)
                }
            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        const val POPULAR = "popular"
        const val TOP_RATED = "top_rated"
    }
}
