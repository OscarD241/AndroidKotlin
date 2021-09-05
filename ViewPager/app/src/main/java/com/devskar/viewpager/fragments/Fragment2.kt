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
import com.devskar.viewpager.R
import com.devskar.viewpager.databinding.Fragment2Binding

/**
 * Crea un [Fragment] simple.
 * Usar [Fragment2.newInstance] para crear una instancia del fragment
 */
class Fragment2 : Fragment() {

    companion object {
        /**
         * Usar este método para crear la instancia del fragment
         * @return nueva instancia de Fragment2.
         */
        @JvmStatic
        fun newInstance() = Fragment2()
    }

    var listener: Fragment2Interface? = null //interface para comunicar el fragment con la activity

    interface Fragment2Interface{
        fun onClick() //evento que se dispara cuando den click en el botón "anterior"
    }

    private lateinit var binding: Fragment2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btn2.setOnClickListener { //se genera el evento onClickListener del botón 2
            listener?.onClick()
        }
    }

}