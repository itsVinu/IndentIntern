package com.example.indentintern

import JokesAdapter
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.postDelayed
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.indentintern.Client.Client
import com.example.indentintern.response.ValueItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Runnable


class MainActivity : AppCompatActivity() {
    val list = arrayListOf<ValueItem>()
    val jokeadapter = JokesAdapter(list)

    lateinit var layoutManager:LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        mainRv.apply {
//            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL,false)
//            adapter = jokeadapter
//        }

        layoutManager = LinearLayoutManager(this)
        mainRv.layoutManager = layoutManager
        mainRv.adapter = jokeadapter


        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) { Client.api.jokes() }
            if (response.isSuccessful) {
                response.body().let { res ->
                    res?.value?.let {
                        list.addAll(it)
                    }
                    runOnUiThread {
                        jokeadapter.notifyDataSetChanged()
                    }
                }
            }
        }
        progress.visibility = View.VISIBLE

        mainRv.addOnScrollListener( object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                progress.visibility = View.VISIBLE
                if (dy >0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = jokeadapter.itemCount

                    if(!isFinishing){

                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            progress.visibility = View.VISIBLE
                            Handler().postDelayed({progress.visibility = View.VISIBLE
                            Bottom()},2000)
//                            Bottom()
//                            progress.visibility = View.GONE
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        } )
    }

    fun Bottom(){
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) { Client.api.jokes() }
            if (response.isSuccessful) {
                response.body().let { res ->
                    res?.value?.let {
                        list.addAll(it)
                    }
                    runOnUiThread {
                        jokeadapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

}
