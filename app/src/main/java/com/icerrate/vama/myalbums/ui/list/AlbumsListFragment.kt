package com.icerrate.vama.myalbums.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.icerrate.vama.myalbums.R
import com.icerrate.vama.myalbums.data.model.Album
import com.icerrate.vama.myalbums.data.model.error.NotAvailableException
import com.icerrate.vama.myalbums.ui.MainActivity
import com.icerrate.vama.myalbums.ui.common.ItemPaddingDecoration
import com.icerrate.vama.myalbums.ui.detail.AlbumDetailFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_albums_list.*
import kotlinx.android.synthetic.main.partial_error.*
import kotlinx.android.synthetic.main.partial_loader.*

@AndroidEntryPoint
class AlbumsListFragment : Fragment(), AlbumAdapter.OnAlbumClickListener {

    companion object {
        fun newInstance() = AlbumsListFragment()
    }

    private lateinit var viewModel: AlbumsListViewModel

    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_albums_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[AlbumsListViewModel::class.java]
        viewModel.albumsListLiveData.observe(viewLifecycleOwner) {
            clearErrorContainer()
            albumAdapter.setAlbums(it)
        }
        viewModel.loadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                loading_container.visibility = View.VISIBLE
                shimmer_container?.startShimmer()
            } else {
                loading_container.visibility = View.GONE
                shimmer_container?.stopShimmer()
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NotAvailableException -> {
                    onNotAvailableResources()
                }
                else -> {
                    //TODO: Handle other exceptions
                }
            }
        }
        viewModel.onViewCreated()
    }

    private fun clearErrorContainer() {
        error_container.visibility = View.GONE
        error_title.text = ""
        error_description.text = ""
        error_action.text = ""
        error_action.setOnClickListener(null)
    }

    private fun onNotAvailableResources() {
        error_container.visibility = View.VISIBLE
        error_title.text = getString(R.string.not_available_title)
        error_description.text = getString(R.string.not_available_description)
        error_action.text = getString(R.string.not_available_action)
        error_action.setOnClickListener {
            clearErrorContainer()
            viewModel.getAlbums()
        }
    }

    private fun initView() {
        albumAdapter = AlbumAdapter()
        albumAdapter.onAlbumClickListener = this
        albums_recycler.layoutManager = GridLayoutManager(requireContext(), resources.getInteger(R.integer.albums_columns))
        albums_recycler.adapter = albumAdapter
        val padding = resources.getDimension(R.dimen.padding_albums).toInt()
        albums_recycler.addItemDecoration(ItemPaddingDecoration(padding))
    }

    override fun onClick(album: Album) {
        val fragment = AlbumDetailFragment.newInstance(album, viewModel.getFooterText())
        (requireActivity() as MainActivity).addFragment(fragment)
    }
}