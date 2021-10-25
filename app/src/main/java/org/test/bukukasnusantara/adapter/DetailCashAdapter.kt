package org.test.bukukasnusantara.adapter
import androidx.lifecycle.MutableLiveData
import org.test.bukukasnusantara.model.DetailCash
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import org.test.bukukasnusantara.R

class DetailCashAdapter(data: MutableList<DetailCash>?) :
    BaseQuickAdapter<DetailCash, BaseViewHolder>(R.layout.item_detail_cash, data) {
    override fun convert(viewHolder: BaseViewHolder, detailCash: DetailCash) {
        viewHolder.setText(R.id.item_nominal, "Rp. ${detailCash.nominal}")
            .setText(R.id.item_keterangan, detailCash.keterangan)
            .setText(R.id.item_tgl, detailCash.tanggal)
        if (detailCash.type == "masuk") {
            viewHolder.setImageResource(R.id.item_status, R.drawable.ic_income)
        } else {
            viewHolder.setImageResource(R.id.item_status, R.drawable.ic_outcome)
        }
    }
}