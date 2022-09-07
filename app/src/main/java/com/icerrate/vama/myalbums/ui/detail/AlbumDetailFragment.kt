package com.icerrate.vama.myalbums.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.chip.Chip
import com.icerrate.vama.myalbums.R
import com.icerrate.vama.myalbums.data.model.Album
import com.icerrate.vama.myalbums.data.model.Genre
import com.icerrate.vama.myalbums.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album_detail.*

@AndroidEntryPoint
class AlbumDetailFragment : Fragment() {

    companion object {
        private const val DETAIL_KEY = "_detail_key"
        private const val FOOTER_KEY = "_footer_key"

        fun newInstance(detail: Album, footerText: String?): AlbumDetailFragment {
            val fragment = AlbumDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable(DETAIL_KEY, detail)
            bundle.putString(FOOTER_KEY, footerText)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_album_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detail = arguments?.get(DETAIL_KEY) as Album
        val footerText = arguments?.getString(FOOTER_KEY)
        initView()
        loadDetail(detail, footerText)
    }

    private fun initView() {
        back.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun loadDetail(detail: Album, copyrightText: String?) {
        val smallResolution = resources.getInteger(R.integer.album_cover_small_resolution)
        val highResolution = resources.getInteger(R.integer.album_cover_high_resolution)
        Glide.with(requireContext())
            .load(detail.getCustomResArtworkUrl(highResolution))
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(Glide.with(requireContext()).load(detail.getCustomResArtworkUrl(smallResolution)))
            .into(album_cover)
        artist_name.text = detail.artistName
        album_title.text = detail.name
        detail.genres.forEach { genre ->
            val chip = prepareGenreChip(genre)
            genres_container.addView(chip)
        }
        release_date.text =
            getString(R.string.released_date, DateUtils.formatStringDate(detail.releaseDate))
        copyright.text = copyrightText ?: ""
        visit_album.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(detail.url)
            startActivity(i)
        }
    }

    private fun prepareGenreChip(genre: Genre): Chip {
        val chip = Chip(requireContext())
        chip.chipStrokeWidth = resources.getDimension(R.dimen.chip_stroke_width)
        val baseColor = resources.getColorStateList(R.color.bright_blue, requireContext().theme)
        chip.chipStrokeColor = baseColor
        chip.setTextColor(baseColor)
        chip.chipBackgroundColor =
            resources.getColorStateList(R.color.translucent, requireContext().theme)
        chip.text = genre.name
        chip.isEnabled = false
        val layoutParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParam.marginEnd = resources.getDimension(R.dimen.chip_margin).toInt()
        chip.layoutParams = layoutParam
        return chip
    }
}