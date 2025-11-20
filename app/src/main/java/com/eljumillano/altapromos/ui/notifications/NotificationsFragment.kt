package com.eljumillano.altapromos.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.eljumillano.altapromos.R
import com.eljumillano.altapromos.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        loadMockStats()
        return binding.root
    }

    private fun loadMockStats() {
        val total = 24
        val nuevos = 8
        val completados = 16

        val dispSi = 15
        val dispNo = 9
        val dispTotal = dispSi + dispNo

        val voucher = 13
        val promo50 = 11
        val promoTotal = voucher + promo50

        binding.tvTotalCount.text = total.toString()
        binding.tvNewCount.text = nuevos.toString()
        binding.tvCompletedCount.text = completados.toString()

        val pctSi = if (dispTotal > 0) dispSi * 100 / dispTotal else 0
        val pctNo = if (dispTotal > 0) dispNo * 100 / dispTotal else 0
        binding.tvDispenserYesCount.text = "$dispSi ($pctSi%)"
        binding.tvDispenserNoCount.text = "$dispNo ($pctNo%)"

        // Configurar gráfico de dispensador
        binding.chartDispenser.setData(listOf(
            DonutChartView.ChartSegment(
                pctSi.toFloat(),
                ContextCompat.getColor(requireContext(), R.color.accent_blue)
            ),
            DonutChartView.ChartSegment(
                pctNo.toFloat(),
                0xFF999999.toInt()
            )
        ))

        val pctVoucher = if (promoTotal > 0) voucher * 100 / promoTotal else 0
        val pctPromo50 = if (promoTotal > 0) promo50 * 100 / promoTotal else 0
        binding.tvVoucherCount.text = "$voucher ($pctVoucher%)"
        binding.tvPromo50Count.text = "$promo50 ($pctPromo50%)"

        // Configurar gráfico de tipos de promo
        binding.chartPromo.setData(listOf(
            DonutChartView.ChartSegment(
                pctVoucher.toFloat(),
                ContextCompat.getColor(requireContext(), R.color.purple_500)
            ),
            DonutChartView.ChartSegment(
                pctPromo50.toFloat(),
                ContextCompat.getColor(requireContext(), R.color.accent_pink)
            )
        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
