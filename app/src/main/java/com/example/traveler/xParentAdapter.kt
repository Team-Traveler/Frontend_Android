import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.traveler.ChildData
import com.example.traveler.databinding.XitemChildRecyclerBinding

class xParentAdapter(private val childDataList: MutableList<ChildData>) :
    RecyclerView.Adapter<xParentAdapter.ParentViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    class ParentViewHolder(
        private val binding: XitemChildRecyclerBinding,
        private val childDataList: MutableList<ChildData>,
        private val adapter: xParentAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.delete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    adapter.deleteItem(position)
                }
            }
        }

        fun bind(childData: ChildData) {
            binding.triptext.text = childData.spot
            binding.button.text = childData.number.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding = XitemChildRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ParentViewHolder(binding, childDataList, this)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        val parentData = childDataList[position]
        holder.bind(parentData)
    }

    override fun getItemCount(): Int {
        return childDataList.size
    }

    fun deleteItem(position: Int) {
        if (position in 0 until childDataList.size) {
            childDataList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    // 외부에서 아이템 클릭 리스너 설정
    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    // 아이템 클릭 리스너 인터페이스
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
