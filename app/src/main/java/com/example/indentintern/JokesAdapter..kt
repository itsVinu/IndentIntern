import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.indentintern.R
import com.example.indentintern.response.JokesResponse
import com.example.indentintern.response.ValueItem
import kotlinx.android.synthetic.main.item_recycle.view.*

class JokesAdapter(var list: List<ValueItem>) : RecyclerView.Adapter<JokesAdapter.ItemViewHolder>() {

    var onItemClick: ((user: ValueItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycle, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(list[position])


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: ValueItem) {
            itemView.apply {
                if (user.categories.isNullOrEmpty()){
                    category.visibility = View.GONE
                }
                else{
                    category.text = user.categories.toString()
                }
                joke.text = user.joke
                setOnClickListener{
                    onItemClick?.invoke(user)
                }
            }
        }
    }
}