package ru.gushchin.myapplication.ui.objects

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import ru.gushchin.myapplication.R
import ru.gushchin.myapplication.ui.fragments.SettingsFragment

class AppDrawer(val mainActivity:AppCompatActivity, val toolbar: Toolbar) {
    private lateinit var mDrawer: Drawer
    private lateinit var mHeader: AccountHeader //верхняя часть в выдвижной фигне(drawer это называется)

    fun create(){
        createHeader()
        createDrawer()
    }

    private fun createDrawer() {
        mDrawer = DrawerBuilder()
            .withActivity(mainActivity)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withSelectedItem(-1)
            .withAccountHeader(mHeader)
            .addDrawerItems(
                PrimaryDrawerItem().withIdentifier(100)
                    .withIconTintingEnabled(true)
                    .withName("Create new group")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                PrimaryDrawerItem().withIdentifier(101)
                    .withIconTintingEnabled(true)
                    .withName("Create secret chat")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                PrimaryDrawerItem().withIdentifier(102)
                    .withIconTintingEnabled(true)
                    .withName("Create new channel")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                PrimaryDrawerItem().withIdentifier(103)
                    .withIconTintingEnabled(true)
                    .withName("Contacts")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                PrimaryDrawerItem().withIdentifier(104)
                    .withIconTintingEnabled(true)
                    .withName("Calls")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                PrimaryDrawerItem().withIdentifier(105)
                    .withIconTintingEnabled(true)
                    .withName("Favourite")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                PrimaryDrawerItem().withIdentifier(106)
                    .withIconTintingEnabled(true)
                    .withName("Settings")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                DividerDrawerItem(),
                PrimaryDrawerItem().withIdentifier(107)
                    .withIconTintingEnabled(true)
                    .withName("Invite new friends")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network),
                PrimaryDrawerItem().withIdentifier(108)
                    .withIconTintingEnabled(true)
                    .withName("Questions about Telegramm")
                    .withSelectable(false)
                    .withIcon(R.drawable.social_network)


            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                /**
                 * @param view
                 * @param position
                 * @param drawerItem
                 * @return true if the event was consumed
                 */
                //функция нажатия на че нить из нашего drawer\а
                override fun onItemClick(
                    view: View?,
                    position: Int,
                    drawerItem: IDrawerItem<*>
                ): Boolean {
                    Toast.makeText(mainActivity.applicationContext,position.toString(), Toast.LENGTH_SHORT).show()
                    //getapplicationContext потому что мы находимся в кликере (this если в самой активности как я понял)

                    when(position){
                        //addBackStack - чтобы потом можно было вернуться в основное активити
                        7 ->
                            mainActivity.supportFragmentManager.beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.dataContainer,
                                    SettingsFragment()
                                ).commit()
                    }
                    return false
                }
            }).build()


    }

    private fun createHeader() {
        mHeader = AccountHeaderBuilder()
            .withActivity(mainActivity)
            .withHeaderBackground(R.drawable.header)
            .addProfiles(
                ProfileDrawerItem().withName("Ilya Gushchin")
                    .withEmail("bulldoo")
            ).build()
    }

}