package ru.gushchin.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.gushchin.myapplication.R
import ru.gushchin.myapplication.databinding.FragmentChatBinding

/**
 * A simple [Fragment] subclass.
 */
class ChatsFragment : BaseFragment(R.layout.fragment_chat) {

    private lateinit var mBinding: FragmentChatBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentChatBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to [Activity.onResume] of the containing
     * Activity's lifecycle.
     */
    override fun onResume() {
        super.onResume()
    }
}
