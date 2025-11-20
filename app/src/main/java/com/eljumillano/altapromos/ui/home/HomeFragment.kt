package com.eljumillano.altapromos.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eljumillano.altapromos.R
import com.eljumillano.altapromos.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var clientsAdapter: ClientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecycler()
        loadMockData()
        return binding.root
    }

    private fun setupRecycler() {
        clientsAdapter = ClientsAdapter { }
        binding.rvClients.layoutManager = LinearLayoutManager(requireContext())
        binding.rvClients.adapter = clientsAdapter
    }

    private fun loadMockData() {
        val data = listOf(
            Client(
                id = "1",
                name = "Juan Pérez",
                address = "Av. Libertador 1234",
                locality = "CABA",
                reparto = "123",
                phone = "",
                email = "",
                time = timeOffset(0),
                status = ClientStatus.NEW
            ),
            Client(
                id = "2",
                name = "María González",
                address = "Calle San Martín 567",
                locality = "Vicente López",
                reparto = "456",
                phone = "",
                email = "",
                time = timeOffset(-30),
                status = ClientStatus.PENDING
            ),
            Client(
                id = "3",
                name = "Carlos Rodríguez",
                address = "Av. Rivadavia 8901",
                locality = "Flores",
                reparto = "789",
                phone = "",
                email = "",
                time = timeOffset(-60),
                status = ClientStatus.COMPLETED
            )
        )
        clientsAdapter.submitList(data)
        updateSubtitle(data.size)
        toggleEmpty(data.isEmpty())
    }

    private fun updateSubtitle(count: Int) {
        binding.tvSubtitle.text = getString(R.string.clients_today_count, count)
    }

    private fun toggleEmpty(empty: Boolean) {
        binding.emptyState.visibility = if (empty) View.VISIBLE else View.GONE
        binding.rvClients.visibility = if (empty) View.GONE else View.VISIBLE
    }

    private fun timeOffset(minutesOffset: Int): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, minutesOffset)
        return SimpleDateFormat("HH:mm", Locale.getDefault()).format(cal.time)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

