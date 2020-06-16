package ru.gushchin.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.gushchin.myapplication.R

open class BaseFragment(val layout: Int) : Fragment() {

    //макет где будут установлены остальные вью
    private lateinit var mRootView : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       mRootView = inflater.inflate(layout, container, false)
        return  mRootView
    }

    override fun onStart() {
        super.onStart()

    }
}
