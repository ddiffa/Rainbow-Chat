package com.example.rainbow_chat.rainbow

import android.os.Handler
import android.os.Looper
import com.ale.infra.contact.IRainbowContact
import com.ale.infra.list.IItemListChangeListener
import com.ale.infra.proxy.conversation.IRainbowConversation
import com.ale.listener.SigninResponseListener
import com.ale.listener.StartResponseListener
import com.ale.rainbowsdk.RainbowSdk


object RainbowConnection {

    val rainbowContacts: List<IRainbowContact>
        get() = RainbowSdk.instance().contacts().rainbowContacts.copyOfDataList

    fun startConnection(connection: RainbowConectionListener.Connection) {
        RainbowSdk.instance().connection().start(object : StartResponseListener() {
            override fun onStartSucceeded() {
                Handler(Looper.getMainLooper())
                    .post(connection::onConnectionSuccess)
            }

            override fun onRequestFailed(errorCode: RainbowSdk.ErrorCode?, s: String?) {
                Handler(Looper.getMainLooper())
                    .post {
                        connection.onConnectionFailed(s.toString())
                    }
            }
        })
    }

    fun startSignIn(email: String, password: String, login: RainbowConectionListener.Login) {
        RainbowSdk.instance().connection()
            .signin(email, password, "sandbox.openrainbow.com", object : SigninResponseListener() {
                override fun onRequestFailed(errorCode: RainbowSdk.ErrorCode?, s: String?) {
                    Handler(Looper.getMainLooper())
                        .post { login.onSignInFailed(s.toString()) }
                }

                override fun onSigninSucceeded() {
                    Handler(Looper.getMainLooper())
                        .post(login::onSignInSuccess)
                }

            })
    }

    fun getConversationFromContact(contactJid: String?): IRainbowConversation =
        RainbowSdk.instance().conversations().getConversationFromContact(contactJid)

    fun getMessageFromConversation(conversation: IRainbowConversation) =
        RainbowSdk.instance().im().getMessagesFromConversation(conversation, 100)

    fun sendMessageToConversation(conversation: IRainbowConversation, message: String) =
        RainbowSdk.instance().im().sendMessageToConversation(conversation, message)

    fun registerAllRainbowContact(listener: IItemListChangeListener) =
        RainbowSdk.instance().contacts().rainbowContacts.registerChangeListener(listener)

    fun unregisterAllRainbowContact(listener: IItemListChangeListener) =
        RainbowSdk.instance().contacts().rainbowContacts.unregisterChangeListener(listener)

    fun registerContactChangeListener(listener: IRainbowContact.IContactListener) {
        rainbowContacts.forEach {
            it.registerChangeListener(listener)
        }
    }

    fun unregisterContactChangeListener(listener: IRainbowContact.IContactListener) {
        rainbowContacts.forEach {
            it.unregisterChangeListener(listener)
        }
    }
}