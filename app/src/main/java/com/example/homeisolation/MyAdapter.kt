package com.example.homeisolation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private  val chatList: ArrayList<ChatModel>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.message_item,parent,false
        )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = chatList[position]
        holder.chatUser.text = currentitem.user
        holder.chatMessage.text = currentitem.message
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val chatUser: TextView = itemView.findViewById(R.id.chatUser)
        val chatMessage :TextView = itemView.findViewById(R.id.chatMessage)
    }

}