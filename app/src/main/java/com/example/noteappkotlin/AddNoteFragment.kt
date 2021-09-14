package com.example.noteappkotlin

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins.onSubscribe
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.note_list_item.*

class AddNoteFragment : Fragment() {

    var noteDatabase: NoteDatabase? = null
    var Id1 = 0
    var noteModel: NoteModel? = null
    var NOTE_OBJECT: String = "NOTE_OBJECT"
    var tNew: String? = null
    var dNew: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDatabase = NoteDatabase.DataBaseManager().getInstance(context!!)
        try {
            noteModel = arguments!!.getSerializable(NOTE_OBJECT) as NoteModel?
            Id1 = noteModel!!.id
            if (Id1 != 0) {
                tNew = title_Et.setText(noteModel!!.title).toString()
                dNew = description_Et.setText(noteModel!!.description).toString()
            }
        } catch (ex: Exception) {
        }
        save_bt.setOnClickListener {
            var values = ContentValues()
            values.put("Title", title_Et.text.toString())
            values.put("Description", title_Et.text.toString())

            if (Id1 == 0) {
                addNotes()
            } else {
                updateNotes()

            }

        }
        back_bt.setOnClickListener {
            replaceFragment(NotesFragment())


        }
    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction!!.replace(R.id.main_frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }


    fun addNotes() {
        noteDatabase!!.noteDao()
            .addData(NoteModel(0, title_Et.text.toString(), description_Et.text.toString()))
            .subscribeOn(Schedulers.computation())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
                    replaceFragment(NotesFragment())
//                    Toast.makeText(activity, "You added ${noteModel!!.title} successfully", Toast.LENGTH_LONG).show()
                }


                override fun onError(e: Throwable) {

                }

            })

    }


    fun updateNotes() {
        noteDatabase!!.noteDao()
            .update(
                NoteModel(
                    Id1,
                    title_Et.text.toString(),
                    description_Et.text.toString()
                )
            )
            .subscribeOn(Schedulers.computation())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onComplete() {
//                    Toast.makeText(activity, "Note updating successfully", Toast.LENGTH_LONG).show()
                    replaceFragment(NotesFragment())

                }


                override fun onError(e: Throwable) {

                }

            })
    }


}