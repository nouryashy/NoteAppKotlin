package com.example.noteappkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_notes.*
import java.util.*
import kotlin.collections.ArrayList


class NotesFragment : Fragment() {
    var noteDatabase: NoteDatabase? = null
    var noteModel: NoteModel? = null
    var NOTE_OBJECT: String = "NOTE_OBJECT"
    var noteAdapter: NoteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        return view
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiRecycleView()
        add_note_bt.setOnClickListener {
            replaceFragment(AddNoteFragment())

        }


        var ad = NoteAdapter.RxBus
        ad.itemClickStream.subscribe() { v ->
            if (v.id == R.id.note_card) {
                ad.itemClickStreamD.subscribe() { noteModel ->
                    val bundle = Bundle()
                    bundle.putSerializable(NOTE_OBJECT, noteModel)
                    replaceFragmentWithBundle(
                        NotePreviewFragment(),
                        fragmentManager,
                        R.id.main_frame_layout,
                        bundle,
                        NotePreviewFragment::class.java.simpleName,
                        true
                    )
                }


            }


        }


        ad.itemClickStreamDel.subscribe() { noteModel ->
            deleteaNote(noteModel)

        }
    }

    @SuppressLint("CheckResult")
    fun intiRecycleView() {

        val noteList = ArrayList<NoteModel>()
        val noteRecView: RecyclerView = notes_recycle_view
        noteAdapter = NoteAdapter(noteList, context!!)
        noteRecView.layoutManager = LinearLayoutManager(context)
        noteRecView.adapter = noteAdapter
        getAllNotes()


    }


    fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction!!.replace(R.id.main_frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun replaceFragmentWithBundle(
        fragment: Fragment, fragmentManager: FragmentManager?,
        containerViewId: Int, bundle: Bundle?, TAG: String?,
        isAddToBackStack: Boolean
    ) {
        assert(fragmentManager != null)
        fragment.arguments = bundle
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.replace(containerViewId, fragment, TAG)
            .commit()
    }

    fun getAllNotes() {
        noteDatabase = NoteDatabase.DataBaseManager().getInstance(context!!)
        noteDatabase!!.noteDao().getAllNotes()
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<NoteModel>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(notes: List<NoteModel>) {

                    noteAdapter!!.setList(notes as ArrayList<NoteModel>)
                    noteAdapter!!.notifyDataSetChanged()
                }

                override fun onError(e: Throwable) {

                }

            })

    }

    fun deleteaNote(noteModel: NoteModel) {
        noteDatabase!!.noteDao().delete(noteModel)
            .subscribeOn(Schedulers.computation())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {

                    try {
                        noteDatabase!!.noteDao().getAllNotes()
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : SingleObserver<List<NoteModel>> {
                                override fun onSubscribe(d: Disposable) {
//
                                }


                                override fun onSuccess(notes: List<NoteModel>) {

                                    noteAdapter!!.setList(notes as ArrayList<NoteModel>)
                                    noteAdapter!!.notifyDataSetChanged()


                                }

                                override fun onError(e: Throwable) {

                                }

                            })


                    } catch (ex: Exception) {
                    }

                }


                override fun onError(e: Throwable) {

                }

            })

    }


}



