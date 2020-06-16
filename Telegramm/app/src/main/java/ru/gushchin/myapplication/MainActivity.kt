package ru.gushchin.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.gushchin.myapplication.databinding.ActivityMainBinding
import ru.gushchin.myapplication.ui.fragments.ChatsFragment
import ru.gushchin.myapplication.ui.objects.AppDrawer

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root) // .root - обращение к самому макету
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() { // прописываем всю функциольность нашего активити
        setSupportActionBar(mToolbar) //чтобы отобразился тулбар
        mAppDrawer.create()
        //устанавливаем контейнер в мейн активити
        //commit - это подтверждение
        supportFragmentManager.beginTransaction()
            .replace(R.id.dataContainer,
                ChatsFragment()
            ).commit()

    }


    private fun initFields() { // функция, инициализирующая все значения
        mToolbar = mBinding.mainToolBar
        mAppDrawer = AppDrawer(this,mToolbar)

    }
}
