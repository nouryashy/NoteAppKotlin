package com.example.noteappkotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_note_preview.*
import kotlinx.android.synthetic.main.fragment_note_preview.back_bt

class NotePreviewFragment : Fragment() {

    var noteModel: NoteModel? = null
    var NOTE_OBJECT: String = "NOTE_OBJECT"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_note_preview, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteModel = arguments!!.getSerializable(NOTE_OBJECT) as NoteModel?
        var titleNew = title_Pre_tv.setText(noteModel!!.title).toString()
        var desNew = description_Pre_tv.setText(noteModel!!.description).toString()

        edit_bt.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(NOTE_OBJECT, noteModel)
            replaceFragmentWithBundle(
                AddNoteFragment(),
                fragmentManager,
                R.id.main_frame_layout, bundle, AddNoteFragment::class.java.simpleName, true
            )

        }
        back_bt.setOnClickListener {
            replaceFragment(NotesFragment())
        }
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
    fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction!!.replace(R.id.main_frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }


}