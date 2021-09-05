package com.devskar.viewpager.fragments

/**
 * @author Oscar Díaz
 * @version 1, 2021/09/04
 */

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devskar.viewpager.databinding.Fragment1Binding

/**
 * Crea un [Fragment] simple.
 * Usar [Fragment1.newInstance] para crear una instancia del fragment
 */
class Fragment1 : Fragment() {

    companion object {
        /**
         * Usar este método para crear la instancia del fragment
         * @return nueva instancia de Fragment1.
         */
        @JvmStatic
        fun newInstance() = Fragment1()
    }

    var listener: Fragment1Interface? = null //interface para comunicar el fragment con la activity

    interface Fragment1Interface{
        fun onClick() //evento que se dispara cuando den click en el botón "siguiente"
    }

    private lateinit var binding: Fragment1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = Fragment1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn1.setOnClickListener { //se genera el evento onClickListener del botón 1
            listener?.onClick()
        }
    }

}