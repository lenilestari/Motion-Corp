package com.example.motioncorp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.motioncorp.Fragment.HomeFragment
import com.example.motioncorp.Fragment.InfoFragment
import com.example.motioncorp.Fragment.NewsFragment
import com.example.motioncorp.Fragment.RadioFragment
import com.example.motioncorp.Fragment.TelevisiFragment
import com.example.motioncorp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var bottomNavigation: MeowBottomNavigation
    private val ID_HOME = 1
    private val ID_TELEVISI = 2
    private val ID_RADIO = 3
    private val ID_NEWS = 4
    private val ID_INFO = 5

    // Tambahkan variabel yang akan menyimpan tampilan fragment saat ini
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Inisialisasi UI komponen
        val customToolbar = binding.customToolbar
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Inisialisasi bottomNavigation
        initializeBottomNavigation()

        val fragmentManager = supportFragmentManager

        // Inisialisasi fungsi untuk tombol kembali
        customToolbar.Back.setOnClickListener {
            val fragmentCount = fragmentManager.backStackEntryCount
            if (fragmentCount > 0) {
                // Jika ada fragment di tumpukan, tampilkan fragment sebelumnya
                fragmentManager.popBackStack()
                val fragmentTag = fragmentManager.getBackStackEntryAt(fragmentCount - 1).name
                // Mengatur bottom navigation sesuai dengan fragment saat ini
                setBottomNavigationItem(fragmentTag)
                currentFragment = fragmentManager.findFragmentByTag(fragmentTag)
            } else {
                super.onBackPressed()
            }
        }

        // Periksa apakah ada fragment saat ini
        if (currentFragment == null) {
            // Jika tidak ada fragment yang ditampilkan, tampilkan HomeFragment
            replace(HomeFragment())
            setBottomNavigationItem(HomeFragment::class.java.simpleName)
        }
    }

    private fun initializeBottomNavigation() {
        bottomNavigation.add(MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_home))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_TELEVISI, R.drawable.ic_televisi))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_RADIO, R.drawable.ic_radio))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_NEWS, R.drawable.ic_news))
        bottomNavigation.add(MeowBottomNavigation.Model(ID_INFO, R.drawable.ic_corporat))

        bottomNavigation.setOnClickMenuListener { item -> }
        bottomNavigation.setOnShowListener { item ->
            when (item.id) {
                ID_HOME -> {
                    replace(HomeFragment())
                }
                ID_TELEVISI -> {
                    replace(TelevisiFragment())
                }
                ID_RADIO -> {
                    replace(RadioFragment())
                }
                ID_NEWS -> {
                    replace(NewsFragment())
                }
                ID_INFO -> {
                    replace(InfoFragment())
                }
            }
        }
    }

    private fun setBottomNavigationItem(fragmentTag: String?) {
        when (fragmentTag) {
            HomeFragment::class.java.simpleName -> {
                bottomNavigation.show(ID_HOME, true)
            }
            TelevisiFragment::class.java.simpleName -> {
                bottomNavigation.show(ID_TELEVISI, true)
            }
            RadioFragment::class.java.simpleName -> {
                bottomNavigation.show(ID_RADIO, true)
            }
            NewsFragment::class.java.simpleName -> {
                bottomNavigation.show(ID_NEWS, true)
            }
            InfoFragment::class.java.simpleName -> {
                bottomNavigation.show(ID_INFO, true)
            }
        }
    }

    private fun replace(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        val fragmentTag = fragment.javaClass.simpleName
        transaction.replace(R.id.frame, fragment, fragmentTag)
        transaction.addToBackStack(fragmentTag)
        transaction.commit()
        currentFragment = fragment
    }

//    override fun onBackPressed() {
//        val fragmentManager = supportFragmentManager
//        val fragmentCount = fragmentManager.backStackEntryCount
//        if (fragmentCount > 0) {
//            // Jika ada fragment di tumpukan, tampilkan fragment sebelumnya
//            fragmentManager.popBackStack()
//            val fragmentTag = fragmentManager.getBackStackEntryAt(fragmentCount - 1).name
//            setBottomNavigationItem(fragmentTag)
//            currentFragment = fragmentManager.findFragmentByTag(fragmentTag)
//        } else {
//            if (currentFragment !is HomeFragment) {
//                // Jika tidak ada fragment di tumpukan, dan fragment saat ini bukan HomeFragment, tampilkan HomeFragment
//                replace(HomeFragment())
//                setBottomNavigationItem(HomeFragment::class.java.simpleName)
//            } else {
//                super.onBackPressed()
//            }
//        }
//    }



}
