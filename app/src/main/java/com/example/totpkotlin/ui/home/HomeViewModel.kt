package com.example.totpkotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _totpEntries : MutableLiveData<ArrayList<TotpEntry>> = MutableLiveData(ArrayList());

    val totpEntries : LiveData<ArrayList<TotpEntry>> = _totpEntries

    fun writeTOTPEntriesToFile()
    {
        throw NotImplementedError();
    }

    fun readTOTPEntriesFromFile()
    {
        throw NotImplementedError();
    }

    fun addTOTPEntry(entry : TotpEntry)
    {
        _totpEntries.value?.add(entry)
        _totpEntries.postValue(_totpEntries.value)
    }

    fun updateAllEntries()
    {
        for (entry in _totpEntries.value!!)
        {
            entry.refreshCode();
            entry.refreshTimeRemaining()
        }
    }
}