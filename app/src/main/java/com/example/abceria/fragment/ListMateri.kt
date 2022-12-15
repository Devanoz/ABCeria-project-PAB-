package com.example.abceria.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abceria.R
import com.example.abceria.adapter.LeaderboardAdapter
import com.example.abceria.adapter.ListMateriAdapter
import com.example.abceria.db.DB
import com.example.abceria.model.materi.Materi
import com.example.abceria.model.user.User


class ListMateri : Fragment() {

    private val firestore = DB.getFirestoreInstance()
    private lateinit var rvListMateri: RecyclerView
    private lateinit var listMateriAdapter: ListMateriAdapter
    private val materiList:ArrayList<Materi> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun getMateriList() {
        firestore.collection("materi").get().addOnSuccessListener {
            it.forEach{ document ->
                val materi = Materi()
                materi.title = document.get("title").toString()
                materi.description = document.get("descriptionn").toString()
                materiList.add(materi)
            }
            listMateriAdapter = ListMateriAdapter(materiList)
            rvListMateri.adapter = listMateriAdapter
        }


    }

    private fun initRecyclerView(view: View){
        rvListMateri = view.findViewById(R.id.list_rv_materi)
        rvListMateri.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_materi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        getMateriList()

    }


}