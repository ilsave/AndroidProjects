package ru.gushchin.myapplication.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment

import ru.gushchin.myapplication.R
import ru.gushchin.myapplication.databinding.FragmentChatBinding
import ru.gushchin.myapplication.databinding.FragmentSettingsBinding

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {


    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    /**
     * Initialize the contents of the Fragment host's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called [.setHasOptionsMenu].  See
     * [Activity.onCreateOptionsMenu]
     * for more information.
     *
     * @param menu The options menu in which you place your items.
     *
     * @see .setHasOptionsMenu
     *
     * @see .onPrepareOptionsMenu
     *
     * @see .onOptionsItemSelected
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        activity?.menuInflater?.inflate(R.menu.settings_action_menu,menu)
    }
}
