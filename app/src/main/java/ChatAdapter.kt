package sri.example.customersupportchatbot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val messageList: List<ChatMessage>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_BOT = 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (messageList[position].isUser) {
            VIEW_TYPE_USER
        } else {
            VIEW_TYPE_BOT
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_USER) {

            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_message,
                parent,
                false
            )

            UserViewHolder(view)

        } else {

            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.item_bot_message,
                parent,
                false
            )

            BotViewHolder(view)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {

        val chatMessage = messageList[position]

        if (holder is UserViewHolder) {

            holder.tvUserMessage.text = chatMessage.message
            holder.tvUserTime.text = chatMessage.time

        } else if (holder is BotViewHolder) {

            holder.tvBotMessage.text = chatMessage.message
            holder.tvBotTime.text = chatMessage.time
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }


    class UserViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvUserMessage: TextView =
            itemView.findViewById(R.id.tvUserMessage)

        val tvUserTime: TextView =
            itemView.findViewById(R.id.tvUserTime)
    }


    class BotViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val tvBotMessage: TextView =
            itemView.findViewById(R.id.tvBotMessage)

        val tvBotTime: TextView =
            itemView.findViewById(R.id.tvBotTime)
    }
}