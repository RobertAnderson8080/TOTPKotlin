package com.example.totpkotlin.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.totpkotlin.totp.TotpTokenGenerator

class TotpEntry {

    private val _name : MutableLiveData<String> = MutableLiveData("")
    private val _secret : MutableLiveData<String> = MutableLiveData("")
    private val _digits : MutableLiveData<Int> = MutableLiveData(6)
    private val _period : MutableLiveData<Int> = MutableLiveData(30)
    private val _code : MutableLiveData<String> = MutableLiveData("")
    private val _time_remaining : MutableLiveData<Int> = MutableLiveData(30)

    val name : LiveData<String> = _name
    val secret : LiveData<String> = _secret
    val digits : LiveData<Int> = _digits
    val period : LiveData<Int> = _period
    val code : LiveData<String> = _code
    val timeRemaining : LiveData<Int> = _time_remaining

    constructor(name: String, secret : String)
    {
        this._name.postValue(name)
        this._secret.postValue(secret)
    }

    constructor(name: String, secret : String, digits : Int)
    {
        this._name.postValue(name)
        this._secret.postValue(secret)
        this._digits.postValue(digits)
    }

    constructor(name: String, secret: String, digits: Int, period : Int)
    {
        this._name.postValue(name)
        this._secret.postValue(secret)
        this._digits.postValue(digits)
        this._period.postValue(period)
    }

    fun refreshCode()
    {
        val tokenGenerator : TotpTokenGenerator = TotpTokenGenerator();
        _code.postValue(tokenGenerator.generateToken(secret.value!!, digits.value!!, period.value!!))
    }

    fun refreshTimeRemaining()
    {
        _time_remaining.postValue(computeTimeRemaining())
    }

    private fun computeTimeRemaining() : Int
    {
        //todo
        return 30;
    }
}