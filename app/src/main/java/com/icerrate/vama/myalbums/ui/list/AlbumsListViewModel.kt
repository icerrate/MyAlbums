package com.icerrate.vama.myalbums.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icerrate.vama.myalbums.data.model.Album
import com.icerrate.vama.myalbums.ui.list.usecase.GetAlbumsUseCase
import com.icerrate.vama.myalbums.ui.common.addTo
import com.icerrate.vama.myalbums.ui.list.usecase.GetCopyrightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class AlbumsListViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getCopyrightUseCase: GetCopyrightUseCase
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _loadingLiveData = MutableLiveData<Boolean>()
    private val _errorLiveData = MutableLiveData<Throwable>()
    private val _albumsListLiveData = MutableLiveData<List<Album>>()

    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData
    val errorLiveData: LiveData<Throwable> get() = _errorLiveData
    val albumsListLiveData: LiveData<List<Album>> get() = _albumsListLiveData

    fun onViewCreated() {
        getAlbums()
    }

    fun getAlbums() {
        getAlbumsUseCase.getAlbums()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _loadingLiveData.value = true }
            .doAfterTerminate { _loadingLiveData.value = false }
            .subscribe(
                { albumsList ->
                    _albumsListLiveData.value = albumsList
                },
                {
                    _errorLiveData.value = it
                }
            ).addTo(compositeDisposable)
    }

    fun getFooterText(): String? {
        return getCopyrightUseCase.getText()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}