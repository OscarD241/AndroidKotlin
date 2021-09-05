package com.devskar.viewpager

/**
*
* @author Oscar Díaz
* @version 1, 2021/09/04
*
* El código forma parte del respositorio "AndroidKotlin"
* Github: https://github.com/OscarD241/AndroidKotlin
*
* */


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devskar.viewpager.databinding.ActivityMainBinding
import com.devskar.viewpager.fragments.Fragment1
import com.devskar.viewpager.fragments.Fragment2
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    /*
    para que el siguiente bindign funcione, no olvidar agregar el siguiente código
    en el build.gradle a nivel app (:app)

    buildFeatures{
        viewBinding true //activa el enlace de vistas
    }
     */
    private lateinit var binding: ActivityMainBinding

    private val PAGE1 = 0 //posición del fragment 1 dentro del viewPager
    private val PAGE2 = 1 //posición del fragment 2 dentro del viewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enlazamos el layout "activity_main.xml"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = getAdapter() //inicializamos el adapter de nuestro viewPager
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ _, _ -> }.attach()//linkeamos el viewPager con la tabLayout
    }

    /**
     * Genera un adaptador para el viewPager
     * @return adapter con los fragments que se verán en el viewPager
     */
    private fun getAdapter(): Adapter{
        val fragment1 = Fragment1.newInstance()
        fragment1.listener = getListener1()
        val fragment2 = Fragment2.newInstance()
        fragment2.listener = getListener2()

        return Adapter(supportFragmentManager, lifecycle).apply {
            addFragment(fragment1)
            addFragment(fragment2)
        }
    }

    /**
     * Genera un listener para comunicar el Fragment1 con la MainActivity
     * @return listener
     */
    private fun getListener1() = object: Fragment1.Fragment1Interface{
        override fun onClick() {
            binding.viewPager.setCurrentItem(PAGE2, true)
        }
    }

    /**
     * Genera un listener para comunicar el Fragment2 con la MainActivity
     * @return listener
     */
    private fun getListener2() = object: Fragment2.Fragment2Interface{
        override fun onClick() {
            binding.viewPager.setCurrentItem(PAGE1, true)
        }
    }
}