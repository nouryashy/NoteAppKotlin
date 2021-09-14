package com.example.noteappkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.note_list_item.view.*

class NoteAdapter(
    private var noteList: ArrayList<NoteModel>,
    var context: Context,

    ) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        var view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        holder.bindItems(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setList(noteModel: ArrayList<NoteModel>) {
        this.noteList = noteModel
        notifyDataSetChanged()


    }

    object RxBus {
        val itemClickStream: PublishSubject<View> = PublishSubject.create()
        val itemClickStreamD: PublishSubject<NoteModel> = PublishSubject.create()
        val itemClickStreamDel: PublishSubject<NoteModel> = PublishSubject.create()



    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var noteTitle = itemView.title_tv
        private var noteDes = itemView.description_tv
        var noteCard = itemView.note_card
        var deleteIb = itemView.delete_ib

        fun bindItems(noteModel: NoteModel) {
            noteTitle.text = noteModel.title
            noteDes.text = noteModel.description

            noteCard.setOnClickListener { v: View ->
                RxBus.itemClickStream.onNext(v)
                RxBus.itemClickStreamD.onNext(noteModel)

            }

            deleteIb.setOnClickListener { v : View ->
//                val position = adapterPosition
//                noteList.removeAt(position)
//                notifyItemRemoved(position)
//                notifyDataSetChanged()
                RxBus.itemClickStreamDel.onNext(noteModel)





            }


        }


    }
}