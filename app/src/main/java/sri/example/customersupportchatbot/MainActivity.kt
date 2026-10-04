package sri.example.customersupportchatbot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewChat: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var btnSend: Button

    private lateinit var btnOrder: Button
    private lateinit var btnDelivery: Button
    private lateinit var btnRefund: Button
    private lateinit var btnHelp: Button
    private lateinit var btnClearChat: Button

    private lateinit var typingIndicator: LinearLayout

    private lateinit var chatAdapter: ChatAdapter
    private val messageList = mutableListOf<ChatMessage>()

    private lateinit var intentDetector: IntentDetector

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // ================= INITIALIZE VIEWS =================

        recyclerViewChat = findViewById(R.id.recyclerViewChat)
        editTextMessage = findViewById(R.id.editTextMessage)
        btnSend = findViewById(R.id.btnSend)

        btnOrder = findViewById(R.id.btnOrder)
        btnDelivery = findViewById(R.id.btnDelivery)
        btnRefund = findViewById(R.id.btnRefund)
        btnHelp = findViewById(R.id.btnHelp)
        btnClearChat = findViewById(R.id.btnClearChat)

        typingIndicator = findViewById(R.id.typingIndicator)

        // ================= CHATBOT BRAIN =================

        intentDetector = IntentDetector()

        // ================= RECYCLER VIEW =================

        chatAdapter = ChatAdapter(messageList)

        recyclerViewChat.layoutManager = LinearLayoutManager(this)
        recyclerViewChat.adapter = chatAdapter

        // ================= WELCOME MESSAGE =================

        addBotMessage(
            "👋 Hello! Welcome to Customer Support.\n\n" +
                    "I'm your virtual support assistant. " +
                    "How can I help you today?"
        )

        // ================= SEND BUTTON =================

        btnSend.setOnClickListener {
            sendMessage()
        }

        // ================= KEYBOARD SEND =================

        editTextMessage.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendMessage()
                true
            } else {
                false
            }
        }

        // ================= QUICK ACTIONS =================

        btnOrder.setOnClickListener {
            sendQuickMessage("I want help with my order")
        }

        btnDelivery.setOnClickListener {
            sendQuickMessage("I want information about delivery")
        }

        btnRefund.setOnClickListener {
            sendQuickMessage("I want information about refund")
        }

        btnHelp.setOnClickListener {
            sendQuickMessage("I need help")
        }

        // ================= CLEAR CHAT =================

        btnClearChat.setOnClickListener {
            clearChat()
        }
    }


    // =====================================================
    // SEND USER MESSAGE
    // =====================================================

    private fun sendMessage() {

        val userMessage = editTextMessage.text.toString().trim()

        if (userMessage.isEmpty()) {
            return
        }

        // Add user message
        addUserMessage(userMessage)

        // Clear input
        editTextMessage.text.clear()

        // Show typing indicator
        showTypingIndicator()

        // Generate bot response
        handler.postDelayed({

            hideTypingIndicator()

            val botResponse = intentDetector.getResponse(userMessage)

            addBotMessage(botResponse)

        }, 700)
    }


    // =====================================================
    // QUICK MESSAGE
    // =====================================================

    private fun sendQuickMessage(message: String) {

        addUserMessage(message)

        showTypingIndicator()

        handler.postDelayed({

            hideTypingIndicator()

            val botResponse = intentDetector.getResponse(message)

            addBotMessage(botResponse)

        }, 700)
    }


    // =====================================================
    // SHOW TYPING INDICATOR
    // =====================================================

    private fun showTypingIndicator() {

        typingIndicator.visibility = View.VISIBLE

        recyclerViewChat.post {

            if (messageList.isNotEmpty()) {
                recyclerViewChat.scrollToPosition(
                    messageList.size - 1
                )
            }
        }
    }


    // =====================================================
    // HIDE TYPING INDICATOR
    // =====================================================

    private fun hideTypingIndicator() {

        typingIndicator.visibility = View.GONE
    }


    // =====================================================
    // CLEAR CHAT
    // =====================================================

    private fun clearChat() {

        // Cancel pending bot response
        handler.removeCallbacksAndMessages(null)

        // Hide typing indicator
        hideTypingIndicator()

        // Clear messages
        messageList.clear()

        chatAdapter.notifyDataSetChanged()

        // Add fresh welcome message
        addBotMessage(
            "👋 Chat cleared.\n\n" +
                    "How can I help you today?"
        )
    }


    // =====================================================
    // ADD USER MESSAGE
    // =====================================================

    private fun addUserMessage(message: String) {

        messageList.add(
            ChatMessage(
                message = message,
                isUser = true,
                time = getCurrentTime()
            )
        )

        chatAdapter.notifyItemInserted(
            messageList.size - 1
        )

        recyclerViewChat.scrollToPosition(
            messageList.size - 1
        )
    }


    // =====================================================
    // ADD BOT MESSAGE
    // =====================================================

    private fun addBotMessage(message: String) {

        messageList.add(
            ChatMessage(
                message = message,
                isUser = false,
                time = getCurrentTime()
            )
        )

        chatAdapter.notifyItemInserted(
            messageList.size - 1
        )

        recyclerViewChat.scrollToPosition(
            messageList.size - 1
        )
    }


    // =====================================================
    // CURRENT TIME
    // =====================================================

    private fun getCurrentTime(): String {

        val dateFormat = SimpleDateFormat(
            "hh:mm a",
            Locale.getDefault()
        )

        return dateFormat.format(Date())
    }


    // =====================================================
    // CLEANUP
    // =====================================================

    override fun onDestroy() {

        handler.removeCallbacksAndMessages(null)

        super.onDestroy()
    }
}