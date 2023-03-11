package com.example.totpkotlin.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import com.example.totpkotlin.R
import com.example.totpkotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), Observer<ArrayList<TotpEntry>>{

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val homeViewModel get() = _homeViewModel!!
    private var listViewAdapter : TotpEntryListAdapter? = null

    private var mainThreadHandler : Handler? = null

    private var isUpdating : Boolean = true;
    private var _homeViewModel : HomeViewModel? = null
    private val codeUpdater = object : Runnable {
        override fun run() {
            homeViewModel.updateAllEntries();

            if (isUpdating) {
                mainThreadHandler?.postDelayed(this, 1000)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java);
        mainThreadHandler = Handler(Looper.getMainLooper());
        mainThreadHandler?.post(codeUpdater);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        val listView : ListView = root.findViewById(R.id.totp_list_view)

        listViewAdapter = TotpEntryListAdapter(requireContext(), R.layout.totp_entry, homeViewModel.totpEntries.value!!)
        listView.adapter = listViewAdapter

        homeViewModel.totpEntries.observe(viewLifecycleOwner, this)

        homeViewModel.addTOTPEntry(TotpEntry("whatever", "whatever"));
        listViewAdapter?.notifyDataSetChanged();

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()

        isUpdating = false;
    }

    override fun onChanged(t: ArrayList<TotpEntry>?) {
        listViewAdapter?.notifyDataSetChanged();
    }
}