package com.icerrate.vama.myalbums.ui.list.usecase

import com.icerrate.vama.myalbums.provider.preference.AppPreferences
import javax.inject.Inject

class GetCopyrightUseCase @Inject constructor(
    private val appPreferences: AppPreferences
) {

    fun getText(): String? = appPreferences.getCopyrightText()
}