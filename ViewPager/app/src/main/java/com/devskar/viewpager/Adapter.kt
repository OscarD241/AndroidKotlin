package com.devskar.viewpager

/**
 * @author Oscar Díaz
 * @version 1, 2021/09/04
*/

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


/**
 * Genera un [FragmentStateAdapter] para poder trabajar con el ViewPager2
 * @param manager: FragmentManager
 * @param lifecycle: Lifecycle
*/
class Adapter(manager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(manager, lifecycle) {

    private val fragmentList: MutableList<Fragment> = ArrayList() //lista para almacenar los fragments

    /**
     * Agrega un fragment a la lista de fragments
     * @param fragment: Fragment que se desea agregar
     */
    fun addFragment(fragment: Fragment){
        fragmentList.add(fragment)
    }

    /**
     * Obtiene la cantidad de elementos que contiene el adapter
     * @return cantidad de fragments dentro de fragmentList
     */
    override fun getItemCount() = fragmentList.size

    /**
     * Despliega en pantalla el fragment seleccionado
     * @return fragment a desplegar en pantalla
     */
    override fun createFragment(position: Int) = fragmentList[position]
}