package com.icerrate.vama.myalbums.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.icerrate.vama.myalbums.R
import com.icerrate.vama.myalbums.data.model.Album

class AlbumAdapter : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var data: List<Album> = mutableListOf()
    var onAlbumClickListener: OnAlbumClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = data[position]
        holder.bindView(item)
        holder.applyListener(onAlbumClickListener)
    }

    fun setAlbums(items: List<Album>) {
        data = items
        notifyDataSetChanged()
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var album: Album
        private val albumCover: ImageView = itemView.findViewById(R.id.album_cover)
        private val albumTitle: TextView = itemView.findViewById(R.id.album_title)
        private val albumArtis: TextView = itemView.findViewById(R.id.album_artist)

        fun bindView(item: Album) {
            val context = itemView.context
            album = item
            albumTitle.text = item.name
            albumArtis.text = item.artistName
            val roundCornerSize = context.resources.getDimension(R.dimen.album_round_corner).toInt()
            val albumResolution = context.resources.getInteger(R.integer.album_cover_small_resolution)
            Glide.with(context)
                .load(item.getCustomResArtworkUrl(albumResolution))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.bitmapTransform(RoundedCorners(roundCornerSize)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(albumCover)
        }

        fun applyListener(listener: OnAlbumClickListener?) {
            itemView.setOnClickListener {
                listener?.onClick(album)
            }
        }
    }

    interface OnAlbumClickListener {
        fun onClick(album: Album)
    }
}