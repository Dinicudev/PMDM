package net.azarquiel.sumanumberjpc

import android.os.SystemClock
import android.provider.Settings
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(mainActivity: MainActivity) : ViewModel() {
    val mainActivity by lazy { mainActivity }
    private val _aciertos = MutableLiveData<Int>()
    val aciertos: LiveData<Int> = _aciertos
    private val _intentos = MutableLiveData<Int>()
    val intentos: LiveData<Int> = _intentos
    private val _numeroRandom = MutableLiveData<Int>()
    val numeroRandom: LiveData<Int> = _numeroRandom
    private val _numero1 = MutableLiveData<Int?>()
    val numero1: LiveData<Int?> = _numero1
    private val _numero2 = MutableLiveData<Int?>()
    val numero2: LiveData<Int?> = _numero2
    private val _suma = MutableLiveData<Int>()
    val suma: LiveData<Int> = _suma
    private val _openDialog = MutableLiveData<Boolean>()
    val openDialog: LiveData<Boolean> = _openDialog
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg
    private val random = Random(System.currentTimeMillis())
    var tiempoinicio: Long = 0
    var tiempofin: Long = 0
    private val _color = MutableLiveData(Color.White)
    val color: LiveData<Color> = _color

    private var contador = 0

    init {
        newGame()
    }


     fun newGame() {
         tiempoinicio = System.currentTimeMillis()
        _aciertos.value = 0
        _intentos.value = 0
        _numeroRandom.value = (2..18).random(random)
        changeBackgroundColor(Color.White)
    }

    fun onClick(n: Int) {
        if (contador==0) {
            _numero1.value = n
            contador++

        }else {

            _numero2.value = n
            contador =0
            comprobar() }
        }

    fun comprobar() {
        _suma.value = _numero1.value!! + _numero2.value!!
        if (_suma.value == _numeroRandom.value) {
            _aciertos.value = _aciertos.value!! + 1
            _intentos.value = _intentos.value!! + 1
            changeBackgroundColor(Color.Green)
            GlobalScope.launch {
                SystemClock.sleep(1000)
                launch(Main) {
                    gameover()
                }
            }

        } else {
            _intentos.value = _intentos.value!! + 1
            changeBackgroundColor(Color.Red)
            GlobalScope.launch {
                SystemClock.sleep(1000)
                launch(Main) {
                     gameover()
                }
            }
        }
    }

    fun changeBackgroundColor(color: Color) {
        _color.value = color
    }

    fun reset() {
        _numeroRandom.value = (2..18).random(random)
        _numero1.value = null
        _numero2.value = null
        changeBackgroundColor(Color.White)
    }

    fun isOpenDialog(value: Boolean) {
        _openDialog.value = value
    }

    fun gameover() {
        if (_intentos.value!! >= 5) {
           var tiempo = (System.currentTimeMillis() - tiempoinicio)/1000
            _msg.value = "Has acertado ${_aciertos.value} en ${tiempo} segundos"
            _openDialog.value = true
        } else{
            reset()
        }
    }



}


